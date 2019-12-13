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
 * Criteria class for the {@link com.epmweb.server.domain.Countries} entity. This class is used
 * in {@link com.epmweb.server.web.rest.CountriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountriesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter formalName;

    private StringFilter isoAplha3Code;

    private IntegerFilter isoNumericCode;

    private StringFilter countryType;

    private LongFilter latestRecordedPopulation;

    private StringFilter continent;

    private StringFilter region;

    private StringFilter subregion;

    private StringFilter border;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    public CountriesCriteria(){
    }

    public CountriesCriteria(CountriesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.formalName = other.formalName == null ? null : other.formalName.copy();
        this.isoAplha3Code = other.isoAplha3Code == null ? null : other.isoAplha3Code.copy();
        this.isoNumericCode = other.isoNumericCode == null ? null : other.isoNumericCode.copy();
        this.countryType = other.countryType == null ? null : other.countryType.copy();
        this.latestRecordedPopulation = other.latestRecordedPopulation == null ? null : other.latestRecordedPopulation.copy();
        this.continent = other.continent == null ? null : other.continent.copy();
        this.region = other.region == null ? null : other.region.copy();
        this.subregion = other.subregion == null ? null : other.subregion.copy();
        this.border = other.border == null ? null : other.border.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
    }

    @Override
    public CountriesCriteria copy() {
        return new CountriesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getFormalName() {
        return formalName;
    }

    public void setFormalName(StringFilter formalName) {
        this.formalName = formalName;
    }

    public StringFilter getIsoAplha3Code() {
        return isoAplha3Code;
    }

    public void setIsoAplha3Code(StringFilter isoAplha3Code) {
        this.isoAplha3Code = isoAplha3Code;
    }

    public IntegerFilter getIsoNumericCode() {
        return isoNumericCode;
    }

    public void setIsoNumericCode(IntegerFilter isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
    }

    public StringFilter getCountryType() {
        return countryType;
    }

    public void setCountryType(StringFilter countryType) {
        this.countryType = countryType;
    }

    public LongFilter getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public void setLatestRecordedPopulation(LongFilter latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
    }

    public StringFilter getContinent() {
        return continent;
    }

    public void setContinent(StringFilter continent) {
        this.continent = continent;
    }

    public StringFilter getRegion() {
        return region;
    }

    public void setRegion(StringFilter region) {
        this.region = region;
    }

    public StringFilter getSubregion() {
        return subregion;
    }

    public void setSubregion(StringFilter subregion) {
        this.subregion = subregion;
    }

    public StringFilter getBorder() {
        return border;
    }

    public void setBorder(StringFilter border) {
        this.border = border;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CountriesCriteria that = (CountriesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(formalName, that.formalName) &&
            Objects.equals(isoAplha3Code, that.isoAplha3Code) &&
            Objects.equals(isoNumericCode, that.isoNumericCode) &&
            Objects.equals(countryType, that.countryType) &&
            Objects.equals(latestRecordedPopulation, that.latestRecordedPopulation) &&
            Objects.equals(continent, that.continent) &&
            Objects.equals(region, that.region) &&
            Objects.equals(subregion, that.subregion) &&
            Objects.equals(border, that.border) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        formalName,
        isoAplha3Code,
        isoNumericCode,
        countryType,
        latestRecordedPopulation,
        continent,
        region,
        subregion,
        border,
        validFrom,
        validTo
        );
    }

    @Override
    public String toString() {
        return "CountriesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (formalName != null ? "formalName=" + formalName + ", " : "") +
                (isoAplha3Code != null ? "isoAplha3Code=" + isoAplha3Code + ", " : "") +
                (isoNumericCode != null ? "isoNumericCode=" + isoNumericCode + ", " : "") +
                (countryType != null ? "countryType=" + countryType + ", " : "") +
                (latestRecordedPopulation != null ? "latestRecordedPopulation=" + latestRecordedPopulation + ", " : "") +
                (continent != null ? "continent=" + continent + ", " : "") +
                (region != null ? "region=" + region + ", " : "") +
                (subregion != null ? "subregion=" + subregion + ", " : "") +
                (border != null ? "border=" + border + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
            "}";
    }

}
