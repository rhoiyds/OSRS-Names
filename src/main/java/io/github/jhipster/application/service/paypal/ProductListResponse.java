package io.github.jhipster.application.service.paypal;

import java.util.List;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;
import com.paypal.orders.LinkDescription;

@Model
public class ProductListResponse {

    @SerializedName(value = "links", listClass=LinkDescription.class)
    private List<LinkDescription> links;

    @SerializedName(value = "products", listClass=PayPalProduct.class)
    private List<PayPalProduct> products;

    public ProductListResponse() {}

    public List<PayPalProduct> getProducts() {
        return products;
    }

    public ProductListResponse setProducts(List<PayPalProduct> products) {
        this.products = products;
        return this;
    }

  public List<LinkDescription> getLinks() {
      return links;
  }

  public ProductListResponse setLinks(List<LinkDescription> links) {
      this.links = links;
      return this;
  }
  
}