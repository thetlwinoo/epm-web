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
 * Criteria class for the {@link com.epmweb.server.domain.PersonEmailAddress} entity. This class is used
 * in {@link com.epmweb.server.web.rest.PersonEmailAddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /person-email-addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonEmailAddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter emailAddress;

    private BooleanFilter defaultInd;

    private BooleanFilter activeInd;

    private LongFilter personId;

    public PersonEmailAddressCriteria(){
    }

    public PersonEmailAddressCriteria(PersonEmailAddressCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.emailAddress = other.emailAddress == null ? null : other.emailAddress.copy();
        this.defaultInd = other.defaultInd == null ? null : other.defaultInd.copy();
        this.activeInd = other.activeInd == null ? null : other.activeInd.copy();
        this.personId = other.personId == null ? null : other.personId.copy();
    }

    @Override
    public PersonEmailAddressCriteria copy() {
        return new PersonEmailAddressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(StringFilter emailAddress) {
        this.emailAddress = emailAddress;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonEmailAddressCriteria that = (PersonEmailAddressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(emailAddress, that.emailAddress) &&
            Objects.equals(defaultInd, that.defaultInd) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        emailAddress,
        defaultInd,
        activeInd,
        personId
        );
    }

    @Override
    public String toString() {
        return "PersonEmailAddressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (emailAddress != null ? "emailAddress=" + emailAddress + ", " : "") +
                (defaultInd != null ? "defaultInd=" + defaultInd + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
            "}";
    }

}
