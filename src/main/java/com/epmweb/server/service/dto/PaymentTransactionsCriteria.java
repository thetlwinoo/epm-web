package com.epmweb.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.epmweb.server.domain.PaymentTransactions} entity. This class is used
 * in {@link com.epmweb.server.web.rest.PaymentTransactionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentTransactionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter paymentOnOrderId;

    public PaymentTransactionsCriteria(){
    }

    public PaymentTransactionsCriteria(PaymentTransactionsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.paymentOnOrderId = other.paymentOnOrderId == null ? null : other.paymentOnOrderId.copy();
    }

    @Override
    public PaymentTransactionsCriteria copy() {
        return new PaymentTransactionsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public InstantFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(InstantFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public LongFilter getPaymentOnOrderId() {
        return paymentOnOrderId;
    }

    public void setPaymentOnOrderId(LongFilter paymentOnOrderId) {
        this.paymentOnOrderId = paymentOnOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentTransactionsCriteria that = (PaymentTransactionsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(paymentOnOrderId, that.paymentOnOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        lastEditedBy,
        lastEditedWhen,
        paymentOnOrderId
        );
    }

    @Override
    public String toString() {
        return "PaymentTransactionsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (paymentOnOrderId != null ? "paymentOnOrderId=" + paymentOnOrderId + ", " : "") +
            "}";
    }

}
