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
 * Criteria class for the {@link com.epmweb.server.domain.BusinessEntityContact} entity. This class is used
 * in {@link com.epmweb.server.web.rest.BusinessEntityContactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /business-entity-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BusinessEntityContactCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter personId;

    private LongFilter contactTypeId;

    public BusinessEntityContactCriteria(){
    }

    public BusinessEntityContactCriteria(BusinessEntityContactCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.personId = other.personId == null ? null : other.personId.copy();
        this.contactTypeId = other.contactTypeId == null ? null : other.contactTypeId.copy();
    }

    @Override
    public BusinessEntityContactCriteria copy() {
        return new BusinessEntityContactCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPersonId() {
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }

    public LongFilter getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(LongFilter contactTypeId) {
        this.contactTypeId = contactTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BusinessEntityContactCriteria that = (BusinessEntityContactCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(personId, that.personId) &&
            Objects.equals(contactTypeId, that.contactTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        personId,
        contactTypeId
        );
    }

    @Override
    public String toString() {
        return "BusinessEntityContactCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
                (contactTypeId != null ? "contactTypeId=" + contactTypeId + ", " : "") +
            "}";
    }

}
