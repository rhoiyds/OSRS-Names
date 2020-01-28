package io.github.jhipster.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.github.jhipster.application.service.paypal.PayPal;

/**
 * Properties specific to Rsnsales.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final PayPal payPal = new PayPal();

    public PayPal getPayPal() {
        return payPal;
    }

}
