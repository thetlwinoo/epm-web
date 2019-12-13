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

/**
 * Criteria class for the {@link com.epmweb.server.domain.BusinessEntityAddress} entity. This class is used
 * in {@link com.epmweb.server.web.rest.BusinessEntityAddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /business-entity-addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BusinessEntityAddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter addressId;

    private LongFilter personId;

    private LongFilter addressTypeId;

    public BusinessEntityAddressCriteria(){
    }

    public BusinessEntityAddressCriteria(BusinessEntityAddressCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.personId = other.personId == null ? null : other.personId.copy();
        this.addressTypeId = other.addressTypeId == null ? null : other.addressTypeId.copy();
    }

    @Override
    public BusinessEntityAddressCriteria copy() {
        return new BusinessEntityAddressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
    }

    public LongFilter getPersonId() {
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }

    public LongFilter getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(LongFilter addressTypeId) {
        this.addressTypeId = addressTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BusinessEntityAddressCriteria that = (BusinessEntityAddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(personId, that.personId) &&
            Objects.equals(addressTypeId, that.addressTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        addressId,
        personId,
        addressTypeId
        );
    }

    @Override
    public String toString() {
        return "BusinessEntityAddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (addressId != null ? "addressId=" + addressId + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
                (addressTypeId != null ? "addressTypeId=" + addressTypeId + ", " : "") +
            "}";
    }

}
