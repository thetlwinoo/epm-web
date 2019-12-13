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

/**
 * Criteria class for the {@link com.epmweb.server.domain.SystemParameters} entity. This class is used
 * in {@link com.epmweb.server.web.rest.SystemParametersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /system-parameters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SystemParametersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter applicationSettings;

    private LongFilter deliveryCityId;

    private LongFilter postalCityId;

    public SystemParametersCriteria(){
    }

    public SystemParametersCriteria(SystemParametersCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.applicationSettings = other.applicationSettings == null ? null : other.applicationSettings.copy();
        this.deliveryCityId = other.deliveryCityId == null ? null : other.deliveryCityId.copy();
        this.postalCityId = other.postalCityId == null ? null : other.postalCityId.copy();
    }

    @Override
    public SystemParametersCriteria copy() {
        return new SystemParametersCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getApplicationSettings() {
        return applicationSettings;
    }

    public void setApplicationSettings(StringFilter applicationSettings) {
        this.applicationSettings = applicationSettings;
    }

    public LongFilter getDeliveryCityId() {
        return deliveryCityId;
    }

    public void setDeliveryCityId(LongFilter deliveryCityId) {
        this.deliveryCityId = deliveryCityId;
    }

    public LongFilter getPostalCityId() {
        return postalCityId;
    }

    public void setPostalCityId(LongFilter postalCityId) {
        this.postalCityId = postalCityId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SystemParametersCriteria that = (SystemParametersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(applicationSettings, that.applicationSettings) &&
            Objects.equals(deliveryCityId, that.deliveryCityId) &&
            Objects.equals(postalCityId, that.postalCityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        applicationSettings,
        deliveryCityId,
        postalCityId
        );
    }

    @Override
    public String toString() {
        return "SystemParametersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (applicationSettings != null ? "applicationSettings=" + applicationSettings + ", " : "") +
                (deliveryCityId != null ? "deliveryCityId=" + deliveryCityId + ", " : "") +
                (postalCityId != null ? "postalCityId=" + postalCityId + ", " : "") +
            "}";
    }

}
