package com.epmweb.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.BusinessEntityAddress} entity.
 */
public class BusinessEntityAddressDTO implements Serializable {

    private Long id;


    private Long addressId;

    private Long personId;

    private Long addressTypeId;

    private String addressTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressesId) {
        this.addressId = addressesId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long peopleId) {
        this.personId = peopleId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessEntityAddressDTO businessEntityAddressDTO = (BusinessEntityAddressDTO) o;
        if (businessEntityAddressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessEntityAddressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessEntityAddressDTO{" +
            "id=" + getId() +
            ", address=" + getAddressId() +
            ", person=" + getPersonId() +
            ", addressType=" + getAddressTypeId() +
            ", addressType='" + getAddressTypeName() + "'" +
            "}";
    }
}
