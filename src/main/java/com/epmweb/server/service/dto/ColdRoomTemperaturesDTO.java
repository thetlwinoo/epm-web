package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.ColdRoomTemperatures} entity.
 */
public class ColdRoomTemperaturesDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer coldRoomSensorNumber;

    @NotNull
    private Instant recordedWhen;

    @NotNull
    private BigDecimal temperature;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validTo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getColdRoomSensorNumber() {
        return coldRoomSensorNumber;
    }

    public void setColdRoomSensorNumber(Integer coldRoomSensorNumber) {
        this.coldRoomSensorNumber = coldRoomSensorNumber;
    }

    public Instant getRecordedWhen() {
        return recordedWhen;
    }

    public void setRecordedWhen(Instant recordedWhen) {
        this.recordedWhen = recordedWhen;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
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

        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = (ColdRoomTemperaturesDTO) o;
        if (coldRoomTemperaturesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coldRoomTemperaturesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ColdRoomTemperaturesDTO{" +
            "id=" + getId() +
            ", coldRoomSensorNumber=" + getColdRoomSensorNumber() +
            ", recordedWhen='" + getRecordedWhen() + "'" +
            ", temperature=" + getTemperature() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
