package com.epmweb.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.epmweb.server.domain.enumeration.PaymentMethod;
import com.epmweb.server.domain.enumeration.InvoiceStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.epmweb.server.domain.Invoices} entity. This class is used
 * in {@link com.epmweb.server.web.rest.InvoicesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvoicesCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PaymentMethod
     */
    public static class PaymentMethodFilter extends Filter<PaymentMethod> {

        public PaymentMethodFilter() {
        }

        public PaymentMethodFilter(PaymentMethodFilter filter) {
            super(filter);
        }

        @Override
        public PaymentMethodFilter copy() {
            return new PaymentMethodFilter(this);
        }

    }
    /**
     * Class for filtering InvoiceStatus
     */
    public static class InvoiceStatusFilter extends Filter<InvoiceStatus> {

        public InvoiceStatusFilter() {
        }

        public InvoiceStatusFilter(InvoiceStatusFilter filter) {
            super(filter);
        }

        @Override
        public InvoiceStatusFilter copy() {
            return new InvoiceStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter invoiceDate;

    private StringFilter customerPurchaseOrderNumber;

    private BooleanFilter isCreditNote;

    private StringFilter creditNoteReason;

    private StringFilter comments;

    private StringFilter deliveryInstructions;

    private StringFilter internalComments;

    private IntegerFilter totalDryItems;

    private IntegerFilter totalChillerItems;

    private StringFilter deliveryRun;

    private StringFilter runPosition;

    private StringFilter returnedDeliveryData;

    private InstantFilter confirmedDeliveryTime;

    private StringFilter confirmedReceivedBy;

    private PaymentMethodFilter paymentMethod;

    private InvoiceStatusFilter status;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter invoiceLineListId;

    private LongFilter contactPersonId;

    private LongFilter salespersonPersonId;

    private LongFilter packedByPersonId;

    private LongFilter accountsPersonId;

    private LongFilter customerId;

    private LongFilter billToCustomerId;

    private LongFilter deliveryMethodId;

    private LongFilter orderId;

    public InvoicesCriteria(){
    }

    public InvoicesCriteria(InvoicesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.invoiceDate = other.invoiceDate == null ? null : other.invoiceDate.copy();
        this.customerPurchaseOrderNumber = other.customerPurchaseOrderNumber == null ? null : other.customerPurchaseOrderNumber.copy();
        this.isCreditNote = other.isCreditNote == null ? null : other.isCreditNote.copy();
        this.creditNoteReason = other.creditNoteReason == null ? null : other.creditNoteReason.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.deliveryInstructions = other.deliveryInstructions == null ? null : other.deliveryInstructions.copy();
        this.internalComments = other.internalComments == null ? null : other.internalComments.copy();
        this.totalDryItems = other.totalDryItems == null ? null : other.totalDryItems.copy();
        this.totalChillerItems = other.totalChillerItems == null ? null : other.totalChillerItems.copy();
        this.deliveryRun = other.deliveryRun == null ? null : other.deliveryRun.copy();
        this.runPosition = other.runPosition == null ? null : other.runPosition.copy();
        this.returnedDeliveryData = other.returnedDeliveryData == null ? null : other.returnedDeliveryData.copy();
        this.confirmedDeliveryTime = other.confirmedDeliveryTime == null ? null : other.confirmedDeliveryTime.copy();
        this.confirmedReceivedBy = other.confirmedReceivedBy == null ? null : other.confirmedReceivedBy.copy();
        this.paymentMethod = other.paymentMethod == null ? null : other.paymentMethod.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.invoiceLineListId = other.invoiceLineListId == null ? null : other.invoiceLineListId.copy();
        this.contactPersonId = other.contactPersonId == null ? null : other.contactPersonId.copy();
        this.salespersonPersonId = other.salespersonPersonId == null ? null : other.salespersonPersonId.copy();
        this.packedByPersonId = other.packedByPersonId == null ? null : other.packedByPersonId.copy();
        this.accountsPersonId = other.accountsPersonId == null ? null : other.accountsPersonId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.billToCustomerId = other.billToCustomerId == null ? null : other.billToCustomerId.copy();
        this.deliveryMethodId = other.deliveryMethodId == null ? null : other.deliveryMethodId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
    }

    @Override
    public InvoicesCriteria copy() {
        return new InvoicesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(InstantFilter invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public StringFilter getCustomerPurchaseOrderNumber() {
        return customerPurchaseOrderNumber;
    }

    public void setCustomerPurchaseOrderNumber(StringFilter customerPurchaseOrderNumber) {
        this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
    }

    public BooleanFilter getIsCreditNote() {
        return isCreditNote;
    }

    public void setIsCreditNote(BooleanFilter isCreditNote) {
        this.isCreditNote = isCreditNote;
    }

    public StringFilter getCreditNoteReason() {
        return creditNoteReason;
    }

    public void setCreditNoteReason(StringFilter creditNoteReason) {
        this.creditNoteReason = creditNoteReason;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(StringFilter deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public StringFilter getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(StringFilter internalComments) {
        this.internalComments = internalComments;
    }

    public IntegerFilter getTotalDryItems() {
        return totalDryItems;
    }

    public void setTotalDryItems(IntegerFilter totalDryItems) {
        this.totalDryItems = totalDryItems;
    }

    public IntegerFilter getTotalChillerItems() {
        return totalChillerItems;
    }

    public void setTotalChillerItems(IntegerFilter totalChillerItems) {
        this.totalChillerItems = totalChillerItems;
    }

    public StringFilter getDeliveryRun() {
        return deliveryRun;
    }

    public void setDeliveryRun(StringFilter deliveryRun) {
        this.deliveryRun = deliveryRun;
    }

    public StringFilter getRunPosition() {
        return runPosition;
    }

    public void setRunPosition(StringFilter runPosition) {
        this.runPosition = runPosition;
    }

    public StringFilter getReturnedDeliveryData() {
        return returnedDeliveryData;
    }

    public void setReturnedDeliveryData(StringFilter returnedDeliveryData) {
        this.returnedDeliveryData = returnedDeliveryData;
    }

    public InstantFilter getConfirmedDeliveryTime() {
        return confirmedDeliveryTime;
    }

    public void setConfirmedDeliveryTime(InstantFilter confirmedDeliveryTime) {
        this.confirmedDeliveryTime = confirmedDeliveryTime;
    }

    public StringFilter getConfirmedReceivedBy() {
        return confirmedReceivedBy;
    }

    public void setConfirmedReceivedBy(StringFilter confirmedReceivedBy) {
        this.confirmedReceivedBy = confirmedReceivedBy;
    }

    public PaymentMethodFilter getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodFilter paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public InvoiceStatusFilter getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatusFilter status) {
        this.status = status;
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

    public LongFilter getInvoiceLineListId() {
        return invoiceLineListId;
    }

    public void setInvoiceLineListId(LongFilter invoiceLineListId) {
        this.invoiceLineListId = invoiceLineListId;
    }

    public LongFilter getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(LongFilter contactPersonId) {
        this.contactPersonId = contactPersonId;
    }

    public LongFilter getSalespersonPersonId() {
        return salespersonPersonId;
    }

    public void setSalespersonPersonId(LongFilter salespersonPersonId) {
        this.salespersonPersonId = salespersonPersonId;
    }

    public LongFilter getPackedByPersonId() {
        return packedByPersonId;
    }

    public void setPackedByPersonId(LongFilter packedByPersonId) {
        this.packedByPersonId = packedByPersonId;
    }

    public LongFilter getAccountsPersonId() {
        return accountsPersonId;
    }

    public void setAccountsPersonId(LongFilter accountsPersonId) {
        this.accountsPersonId = accountsPersonId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getBillToCustomerId() {
        return billToCustomerId;
    }

    public void setBillToCustomerId(LongFilter billToCustomerId) {
        this.billToCustomerId = billToCustomerId;
    }

    public LongFilter getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(LongFilter deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InvoicesCriteria that = (InvoicesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(customerPurchaseOrderNumber, that.customerPurchaseOrderNumber) &&
            Objects.equals(isCreditNote, that.isCreditNote) &&
            Objects.equals(creditNoteReason, that.creditNoteReason) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(deliveryInstructions, that.deliveryInstructions) &&
            Objects.equals(internalComments, that.internalComments) &&
            Objects.equals(totalDryItems, that.totalDryItems) &&
            Objects.equals(totalChillerItems, that.totalChillerItems) &&
            Objects.equals(deliveryRun, that.deliveryRun) &&
            Objects.equals(runPosition, that.runPosition) &&
            Objects.equals(returnedDeliveryData, that.returnedDeliveryData) &&
            Objects.equals(confirmedDeliveryTime, that.confirmedDeliveryTime) &&
            Objects.equals(confirmedReceivedBy, that.confirmedReceivedBy) &&
            Objects.equals(paymentMethod, that.paymentMethod) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(invoiceLineListId, that.invoiceLineListId) &&
            Objects.equals(contactPersonId, that.contactPersonId) &&
            Objects.equals(salespersonPersonId, that.salespersonPersonId) &&
            Objects.equals(packedByPersonId, that.packedByPersonId) &&
            Objects.equals(accountsPersonId, that.accountsPersonId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(billToCustomerId, that.billToCustomerId) &&
            Objects.equals(deliveryMethodId, that.deliveryMethodId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        invoiceDate,
        customerPurchaseOrderNumber,
        isCreditNote,
        creditNoteReason,
        comments,
        deliveryInstructions,
        internalComments,
        totalDryItems,
        totalChillerItems,
        deliveryRun,
        runPosition,
        returnedDeliveryData,
        confirmedDeliveryTime,
        confirmedReceivedBy,
        paymentMethod,
        status,
        lastEditedBy,
        lastEditedWhen,
        invoiceLineListId,
        contactPersonId,
        salespersonPersonId,
        packedByPersonId,
        accountsPersonId,
        customerId,
        billToCustomerId,
        deliveryMethodId,
        orderId
        );
    }

    @Override
    public String toString() {
        return "InvoicesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "") +
                (customerPurchaseOrderNumber != null ? "customerPurchaseOrderNumber=" + customerPurchaseOrderNumber + ", " : "") +
                (isCreditNote != null ? "isCreditNote=" + isCreditNote + ", " : "") +
                (creditNoteReason != null ? "creditNoteReason=" + creditNoteReason + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (deliveryInstructions != null ? "deliveryInstructions=" + deliveryInstructions + ", " : "") +
                (internalComments != null ? "internalComments=" + internalComments + ", " : "") +
                (totalDryItems != null ? "totalDryItems=" + totalDryItems + ", " : "") +
                (totalChillerItems != null ? "totalChillerItems=" + totalChillerItems + ", " : "") +
                (deliveryRun != null ? "deliveryRun=" + deliveryRun + ", " : "") +
                (runPosition != null ? "runPosition=" + runPosition + ", " : "") +
                (returnedDeliveryData != null ? "returnedDeliveryData=" + returnedDeliveryData + ", " : "") +
                (confirmedDeliveryTime != null ? "confirmedDeliveryTime=" + confirmedDeliveryTime + ", " : "") +
                (confirmedReceivedBy != null ? "confirmedReceivedBy=" + confirmedReceivedBy + ", " : "") +
                (paymentMethod != null ? "paymentMethod=" + paymentMethod + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (invoiceLineListId != null ? "invoiceLineListId=" + invoiceLineListId + ", " : "") +
                (contactPersonId != null ? "contactPersonId=" + contactPersonId + ", " : "") +
                (salespersonPersonId != null ? "salespersonPersonId=" + salespersonPersonId + ", " : "") +
                (packedByPersonId != null ? "packedByPersonId=" + packedByPersonId + ", " : "") +
                (accountsPersonId != null ? "accountsPersonId=" + accountsPersonId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
                (billToCustomerId != null ? "billToCustomerId=" + billToCustomerId + ", " : "") +
                (deliveryMethodId != null ? "deliveryMethodId=" + deliveryMethodId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
