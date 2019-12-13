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
 * Criteria class for the {@link com.epmweb.server.domain.StateProvinces} entity. This class is used
 * in {@link com.epmweb.server.web.rest.StateProvincesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /state-provinces?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StateProvincesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter salesTerritory;

    private StringFilter border;

    private LongFilter latestRecordedPopulation;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private LongFilter countryId;

    public StateProvincesCriteria(){
    }

    public StateProvincesCriteria(StateProvincesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.salesTerritory = other.salesTerritory == null ? null : other.salesTerritory.copy();
        this.border = other.border == null ? null : other.border.copy();
        this.latestRecordedPopulation = other.latestRecordedPopulation == null ? null : other.latestRecordedPopulation.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
    }

    @Override
    public StateProvincesCriteria copy() {
        return new StateProvincesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSalesTerritory() {
        return salesTerritory;
    }

    public void setSalesTerritory(StringFilter salesTerritory) {
        this.salesTerritory = salesTerritory;
    }

    public StringFilter getBorder() {
        return border;
    }

    public void setBorder(StringFilter border) {
        this.border = border;
    }

    public LongFilter getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public void setLatestRecordedPopulation(LongFilter latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
    }

    public InstantFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(InstantFilter validFrom) {
        this.validFrom = validFrom;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StateProvincesCriteria that = (StateProvincesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(salesTerritory, that.salesTerritory) &&
            Objects.equals(border, that.border) &&
            Objects.equals(latestRecordedPopulation, that.latestRecordedPopulation) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(countryId, that.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        salesTerritory,
        border,
        latestRecordedPopulation,
        validFrom,
        validTo,
        countryId
        );
    }

    @Override
    public String toString() {
        return "StateProvincesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (salesTerritory != null ? "salesTerritory=" + salesTerritory + ", " : "") +
                (border != null ? "border=" + border + ", " : "") +
                (latestRecordedPopulation != null ? "latestRecordedPopulation=" + latestRecordedPopulation + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
            "}";
    }

}
