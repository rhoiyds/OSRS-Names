package io.github.jhipster.application.service.paypal;


public class Plan {

    private String id;

    private String name;

    private String cost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Plan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cost='" + getCost() + "'" +
            "}";
    }

}