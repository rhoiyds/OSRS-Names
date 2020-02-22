package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.*;

import io.github.jhipster.config.JHipsterProperties;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String OFFER = "offer";

    private static final String COMMENT = "comment";

    private static final String LISTING = "listing";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(new InternetAddress(jHipsterProperties.getMail().getFrom(), "OSRS Names"));
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    @Async
    public void sendEmailFromTemplate(Context context, String templateName, String titleKey) {
        User user = (User)context.getVariable(USER);
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        if (!user.getReceiveNotifications()) {
            log.debug("Receiver {} has opted out of receiving emails - not sending", user.toString());
            return;
        }
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        Context context = getNewContext(user);
        sendEmailFromTemplate(context, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        Context context = getNewContext(user);
        sendEmailFromTemplate(context, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        Context context = getNewContext(user);
        sendEmailFromTemplate(context, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendNewOfferMail(Offer offer) {
        log.debug("Sending new offer email to '{}'", offer.getListing().getOwner().getEmail());
        Context context = getNewContext(offer.getListing().getOwner());
        context.setVariable(OFFER, offer);
        sendEmailFromTemplate(context, "mail/newOfferEmail", "email.newOffer.title");
    }

    @Async
    public void sendNewCommentMail(Comment comment) {
        Long offerOwnerId = comment.getOffer().getOwner().getId();
        Long commentOwnerId = comment.getOwner().getId();

        User recipient = offerOwnerId.equals(commentOwnerId) ? comment.getOffer().getListing().getOwner() : comment.getOffer().getListing().getOwner();
        log.debug("Sending new comment email to '{}'", recipient.getEmail());
        Context context = getNewContext(recipient);
        context.setVariable(COMMENT, comment);
        sendEmailFromTemplate(context, "mail/commentEmail", "email.comment.title");
    }

    @Async
    public void sendAnsweredOfferMail(Offer offer) {
        log.debug("Sending new offer email to '{}'", offer.getOwner().getEmail());
        Context context = getNewContext(offer.getOwner());
        context.setVariable(OFFER, offer);
        context.setVariable("action", offer.getStatus().toString().toLowerCase());
        sendEmailFromTemplate(context, "mail/answeredOfferEmail", "email.answeredOffer.title");
    }

    @Async
    public void sendNewMatchMail(Listing listing) {
        log.debug("Sending new match email to '{}'", listing.getOwner().getEmail());
        Context context = getNewContext(listing.getOwner());
        context.setVariable(LISTING, listing);
        sendEmailFromTemplate(context, "mail/newMatchEmail", "email.newMatch.title");
    }

    public Context getNewContext(User user) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        return context;
    }

    @Async
    public void sendNewCategoryMatchMail(User toUser, Listing matchedListing) {
        log.debug("Sending new match category match email to '{}'",toUser.getEmail());
        Context context = getNewContext(toUser);
        context.setVariable(LISTING, matchedListing);
        context.setVariable("category", matchedListing.getTags().stream().map(Tag::getName).collect(Collectors.joining(", ")));
        sendEmailFromTemplate(context, "mail/newCategoryMatchEmail", "email.newMatch.title");
    }
}
