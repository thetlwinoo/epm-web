package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.Countries} entity.
 */
public class CountriesDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String formalName;

    private String isoAplha3Code;

    private Integer isoNumericCode;

    private String countryType;

    private Long latestRecordedPopulation;

    @NotNull
    private String continent;

    @NotNull
    private String region;

    @NotNull
    private String subregion;

    private String border;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public String getIsoAplha3Code() {
        return isoAplha3Code;
    }

    public void setIsoAplha3Code(String isoAplha3Code) {
        this.isoAplha3Code = isoAplha3Code;
    }

    public Integer getIsoNumericCode() {
        return isoNumericCode;
    }

    public void setIsoNumericCode(Integer isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
    }

    public String getCountryType() {
        return countryType;
    }

    public void setCountryType(String countryType) {
        this.countryType = countryType;
    }

    public Long getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public void setLatestRecordedPopulation(Long latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
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

        CountriesDTO countriesDTO = (CountriesDTO) o;
        if (countriesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countriesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountriesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", formalName='" + getFormalName() + "'" +
            ", isoAplha3Code='" + getIsoAplha3Code() + "'" +
            ", isoNumericCode=" + getIsoNumericCode() +
            ", countryType='" + getCountryType() + "'" +
            ", latestRecordedPopulation=" + getLatestRecordedPopulation() +
            ", continent='" + getContinent() + "'" +
            ", region='" + getRegion() + "'" +
            ", subregion='" + getSubregion() + "'" +
            ", border='" + getBorder() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
