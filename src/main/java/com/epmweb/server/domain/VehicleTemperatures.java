package com.epmweb.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A VehicleTemperatures.
 */
@Entity
@Table(name = "vehicle_temperatures")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VehicleTemperatures implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "vehicle_registration", nullable = false)
    private Integer vehicleRegistration;

    @NotNull
    @Column(name = "chiller_sensor_number", nullable = false)
    private String chillerSensorNumber;

    @NotNull
    @Column(name = "recorded_when", nullable = false)
    private Integer recordedWhen;

    @NotNull
    @Column(name = "temperature", precision = 21, scale = 2, nullable = false)
    private BigDecimal temperature;

    @NotNull
    @Column(name = "is_compressed", nullable = false)
    private Boolean isCompressed;

    @Column(name = "full_sensor_data")
    private String fullSensorData;

    @Column(name = "compressed_sensor_data")
    private String compressedSensorData;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVehicleRegistration() {
        return vehicleRegistration;
    }

    public VehicleTemperatures vehicleRegistration(Integer vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
        return this;
    }

    public void setVehicleRegistration(Integer vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }

    public String getChillerSensorNumber() {
        return chillerSensorNumber;
    }

    public VehicleTemperatures chillerSensorNumber(String chillerSensorNumber) {
        this.chillerSensorNumber = chillerSensorNumber;
        return this;
    }

    public void setChillerSensorNumber(String chillerSensorNumber) {
        this.chillerSensorNumber = chillerSensorNumber;
    }

    public Integer getRecordedWhen() {
        return recordedWhen;
    }

    public VehicleTemperatures recordedWhen(Integer recordedWhen) {
        this.recordedWhen = recordedWhen;
        return this;
    }

    public void setRecordedWhen(Integer recordedWhen) {
        this.recordedWhen = recordedWhen;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public VehicleTemperatures temperature(BigDecimal temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public Boolean isIsCompressed() {
        return isCompressed;
    }

    public VehicleTemperatures isCompressed(Boolean isCompressed) {
        this.isCompressed = isCompressed;
        return this;
    }

    public void setIsCompressed(Boolean isCompressed) {
        this.isCompressed = isCompressed;
    }

    public String getFullSensorData() {
        return fullSensorData;
    }

    public VehicleTemperatures fullSensorData(String fullSensorData) {
        this.fullSensorData = fullSensorData;
        return this;
    }

    public void setFullSensorData(String fullSensorData) {
        this.fullSensorData = fullSensorData;
    }

    public String getCompressedSensorData() {
        return compressedSensorData;
    }

    public VehicleTemperatures compressedSensorData(String compressedSensorData) {
        this.compressedSensorData = compressedSensorData;
        return this;
    }

    public void setCompressedSensorData(String compressedSensorData) {
        this.compressedSensorData = compressedSensorData;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleTemperatures)) {
            return false;
        }
        return id != null && id.equals(((VehicleTemperatures) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VehicleTemperatures{" +
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
