package io.github.jhipster.application.service.dto;

import io.github.jhipster.application.domain.enumeration.TradeStatus;

import java.io.Serializable;
import java.util.Objects;

public class TradeConfirmDTO implements Serializable {

    private Integer score;

    private String message;

    private TradeStatus tradeStatus;

    public TradeConfirmDTO(){
    }

    public TradeConfirmDTO(TradeConfirmDTO other){
        this.score = other.score;
        this.message = other.message;
        this.tradeStatus = other.tradeStatus;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TradeConfirmDTO that = (TradeConfirmDTO) o;
        return
            Objects.equals(score, that.score) &&
            Objects.equals(message, that.message) &&
            Objects.equals(tradeStatus, that.tradeStatus);

    }

    @Override
    public int hashCode() {
        return Objects.hash(
        score,
        message,
        tradeStatus
        );
    }

    @Override
    public String toString() {
        return "TradeConfirm{" +
                (score != null ? "score=" + score + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (tradeStatus != null ? "tradeStatus=" + tradeStatus+ ", " : "") +
            "}";
    }

}
