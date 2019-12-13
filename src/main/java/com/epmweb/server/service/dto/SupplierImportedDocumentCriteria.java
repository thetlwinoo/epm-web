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
 * Criteria class for the {@link com.epmweb.server.domain.SupplierImportedDocument} entity. This class is used
 * in {@link com.epmweb.server.web.rest.SupplierImportedDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /supplier-imported-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplierImportedDocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    private LongFilter uploadTransactionId;

    public SupplierImportedDocumentCriteria(){
    }

    public SupplierImportedDocumentCriteria(SupplierImportedDocumentCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.uploadTransactionId = other.uploadTransactionId == null ? null : other.uploadTransactionId.copy();
    }

    @Override
    public SupplierImportedDocumentCriteria copy() {
        return new SupplierImportedDocumentCriteria(this);
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

    public LongFilter getUploadTransactionId() {
        return uploadTransactionId;
    }

    public void setUploadTransactionId(LongFilter uploadTransactionId) {
        this.uploadTransactionId = uploadTransactionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplierImportedDocumentCriteria that = (SupplierImportedDocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(uploadTransactionId, that.uploadTransactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        lastEditedBy,
        lastEditedWhen,
        uploadTransactionId
        );
    }

    @Override
    public String toString() {
        return "SupplierImportedDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (uploadTransactionId != null ? "uploadTransactionId=" + uploadTransactionId + ", " : "") +
            "}";
    }

}
