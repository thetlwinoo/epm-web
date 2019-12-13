package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.VehicleTemperatures} entity.
 */
public class VehicleTemperaturesDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer vehicleRegistration;

    @NotNull
    private String chillerSensorNumber;

    @NotNull
    private Integer recordedWhen;

    @NotNull
    private BigDecimal temperature;

    @NotNull
    private Boolean isCompressed;

    private String fullSensorData;

    private String compressedSensorData;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(Integer vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }

    public String getChillerSensorNumber() {
        return chillerSensorNumber;
    }

    public void setChillerSensorNumber(String chillerSensorNumber) {
        this.chillerSensorNumber = chillerSensorNumber;
    }

    public Integer getRecordedWhen() {
        return recordedWhen;
    }

    public void setRecordedWhen(Integer recordedWhen) {
        this.recordedWhen = recordedWhen;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public Boolean isIsCompressed() {
        return isCompressed;
    }

    public void setIsCompressed(Boolean isCompressed) {
        this.isCompressed = isCompressed;
    }

    public String getFullSensorData() {
        return fullSensorData;
    }

    public void setFullSensorData(String fullSensorData) {
        this.fullSensorData = fullSensorData;
    }

    public String getCompressedSensorData() {
        return compressedSensorData;
    }

    public void setCompressedSensorData(String compressedSensorData) {
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

        VehicleTemperaturesDTO vehicleTemperaturesDTO = (VehicleTemperaturesDTO) o;
        if (vehicleTemperaturesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleTemperaturesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleTemperaturesDTO{" +
            "id=" + getId() +
            ", vehicleRegistration=" + getVehicleRegistration() +
            ", chillerSensorNumber='" + getChillerSensorNumber() + "'" +
            ", recordedWhen=" + getRecordedWhen() +
            ", temperature=" + getTemperature() +
            ", isCompressed='" + isIsCompressed() + "'" +
            ", fullSensorData='" + getFullSensorData() + "'" +
            ", compressedSensorData='" + getCompressedSensorData() + "'" +
            "}";
    }
}
