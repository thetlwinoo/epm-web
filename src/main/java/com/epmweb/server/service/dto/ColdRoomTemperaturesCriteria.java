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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.epmweb.server.domain.ColdRoomTemperatures} entity. This class is used
 * in {@link com.epmweb.server.web.rest.ColdRoomTemperaturesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cold-room-temperatures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ColdRoomTemperaturesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter coldRoomSensorNumber;

    private InstantFilter recordedWhen;

    private BigDecimalFilter temperature;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    public ColdRoomTemperaturesCriteria(){
    }

    public ColdRoomTemperaturesCriteria(ColdRoomTemperaturesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.coldRoomSensorNumber = other.coldRoomSensorNumber == null ? null : other.coldRoomSensorNumber.copy();
        this.recordedWhen = other.recordedWhen == null ? null : other.recordedWhen.copy();
        this.temperature = other.temperature == null ? null : other.temperature.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
    }

    @Override
    public ColdRoomTemperaturesCriteria copy() {
        return new ColdRoomTemperaturesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getColdRoomSensorNumber() {
        return coldRoomSensorNumber;
    }

    public void setColdRoomSensorNumber(IntegerFilter coldRoomSensorNumber) {
        this.coldRoomSensorNumber = coldRoomSensorNumber;
    }

    public InstantFilter getRecordedWhen() {
        return recordedWhen;
    }

    public void setRecordedWhen(InstantFilter recordedWhen) {
        this.recordedWhen = recordedWhen;
    }

    public BigDecimalFilter getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimalFilter temperature) {
        this.temperature = temperature;
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
        final ColdRoomTemperaturesCriteria that = (ColdRoomTemperaturesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(coldRoomSensorNumber, that.coldRoomSensorNumber) &&
            Objects.equals(recordedWhen, that.recordedWhen) &&
            Objects.equals(temperature, that.temperature) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        coldRoomSensorNumber,
        recordedWhen,
        temperature,
        validFrom,
        validTo
        );
    }

    @Override
    public String toString() {
        return "ColdRoomTemperaturesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (coldRoomSensorNumber != null ? "coldRoomSensorNumber=" + coldRoomSensorNumber + ", " : "") +
                (recordedWhen != null ? "recordedWhen=" + recordedWhen + ", " : "") +
                (temperature != null ? "temperature=" + temperature + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
            "}";
    }

}
