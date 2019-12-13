package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.epmweb.server.domain.PaymentTransactions} entity.
 */
public class PaymentTransactionsDTO implements Serializable {

    private Long id;

    
    @Lob
    private String returnedCompletedPaymentData;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long paymentOnOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReturnedCompletedPaymentData() {
        return returnedCompletedPaymentData;
    }

    public void setReturnedCompletedPaymentData(String returnedCompletedPaymentData) {
        this.returnedCompletedPaymentData = returnedCompletedPaymentData;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getPaymentOnOrderId() {
        return paymentOnOrderId;
    }

    public void setPaymentOnOrderId(Long ordersId) {
        this.paymentOnOrderId = ordersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentTransactionsDTO paymentTransactionsDTO = (PaymentTransactionsDTO) o;
        if (paymentTransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentTransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentTransactionsDTO{" +
            "id=" + getId() +
            ", returnedCompletedPaymentData='" + getReturnedCompletedPaymentData() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", paymentOnOrder=" + getPaymentOnOrderId() +
            "}";
    }
}
