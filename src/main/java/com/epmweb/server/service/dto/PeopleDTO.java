package com.epmweb.server.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.epmweb.server.domain.enumeration.Gender;

/**
 * A DTO for the {@link com.epmweb.server.domain.People} entity.
 */
public class PeopleDTO implements Serializable {

    private Long id;

    @NotNull
    private String fullName;

    @NotNull
    private String preferredName;

    @NotNull
    private String searchName;

    @NotNull
    private Gender gender;

    @NotNull
    private Boolean isPermittedToLogon;

    private String logonName;

    @NotNull
    private Boolean isExternalLogonProvider;

    @NotNull
    private Boolean isSystemUser;

    @NotNull
    private Boolean isEmployee;

    @NotNull
    private Boolean isSalesPerson;

    @NotNull
    private Boolean isGuestUser;

    @NotNull
    private Integer emailPromotion;

    private String userPreferences;

    private String phoneNumber;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String emailAddress;

    private String photo;

    private String customFields;

    private String otherLanguages;

    @NotNull
    private Instant validFrom;

    @NotNull
    private Instant validTo;


    private String userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean isIsPermittedToLogon() {
        return isPermittedToLogon;
    }

    public void setIsPermittedToLogon(Boolean isPermittedToLogon) {
        this.isPermittedToLogon = isPermittedToLogon;
    }

    public String getLogonName() {
        return logonName;
    }

    public void setLogonName(String logonName) {
        this.logonName = logonName;
    }

    public Boolean isIsExternalLogonProvider() {
        return isExternalLogonProvider;
    }

    public void setIsExternalLogonProvider(Boolean isExternalLogonProvider) {
        this.isExternalLogonProvider = isExternalLogonProvider;
    }

    public Boolean isIsSystemUser() {
        return isSystemUser;
    }

    public void setIsSystemUser(Boolean isSystemUser) {
        this.isSystemUser = isSystemUser;
    }

    public Boolean isIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(Boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public Boolean isIsSalesPerson() {
        return isSalesPerson;
    }

    public void setIsSalesPerson(Boolean isSalesPerson) {
        this.isSalesPerson = isSalesPerson;
    }

    public Boolean isIsGuestUser() {
        return isGuestUser;
    }

    public void setIsGuestUser(Boolean isGuestUser) {
        this.isGuestUser = isGuestUser;
    }

    public Integer getEmailPromotion() {
        return emailPromotion;
    }

    public void setEmailPromotion(Integer emailPromotion) {
        this.emailPromotion = emailPromotion;
    }

    public String getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(String userPreferences) {
        this.userPreferences = userPreferences;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCustomFields() {
        return customFields;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getOtherLanguages() {
        return otherLanguages;
    }

    public void setOtherLanguages(String otherLanguages) {
        this.otherLanguages = otherLanguages;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PeopleDTO peopleDTO = (PeopleDTO) o;
        if (peopleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), peopleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeopleDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", preferredName='" + getPreferredName() + "'" +
            ", searchName='" + getSearchName() + "'" +
            ", gender='" + getGender() + "'" +
            ", isPermittedToLogon='" + isIsPermittedToLogon() + "'" +
            ", logonName='" + getLogonName() + "'" +
            ", isExternalLogonProvider='" + isIsExternalLogonProvider() + "'" +
            ", isSystemUser='" + isIsSystemUser() + "'" +
            ", isEmployee='" + isIsEmployee() + "'" +
            ", isSalesPerson='" + isIsSalesPerson() + "'" +
            ", isGuestUser='" + isIsGuestUser() + "'" +
            ", emailPromotion=" + getEmailPromotion() +
            ", userPreferences='" + getUserPreferences() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", customFields='" + getCustomFields() + "'" +
            ", otherLanguages='" + getOtherLanguages() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", user='" + getUserId() + "'" +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
