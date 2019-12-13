package com.epmweb.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.epmweb.server.domain.enumeration.Gender;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.epmweb.server.domain.People} entity. This class is used
 * in {@link com.epmweb.server.web.rest.PeopleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /people?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PeopleCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter preferredName;

    private StringFilter searchName;

    private GenderFilter gender;

    private BooleanFilter isPermittedToLogon;

    private StringFilter logonName;

    private BooleanFilter isExternalLogonProvider;

    private BooleanFilter isSystemUser;

    private BooleanFilter isEmployee;

    private BooleanFilter isSalesPerson;

    private BooleanFilter isGuestUser;

    private IntegerFilter emailPromotion;

    private StringFilter userPreferences;

    private StringFilter phoneNumber;

    private StringFilter emailAddress;

    private StringFilter photo;

    private StringFilter customFields;

    private StringFilter otherLanguages;

    private InstantFilter validFrom;

    private InstantFilter validTo;

    private StringFilter userId;

    private LongFilter cartId;

    private LongFilter wishlistId;

    private LongFilter compareId;

    public PeopleCriteria(){
    }

    public PeopleCriteria(PeopleCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.preferredName = other.preferredName == null ? null : other.preferredName.copy();
        this.searchName = other.searchName == null ? null : other.searchName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.isPermittedToLogon = other.isPermittedToLogon == null ? null : other.isPermittedToLogon.copy();
        this.logonName = other.logonName == null ? null : other.logonName.copy();
        this.isExternalLogonProvider = other.isExternalLogonProvider == null ? null : other.isExternalLogonProvider.copy();
        this.isSystemUser = other.isSystemUser == null ? null : other.isSystemUser.copy();
        this.isEmployee = other.isEmployee == null ? null : other.isEmployee.copy();
        this.isSalesPerson = other.isSalesPerson == null ? null : other.isSalesPerson.copy();
        this.isGuestUser = other.isGuestUser == null ? null : other.isGuestUser.copy();
        this.emailPromotion = other.emailPromotion == null ? null : other.emailPromotion.copy();
        this.userPreferences = other.userPreferences == null ? null : other.userPreferences.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.emailAddress = other.emailAddress == null ? null : other.emailAddress.copy();
        this.photo = other.photo == null ? null : other.photo.copy();
        this.customFields = other.customFields == null ? null : other.customFields.copy();
        this.otherLanguages = other.otherLanguages == null ? null : other.otherLanguages.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.cartId = other.cartId == null ? null : other.cartId.copy();
        this.wishlistId = other.wishlistId == null ? null : other.wishlistId.copy();
        this.compareId = other.compareId == null ? null : other.compareId.copy();
    }

    @Override
    public PeopleCriteria copy() {
        return new PeopleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(StringFilter preferredName) {
        this.preferredName = preferredName;
    }

    public StringFilter getSearchName() {
        return searchName;
    }

    public void setSearchName(StringFilter searchName) {
        this.searchName = searchName;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public BooleanFilter getIsPermittedToLogon() {
        return isPermittedToLogon;
    }

    public void setIsPermittedToLogon(BooleanFilter isPermittedToLogon) {
        this.isPermittedToLogon = isPermittedToLogon;
    }

    public StringFilter getLogonName() {
        return logonName;
    }

    public void setLogonName(StringFilter logonName) {
        this.logonName = logonName;
    }

    public BooleanFilter getIsExternalLogonProvider() {
        return isExternalLogonProvider;
    }

    public void setIsExternalLogonProvider(BooleanFilter isExternalLogonProvider) {
        this.isExternalLogonProvider = isExternalLogonProvider;
    }

    public BooleanFilter getIsSystemUser() {
        return isSystemUser;
    }

    public void setIsSystemUser(BooleanFilter isSystemUser) {
        this.isSystemUser = isSystemUser;
    }

    public BooleanFilter getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(BooleanFilter isEmployee) {
        this.isEmployee = isEmployee;
    }

    public BooleanFilter getIsSalesPerson() {
        return isSalesPerson;
    }

    public void setIsSalesPerson(BooleanFilter isSalesPerson) {
        this.isSalesPerson = isSalesPerson;
    }

    public BooleanFilter getIsGuestUser() {
        return isGuestUser;
    }

    public void setIsGuestUser(BooleanFilter isGuestUser) {
        this.isGuestUser = isGuestUser;
    }

    public IntegerFilter getEmailPromotion() {
        return emailPromotion;
    }

    public void setEmailPromotion(IntegerFilter emailPromotion) {
        this.emailPromotion = emailPromotion;
    }

    public StringFilter getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(StringFilter userPreferences) {
        this.userPreferences = userPreferences;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(StringFilter emailAddress) {
        this.emailAddress = emailAddress;
    }

    public StringFilter getPhoto() {
        return photo;
    }

    public void setPhoto(StringFilter photo) {
        this.photo = photo;
    }

    public StringFilter getCustomFields() {
        return customFields;
    }

    public void setCustomFields(StringFilter customFields) {
        this.customFields = customFields;
    }

    public StringFilter getOtherLanguages() {
        return otherLanguages;
    }

    public void setOtherLanguages(StringFilter otherLanguages) {
        this.otherLanguages = otherLanguages;
    }

    public InstantFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(InstantFilter validFrom) {
        this.validFrom = validFrom;
    }

    public InstantFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(InstantFilter validTo) {
        this.validTo = validTo;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public LongFilter getCartId() {
        return cartId;
    }

    public void setCartId(LongFilter cartId) {
        this.cartId = cartId;
    }

    public LongFilter getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(LongFilter wishlistId) {
        this.wishlistId = wishlistId;
    }

    public LongFilter getCompareId() {
        return compareId;
    }

    public void setCompareId(LongFilter compareId) {
        this.compareId = compareId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PeopleCriteria that = (PeopleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(preferredName, that.preferredName) &&
            Objects.equals(searchName, that.searchName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(isPermittedToLogon, that.isPermittedToLogon) &&
            Objects.equals(logonName, that.logonName) &&
            Objects.equals(isExternalLogonProvider, that.isExternalLogonProvider) &&
            Objects.equals(isSystemUser, that.isSystemUser) &&
            Objects.equals(isEmployee, that.isEmployee) &&
            Objects.equals(isSalesPerson, that.isSalesPerson) &&
            Objects.equals(isGuestUser, that.isGuestUser) &&
            Objects.equals(emailPromotion, that.emailPromotion) &&
            Objects.equals(userPreferences, that.userPreferences) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(emailAddress, that.emailAddress) &&
            Objects.equals(photo, that.photo) &&
            Objects.equals(customFields, that.customFields) &&
            Objects.equals(otherLanguages, that.otherLanguages) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(cartId, that.cartId) &&
            Objects.equals(wishlistId, that.wishlistId) &&
            Objects.equals(compareId, that.compareId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fullName,
        preferredName,
        searchName,
        gender,
        isPermittedToLogon,
        logonName,
        isExternalLogonProvider,
        isSystemUser,
        isEmployee,
        isSalesPerson,
        isGuestUser,
        emailPromotion,
        userPreferences,
        phoneNumber,
        emailAddress,
        photo,
        customFields,
        otherLanguages,
        validFrom,
        validTo,
        userId,
        cartId,
        wishlistId,
        compareId
        );
    }

    @Override
    public String toString() {
        return "PeopleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (preferredName != null ? "preferredName=" + preferredName + ", " : "") +
                (searchName != null ? "searchName=" + searchName + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (isPermittedToLogon != null ? "isPermittedToLogon=" + isPermittedToLogon + ", " : "") +
                (logonName != null ? "logonName=" + logonName + ", " : "") +
                (isExternalLogonProvider != null ? "isExternalLogonProvider=" + isExternalLogonProvider + ", " : "") +
                (isSystemUser != null ? "isSystemUser=" + isSystemUser + ", " : "") +
                (isEmployee != null ? "isEmployee=" + isEmployee + ", " : "") +
                (isSalesPerson != null ? "isSalesPerson=" + isSalesPerson + ", " : "") +
                (isGuestUser != null ? "isGuestUser=" + isGuestUser + ", " : "") +
                (emailPromotion != null ? "emailPromotion=" + emailPromotion + ", " : "") +
                (userPreferences != null ? "userPreferences=" + userPreferences + ", " : "") +
                (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
                (emailAddress != null ? "emailAddress=" + emailAddress + ", " : "") +
                (photo != null ? "photo=" + photo + ", " : "") +
                (customFields != null ? "customFields=" + customFields + ", " : "") +
                (otherLanguages != null ? "otherLanguages=" + otherLanguages + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (cartId != null ? "cartId=" + cartId + ", " : "") +
                (wishlistId != null ? "wishlistId=" + wishlistId + ", " : "") +
                (compareId != null ? "compareId=" + compareId + ", " : "") +
            "}";
    }

}
