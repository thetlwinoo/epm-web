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
 * Criteria class for the {@link com.epmweb.server.domain.Addresses} entity. This class is used
 * in {@link com.epmweb.server.web.rest.AddressesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter contactPerson;

    private StringFilter contactNumber;

    private StringFilter contactEmailAddress;

    private StringFilter addressLine1;

    private StringFilter addressLine2;

    private StringFilter city;

    private StringFilter postalCode;

    private BooleanFilter defaultInd;

    private BooleanFilter activeInd;

    private LongFilter stateProvinceId;

    private LongFilter addressTypeId;

    private LongFilter personId;

    public AddressesCriteria(){
    }

    public AddressesCriteria(AddressesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.contactPerson = other.contactPerson == null ? null : other.contactPerson.copy();
        this.contactNumber = other.contactNumber == null ? null : other.contactNumber.copy();
        this.contactEmailAddress = other.contactEmailAddress == null ? null : other.contactEmailAddress.copy();
        this.addressLine1 = other.addressLine1 == null ? null : other.addressLine1.copy();
        this.addressLine2 = other.addressLine2 == null ? null : other.addressLine2.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.defaultInd = other.defaultInd == null ? null : other.defaultInd.copy();
        this.activeInd = other.activeInd == null ? null : other.activeInd.copy();
        this.stateProvinceId = other.stateProvinceId == null ? null : other.stateProvinceId.copy();
        this.addressTypeId = other.addressTypeId == null ? null : other.addressTypeId.copy();
        this.personId = other.personId == null ? null : other.personId.copy();
    }

    @Override
    public AddressesCriteria copy() {
        return new AddressesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(StringFilter contactPerson) {
        this.contactPerson = contactPerson;
    }

    public StringFilter getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(StringFilter contactNumber) {
        this.contactNumber = contactNumber;
    }

    public StringFilter getContactEmailAddress() {
        return contactEmailAddress;
    }

    public void setContactEmailAddress(StringFilter contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public StringFilter getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(StringFilter addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public StringFilter getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(StringFilter addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public BooleanFilter getDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(BooleanFilter defaultInd) {
        this.defaultInd = defaultInd;
    }

    public BooleanFilter getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(BooleanFilter activeInd) {
        this.activeInd = activeInd;
    }

    public LongFilter getStateProvinceId() {
        return stateProvinceId;
    }

    public void setStateProvinceId(LongFilter stateProvinceId) {
        this.stateProvinceId = stateProvinceId;
    }

    public LongFilter getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(LongFilter addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public LongFilter getPersonId() {
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AddressesCriteria that = (AddressesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(contactPerson, that.contactPerson) &&
            Objects.equals(contactNumber, that.contactNumber) &&
            Objects.equals(contactEmailAddress, that.contactEmailAddress) &&
            Objects.equals(addressLine1, that.addressLine1) &&
            Objects.equals(addressLine2, that.addressLine2) &&
            Objects.equals(city, that.city) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(defaultInd, that.defaultInd) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(stateProvinceId, that.stateProvinceId) &&
            Objects.equals(addressTypeId, that.addressTypeId) &&
            Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        contactPerson,
        contactNumber,
        contactEmailAddress,
        addressLine1,
        addressLine2,
        city,
        postalCode,
        defaultInd,
        activeInd,
        stateProvinceId,
        addressTypeId,
        personId
        );
    }

    @Override
    public String toString() {
        return "AddressesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (contactPerson != null ? "contactPerson=" + contactPerson + ", " : "") +
                (contactNumber != null ? "contactNumber=" + contactNumber + ", " : "") +
                (contactEmailAddress != null ? "contactEmailAddress=" + contactEmailAddress + ", " : "") +
                (addressLine1 != null ? "addressLine1=" + addressLine1 + ", " : "") +
                (addressLine2 != null ? "addressLine2=" + addressLine2 + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
                (defaultInd != null ? "defaultInd=" + defaultInd + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (stateProvinceId != null ? "stateProvinceId=" + stateProvinceId + ", " : "") +
                (addressTypeId != null ? "addressTypeId=" + addressTypeId + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
            "}";
    }

}
