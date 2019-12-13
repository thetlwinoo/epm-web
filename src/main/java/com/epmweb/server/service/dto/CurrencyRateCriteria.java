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
 * Criteria class for the {@link com.epmweb.server.domain.CurrencyRate} entity. This class is used
 * in {@link com.epmweb.server.web.rest.CurrencyRateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /currency-rates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CurrencyRateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter currencyRateDate;

    private StringFilter fromcode;

    private StringFilter tocode;

    private BigDecimalFilter averageRate;

    private BigDecimalFilter endOfDayRate;

    private StringFilter lastEditedBy;

    private InstantFilter lastEditedWhen;

    public CurrencyRateCriteria(){
    }

    public CurrencyRateCriteria(CurrencyRateCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.currencyRateDate = other.currencyRateDate == null ? null : other.currencyRateDate.copy();
        this.fromcode = other.fromcode == null ? null : other.fromcode.copy();
        this.tocode = other.tocode == null ? null : other.tocode.copy();
        this.averageRate = other.averageRate == null ? null : other.averageRate.copy();
        this.endOfDayRate = other.endOfDayRate == null ? null : other.endOfDayRate.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
    }

    @Override
    public CurrencyRateCriteria copy() {
        return new CurrencyRateCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCurrencyRateDate() {
        return currencyRateDate;
    }

    public void setCurrencyRateDate(InstantFilter currencyRateDate) {
        this.currencyRateDate = currencyRateDate;
    }

    public StringFilter getFromcode() {
        return fromcode;
    }

    public void setFromcode(StringFilter fromcode) {
        this.fromcode = fromcode;
    }

    public StringFilter getTocode() {
        return tocode;
    }

    public void setTocode(StringFilter tocode) {
        this.tocode = tocode;
    }

    public BigDecimalFilter getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(BigDecimalFilter averageRate) {
        this.averageRate = averageRate;
    }

    public BigDecimalFilter getEndOfDayRate() {
        return endOfDayRate;
    }

    public void setEndOfDayRate(BigDecimalFilter endOfDayRate) {
        this.endOfDayRate = endOfDayRate;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public InstantFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(InstantFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurrencyRateCriteria that = (CurrencyRateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(currencyRateDate, that.currencyRateDate) &&
            Objects.equals(fromcode, that.fromcode) &&
            Objects.equals(tocode, that.tocode) &&
            Objects.equals(averageRate, that.averageRate) &&
            Objects.equals(endOfDayRate, that.endOfDayRate) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        currencyRateDate,
        fromcode,
        tocode,
        averageRate,
        endOfDayRate,
        lastEditedBy,
        lastEditedWhen
        );
    }

    @Override
    public String toString() {
        return "CurrencyRateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (currencyRateDate != null ? "currencyRateDate=" + currencyRateDate + ", " : "") +
                (fromcode != null ? "fromcode=" + fromcode + ", " : "") +
                (tocode != null ? "tocode=" + tocode + ", " : "") +
                (averageRate != null ? "averageRate=" + averageRate + ", " : "") +
                (endOfDayRate != null ? "endOfDayRate=" + endOfDayRate + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
            "}";
    }

}
