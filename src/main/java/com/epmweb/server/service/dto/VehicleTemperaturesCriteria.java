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

/**
 * Criteria class for the {@link com.epmweb.server.domain.VehicleTemperatures} entity. This class is used
 * in {@link com.epmweb.server.web.rest.VehicleTemperaturesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-temperatures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleTemperaturesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter vehicleRegistration;

    private StringFilter chillerSensorNumber;

    private IntegerFilter recordedWhen;

    private BigDecimalFilter temperature;

    private BooleanFilter isCompressed;

    private StringFilter fullSensorData;

    private StringFilter compressedSensorData;

    public VehicleTemperaturesCriteria(){
    }

    public VehicleTemperaturesCriteria(VehicleTemperaturesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.vehicleRegistration = other.vehicleRegistration == null ? null : other.vehicleRegistration.copy();
        this.chillerSensorNumber = other.chillerSensorNumber == null ? null : other.chillerSensorNumber.copy();
        this.recordedWhen = other.recordedWhen == null ? null : other.recordedWhen.copy();
        this.temperature = other.temperature == null ? null : other.temperature.copy();
        this.isCompressed = other.isCompressed == null ? null : other.isCompressed.copy();
        this.fullSensorData = other.fullSensorData == null ? null : other.fullSensorData.copy();
        this.compressedSensorData = other.compressedSensorData == null ? null : other.compressedSensorData.copy();
    }

    @Override
    public VehicleTemperaturesCriteria copy() {
        return new VehicleTemperaturesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(IntegerFilter vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }

    public StringFilter getChillerSensorNumber() {
        return chillerSensorNumber;
    }

    public void setChillerSensorNumber(StringFilter chillerSensorNumber) {
        this.chillerSensorNumber = chillerSensorNumber;
    }

    public IntegerFilter getRecordedWhen() {
        return recordedWhen;
    }

    public void setRecordedWhen(IntegerFilter recordedWhen) {
        this.recordedWhen = recordedWhen;
    }

    public BigDecimalFilter getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimalFilter temperature) {
        this.temperature = temperature;
    }

    public BooleanFilter getIsCompressed() {
        return isCompressed;
    }

    public void setIsCompressed(BooleanFilter isCompressed) {
        this.isCompressed = isCompressed;
    }

    public StringFilter getFullSensorData() {
        return fullSensorData;
    }

    public void setFullSensorData(StringFilter fullSensorData) {
        this.fullSensorData = fullSensorData;
    }

    public StringFilter getCompressedSensorData() {
        return compressedSensorData;
    }

    public void setCompressedSensorData(StringFilter compressedSensorData) {
        this.compressedSensorData = compressedSensorData;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleTemperaturesCriteria that = (VehicleTemperaturesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(vehicleRegistration, that.vehicleRegistration) &&
            Objects.equals(chillerSensorNumber, that.chillerSensorNumber) &&
            Objects.equals(recordedWhen, that.recordedWhen) &&
            Objects.equals(temperature, that.temperature) &&
            Objects.equals(isCompressed, that.isCompressed) &&
            Objects.equals(fullSensorData, that.fullSensorData) &&
            Objects.equals(compressedSensorData, that.compressedSensorData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        vehicleRegistration,
        chillerSensorNumber,
        recordedWhen,
        temperature,
        isCompressed,
        fullSensorData,
        compressedSensorData
        );
    }

    @Override
    public String toString() {
        return "VehicleTemperaturesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (vehicleRegistration != null ? "vehicleRegistration=" + vehicleRegistration + ", " : "") +
                (chillerSensorNumber != null ? "chillerSensorNumber=" + chillerSensorNumber + ", " : "") +
                (recordedWhen != null ? "recordedWhen=" + recordedWhen + ", " : "") +
                (temperature != null ? "temperature=" + temperature + ", " : "") +
                (isCompressed != null ? "isCompressed=" + isCompressed + ", " : "") +
                (fullSensorData != null ? "fullSensorData=" + fullSensorData + ", " : "") +
                (compressedSensorData != null ? "compressedSensorData=" + compressedSensorData + ", " : "") +
            "}";
    }

}
