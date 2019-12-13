package com.epmweb.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Countries.
 */
@Entity
@Table(name = "countries")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Countries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "formal_name", nullable = false)
    private String formalName;

    @Column(name = "iso_aplha_3_code")
    private String isoAplha3Code;

    @Column(name = "iso_numeric_code")
    private Integer isoNumericCode;

    @Column(name = "country_type")
    private String countryType;

    @Column(name = "latest_recorded_population")
    private Long latestRecordedPopulation;

    @NotNull
    @Column(name = "continent", nullable = false)
    private String continent;

    @NotNull
    @Column(name = "region", nullable = false)
    private String region;

    @NotNull
    @Column(name = "subregion", nullable = false)
    private String subregion;

    @Column(name = "border")
    private String border;

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

    public String getName() {
        return name;
    }

    public Countries name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormalName() {
        return formalName;
    }

    public Countries formalName(String formalName) {
        this.formalName = formalName;
        return this;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public String getIsoAplha3Code() {
        return isoAplha3Code;
    }

    public Countries isoAplha3Code(String isoAplha3Code) {
        this.isoAplha3Code = isoAplha3Code;
        return this;
    }

    public void setIsoAplha3Code(String isoAplha3Code) {
        this.isoAplha3Code = isoAplha3Code;
    }

    public Integer getIsoNumericCode() {
        return isoNumericCode;
    }

    public Countries isoNumericCode(Integer isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
        return this;
    }

    public void setIsoNumericCode(Integer isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
    }

    public String getCountryType() {
        return countryType;
    }

    public Countries countryType(String countryType) {
        this.countryType = countryType;
        return this;
    }

    public void setCountryType(String countryType) {
        this.countryType = countryType;
    }

    public Long getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public Countries latestRecordedPopulation(Long latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
        return this;
    }

    public void setLatestRecordedPopulation(Long latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
    }

    public String getContinent() {
        return continent;
    }

    public Countries continent(String continent) {
        this.continent = continent;
        return this;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public Countries region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public Countries subregion(String subregion) {
        this.subregion = subregion;
        return this;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getBorder() {
        return border;
    }

    public Countries border(String border) {
        this.border = border;
        return this;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Countries validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Countries validTo(Instant validTo) {
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
        if (!(o instanceof Countries)) {
            return false;
        }
        return id != null && id.equals(((Countries) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Countries{" +
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
