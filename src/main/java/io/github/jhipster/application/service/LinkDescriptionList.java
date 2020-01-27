package io.github.jhipster.application.service;

import java.util.ArrayList;

import com.paypal.http.annotations.Model;
import com.paypal.orders.LinkDescription;
import com.paypal.http.annotations.ListOf;

@Model 
@ListOf(listClass = LinkDescription.class) 
public class LinkDescriptionList extends ArrayList<LinkDescription> { 

    public LinkDescriptionList() {
        super();
    }
}
