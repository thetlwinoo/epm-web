package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.epmweb.server.domain.enumeration.PaymentMethod;
import com.epmweb.server.domain.enumeration.InvoiceStatus;

/**
 * A DTO for the {@link com.epmweb.server.domain.Invoices} entity.
 */
public class InvoicesDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant invoiceDate;

    private String customerPurchaseOrderNumber;

    @NotNull
    private Boolean isCreditNote;

    private String creditNoteReason;

    private String comments;

    private String deliveryInstructions;

    private String internalComments;

    @NotNull
    private Integer totalDryItems;

    @NotNull
    private Integer totalChillerItems;

    private String deliveryRun;

    private String runPosition;

    private String returnedDeliveryData;

    private Instant confirmedDeliveryTime;

    private String confirmedReceivedBy;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private InvoiceStatus status;

    private String lastEditedBy;

    private Instant lastEditedWhen;


    private Long contactPersonId;

    private String contactPersonFullName;

    private Long salespersonPersonId;

    private String salespersonPersonFullName;

    private Long packedByPersonId;

    private String packedByPersonFullName;

    private Long accountsPersonId;

    private String accountsPersonFullName;

    private Long customerId;

    private Long billToCustomerId;

    private Long deliveryMethodId;

    private String deliveryMethodName;

    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerPurchaseOrderNumber() {
        return customerPurchaseOrderNumber;
    }

    public void setCustomerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
        this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
    }

    public Boolean isIsCreditNote() {
        return isCreditNote;
    }

    public void setIsCreditNote(Boolean isCreditNote) {
        this.isCreditNote = isCreditNote;
    }

    public String getCreditNoteReason() {
        return creditNoteReason;
    }

    public void setCreditNoteReason(String creditNoteReason) {
        this.creditNoteReason = creditNoteReason;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public Integer getTotalDryItems() {
        return totalDryItems;
    }

    public void setTotalDryItems(Integer totalDryItems) {
        this.totalDryItems = totalDryItems;
    }

    public Integer getTotalChillerItems() {
        return totalChillerItems;
    }

    public void setTotalChillerItems(Integer totalChillerItems) {
        this.totalChillerItems = totalChillerItems;
    }

    public String getDeliveryRun() {
        return deliveryRun;
    }

    public void setDeliveryRun(String deliveryRun) {
        this.deliveryRun = deliveryRun;
    }

    public String getRunPosition() {
        return runPosition;
    }

    public void setRunPosition(String runPosition) {
        this.runPosition = runPosition;
    }

    public String getReturnedDeliveryData() {
        return returnedDeliveryData;
    }

    public void setReturnedDeliveryData(String returnedDeliveryData) {
        this.returnedDeliveryData = returnedDeliveryData;
    }

    public Instant getConfirmedDeliveryTime() {
        return confirmedDeliveryTime;
    }

    public void setConfirmedDeliveryTime(Instant confirmedDeliveryTime) {
        this.confirmedDeliveryTime = confirmedDeliveryTime;
    }

    public String getConfirmedReceivedBy() {
        return confirmedReceivedBy;
    }

    public void setConfirmedReceivedBy(String confirmedReceivedBy) {
        this.confirmedReceivedBy = confirmedReceivedBy;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
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

    public Long getContactPersonId() {
        return contactPersonId;
    }

    public void setContactPersonId(Long peopleId) {
        this.contactPersonId = peopleId;
    }

    public String getContactPersonFullName() {
        return contactPersonFullName;
    }

    public void setContactPersonFullName(String peopleFullName) {
        this.contactPersonFullName = peopleFullName;
    }

    public Long getSalespersonPersonId() {
        return salespersonPersonId;
    }

    public void setSalespersonPersonId(Long peopleId) {
        this.salespersonPersonId = peopleId;
    }

    public String getSalespersonPersonFullName() {
        return salespersonPersonFullName;
    }

    public void setSalespersonPersonFullName(String peopleFullName) {
        this.salespersonPersonFullName = peopleFullName;
    }

    public Long getPackedByPersonId() {
        return packedByPersonId;
    }

    public void setPackedByPersonId(Long peopleId) {
        this.packedByPersonId = peopleId;
    }

    public String getPackedByPersonFullName() {
        return packedByPersonFullName;
    }

    public void setPackedByPersonFullName(String peopleFullName) {
        this.packedByPersonFullName = peopleFullName;
    }

    public Long getAccountsPersonId() {
        return accountsPersonId;
    }

    public void setAccountsPersonId(Long peopleId) {
        this.accountsPersonId = peopleId;
    }

    public String getAccountsPersonFullName() {
        return accountsPersonFullName;
    }

    public void setAccountsPersonFullName(String peopleFullName) {
        this.accountsPersonFullName = peopleFullName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public Long getBillToCustomerId() {
        return billToCustomerId;
    }

    public void setBillToCustomerId(Long customersId) {
        this.billToCustomerId = customersId;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodsId) {
        this.deliveryMethodId = deliveryMethodsId;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public void setDeliveryMethodName(String deliveryMethodsName) {
        this.deliveryMethodName = deliveryMethodsName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long ordersId) {
        this.orderId = ordersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvoicesDTO invoicesDTO = (InvoicesDTO) o;
        if (invoicesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoicesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoicesDTO{" +
            "id=" + getId() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", customerPurchaseOrderNumber='" + getCustomerPurchaseOrderNumber() + "'" +
            ", isCreditNote='" + isIsCreditNote() + "'" +
            ", creditNoteReason='" + getCreditNoteReason() + "'" +
            ", comments='" + getComments() + "'" +
            ", deliveryInstructions='" + getDeliveryInstructions() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", totalDryItems=" + getTotalDryItems() +
            ", totalChillerItems=" + getTotalChillerItems() +
            ", deliveryRun='" + getDeliveryRun() + "'" +
            ", runPosition='" + getRunPosition() + "'" +
            ", returnedDeliveryData='" + getReturnedDeliveryData() + "'" +
            ", confirmedDeliveryTime='" + getConfirmedDeliveryTime() + "'" +
            ", confirmedReceivedBy='" + getConfirmedReceivedBy() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", contactPerson=" + getContactPersonId() +
            ", contactPerson='" + getContactPersonFullName() + "'" +
            ", salespersonPerson=" + getSalespersonPersonId() +
            ", salespersonPerson='" + getSalespersonPersonFullName() + "'" +
            ", packedByPerson=" + getPackedByPersonId() +
            ", packedByPerson='" + getPackedByPersonFullName() + "'" +
            ", accountsPerson=" + getAccountsPersonId() +
            ", accountsPerson='" + getAccountsPersonFullName() + "'" +
            ", customer=" + getCustomerId() +
            ", billToCustomer=" + getBillToCustomerId() +
            ", deliveryMethod=" + getDeliveryMethodId() +
            ", deliveryMethod='" + getDeliveryMethodName() + "'" +
            ", order=" + getOrderId() +
            "}";
    }
}
