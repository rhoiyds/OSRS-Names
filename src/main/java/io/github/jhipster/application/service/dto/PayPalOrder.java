package io.github.jhipster.application.service.dto;
import javax.validation.constraints.NotBlank;

/**
 * A DTO representing an order String from PayPal.
 */
public class PayPalOrder {

    @NotBlank
    private String id;

    public PayPalOrder() {
        // Empty constructor needed for Jackson.
    }

    public PayPalOrder(PayPalOrder payPalOrder) {
        this.id = payPalOrder.getId();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PayPalOrder{" +
            "id='" + id + '\'' +
            "}";
    }
}
