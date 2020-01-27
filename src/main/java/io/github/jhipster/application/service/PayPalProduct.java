package io.github.jhipster.application.service;

import java.util.ArrayList;
import java.util.List;

import com.paypal.http.annotations.Model;
import com.paypal.http.annotations.SerializedName;
import com.paypal.orders.LinkDescription;
import com.paypal.http.annotations.ListOf;
// {
//     "products":[
//        {
//           "id":"PROD-4FG90808JU154103T",
//           "name":"OSRS Names premium",
//           "description":"Extra account features for improving buying and selling of Old School Runescape names",
//           "create_time":"2020-01-27T05:08:55Z",
//           "links":[
//              {
//                 "href":"https://api.sandbox.paypal.com/v1/catalogs/products/PROD-4FG90808JU154103T",
//                 "rel":"self",
//                 "method":"GET"
//              }
//           ]
//        },
//        {
//           "id":"PROD-3C418042313263730",
//           "name":"OSRS Names premium",
//           "description":"Extra account features for improving buying and selling of Old School Runescape names",
//           "create_time":"2020-01-27T05:14:31Z",
//           "links":[
//              {
//                 "href":"https://api.sandbox.paypal.com/v1/catalogs/products/PROD-3C418042313263730",
//                 "rel":"self",
//                 "method":"GET"
//              }
//           ]
//        }
//     ],
//     "links":[
//        {
//           "href":"https://api.sandbox.paypal.com/v1/catalogs/products?page_size=10&page=1",
//           "rel":"self",
//           "method":"GET"
//        }
//     ]
//  }


@Model
public class PayPalProduct {
    
    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("description")
    String description;

    @SerializedName("create_time")
    String createTime;

    @SerializedName("links")
    LinkDescriptionList links;

    public PayPalProduct() {}

    PayPalProduct setId(String id) {
        this.id = id;
        return this;
    }

    String getId() {
        return id;
    }

    PayPalProduct setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    String getCreateTime() {
        return createTime;
    }

    PayPalProduct setName(String name) {
        this.name = name;
        return this;
    }

    String getName() {
        return name;
    }

    PayPalProduct setDescription(String description) {
        this.description = description;
        return this;
    }

    String getDescription() {
        return description;
    }

    PayPalProduct setLinks(LinkDescriptionList links) {
        this.links = links;
        return this;
    }

    LinkDescriptionList getLinks() {
        return links;
    }

}
