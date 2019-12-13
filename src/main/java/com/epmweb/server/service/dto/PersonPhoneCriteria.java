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
 * Criteria class for the {@link com.epmweb.server.domain.PersonPhone} entity. This class is used
 * in {@link com.epmweb.server.web.rest.PersonPhoneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /person-phones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonPhoneCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phoneNumber;

    private BooleanFilter defaultInd;

    private BooleanFilter activeInd;

    private LongFilter personId;

    private LongFilter phoneNumberTypeId;

    public PersonPhoneCriteria(){
    }

    public PersonPhoneCriteria(PersonPhoneCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.defaultInd = other.defaultInd == null ? null : other.defaultInd.copy();
        this.activeInd = other.activeInd == null ? null : other.activeInd.copy();
        this.personId = other.personId == null ? null : other.personId.copy();
        this.phoneNumberTypeId = other.phoneNumberTypeId == null ? null : other.phoneNumberTypeId.copy();
    }

    @Override
    public PersonPhoneCriteria copy() {
        return new PersonPhoneCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public LongFilter getPersonId() {
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }

    public LongFilter getPhoneNumberTypeId() {
        return phoneNumberTypeId;
    }

    public void setPhoneNumberTypeId(LongFilter phoneNumberTypeId) {
        this.phoneNumberTypeId = phoneNumberTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonPhoneCriteria that = (PersonPhoneCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(defaultInd, that.defaultInd) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(personId, that.personId) &&
            Objects.equals(phoneNumberTypeId, that.phoneNumberTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        phoneNumber,
        defaultInd,
        activeInd,
        personId,
        phoneNumberTypeId
        );
    }

    @Override
    public String toString() {
        return "PersonPhoneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (defaultInd != null ? "defaultInd=" + defaultInd + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
                (phoneNumberTypeId != null ? "phoneNumberTypeId=" + phoneNumberTypeId + ", " : "") +
            "}";
    }

}
