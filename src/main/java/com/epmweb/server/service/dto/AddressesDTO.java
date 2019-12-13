package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.Addresses} entity.
 */
public class AddressesDTO implements Serializable {

    private Long id;

    @NotNull
    private String contactPerson;

    @NotNull
    private String contactNumber;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String contactEmailAddress;

    @NotNull
    private String addressLine1;

    private String addressLine2;

    private String city;

    private String postalCode;

    private Boolean defaultInd;

    private Boolean activeInd;


    private Long stateProvinceId;

    private String stateProvinceName;

    private Long addressTypeId;

    private String addressTypeName;

    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactEmailAddress() {
        return contactEmailAddress;
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public Long getStateProvinceId() {
        return stateProvinceId;
    }

    public void setStateProvinceId(Long stateProvincesId) {
        this.stateProvinceId = stateProvincesId;
    }

    public String getStateProvinceName() {
        return stateProvinceName;
    }

    public void setStateProvinceName(String stateProvincesName) {
        this.stateProvinceName = stateProvincesName;
    }

    public Long getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Long addressTypesId) {
        this.addressTypeId = addressTypesId;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public void setAddressTypeName(String addressTypesName) {
        this.addressTypeName = addressTypesName;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long peopleId) {
        this.personId = peopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressesDTO addressesDTO = (AddressesDTO) o;
        if (addressesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressesDTO{" +
            "id=" + getId() +
            ", contactPerson='" + getContactPerson() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", contactEmailAddress='" + getContactEmailAddress() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", city='" + getCity() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", stateProvince=" + getStateProvinceId() +
            ", stateProvince='" + getStateProvinceName() + "'" +
            ", addressType=" + getAddressTypeId() +
            ", addressType='" + getAddressTypeName() + "'" +
            ", person=" + getPersonId() +
            "}";
    }
}
