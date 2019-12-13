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
 * Criteria class for the {@link com.epmweb.server.domain.Compares} entity. This class is used
 * in {@link com.epmweb.server.web.rest.ComparesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /compares?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ComparesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter compareUserId;

    private LongFilter compareListId;

    public ComparesCriteria(){
    }

    public ComparesCriteria(ComparesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.compareUserId = other.compareUserId == null ? null : other.compareUserId.copy();
        this.compareListId = other.compareListId == null ? null : other.compareListId.copy();
    }

    @Override
    public ComparesCriteria copy() {
        return new ComparesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCompareUserId() {
        return compareUserId;
    }

    public void setCompareUserId(LongFilter compareUserId) {
        this.compareUserId = compareUserId;
    }

    public LongFilter getCompareListId() {
        return compareListId;
    }

    public void setCompareListId(LongFilter compareListId) {
        this.compareListId = compareListId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ComparesCriteria that = (ComparesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(compareUserId, that.compareUserId) &&
            Objects.equals(compareListId, that.compareListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        compareUserId,
        compareListId
        );
    }

    @Override
    public String toString() {
        return "ComparesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (compareUserId != null ? "compareUserId=" + compareUserId + ", " : "") +
                (compareListId != null ? "compareListId=" + compareListId + ", " : "") +
            "}";
    }

}
