package com.epmweb.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A ColdRoomTemperatures.
 */
@Entity
@Table(name = "cold_room_temperatures")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ColdRoomTemperatures implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cold_room_sensor_number", nullable = false)
    private Integer coldRoomSensorNumber;

    @NotNull
    @Column(name = "recorded_when", nullable = false)
    private Instant recordedWhen;

    @NotNull
    @Column(name = "temperature", precision = 21, scale = 2, nullable = false)
    private BigDecimal temperature;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getColdRoomSensorNumber() {
        return coldRoomSensorNumber;
    }

    public ColdRoomTemperatures coldRoomSensorNumber(Integer coldRoomSensorNumber) {
        this.coldRoomSensorNumber = coldRoomSensorNumber;
        return this;
    }

    public void setColdRoomSensorNumber(Integer coldRoomSensorNumber) {
        this.coldRoomSensorNumber = coldRoomSensorNumber;
    }

    public Instant getRecordedWhen() {
        return recordedWhen;
    }

    public ColdRoomTemperatures recordedWhen(Instant recordedWhen) {
        this.recordedWhen = recordedWhen;
        return this;
    }

    public void setRecordedWhen(Instant recordedWhen) {
        this.recordedWhen = recordedWhen;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public ColdRoomTemperatures temperature(BigDecimal temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public ColdRoomTemperatures validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public ColdRoomTemperatures validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColdRoomTemperatures)) {
            return false;
        }
        return id != null && id.equals(((ColdRoomTemperatures) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ColdRoomTemperatures{" +
            "id=" + getId() +
            ", coldRoomSensorNumber=" + getColdRoomSensorNumber() +
            ", recordedWhen='" + getRecordedWhen() + "'" +
            ", temperature=" + getTemperature() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
