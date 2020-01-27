package io.github.jhipster.application.service;

import java.util.ArrayList;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.ListOf;

@Model 
@ListOf(listClass = PayPalProduct.class) 
public class PayPalProductList extends ArrayList<PayPalProduct> { 

    public PayPalProductList() {
        super();
    }
}
