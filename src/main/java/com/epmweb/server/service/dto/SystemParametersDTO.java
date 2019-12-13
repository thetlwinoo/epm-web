package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.SystemParameters} entity.
 */
public class SystemParametersDTO implements Serializable {

    private Long id;

    @NotNull
    private String applicationSettings;


    private Long deliveryCityId;

    private String deliveryCityName;

    private Long postalCityId;

    private String postalCityName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationSettings() {
        return applicationSettings;
    }

    public void setApplicationSettings(String applicationSettings) {
        this.applicationSettings = applicationSettings;
    }

    public Long getDeliveryCityId() {
        return deliveryCityId;
    }

    public void setDeliveryCityId(Long citiesId) {
        this.deliveryCityId = citiesId;
    }

    public String getDeliveryCityName() {
        return deliveryCityName;
    }

    public void setDeliveryCityName(String citiesName) {
        this.deliveryCityName = citiesName;
    }

    public Long getPostalCityId() {
        return postalCityId;
    }

    public void setPostalCityId(Long citiesId) {
        this.postalCityId = citiesId;
    }

    public String getPostalCityName() {
        return postalCityName;
    }

    public void setPostalCityName(String citiesName) {
        this.postalCityName = citiesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SystemParametersDTO systemParametersDTO = (SystemParametersDTO) o;
        if (systemParametersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemParametersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemParametersDTO{" +
            "id=" + getId() +
            ", applicationSettings='" + getApplicationSettings() + "'" +
            ", deliveryCity=" + getDeliveryCityId() +
            ", deliveryCity='" + getDeliveryCityName() + "'" +
            ", postalCity=" + getPostalCityId() +
            ", postalCity='" + getPostalCityName() + "'" +
            "}";
    }
}
