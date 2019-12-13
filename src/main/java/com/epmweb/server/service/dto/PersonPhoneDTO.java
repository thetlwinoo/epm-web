package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.PersonPhone} entity.
 */
public class PersonPhoneDTO implements Serializable {

    private Long id;

    @NotNull
    private String phoneNumber;

    private Boolean defaultInd;

    private Boolean activeInd;


    private Long personId;

    private String personFullName;

    private Long phoneNumberTypeId;

    private String phoneNumberTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long peopleId) {
        this.personId = peopleId;
    }

    public String getPersonFullName() {
        return personFullName;
    }

    public void setPersonFullName(String peopleFullName) {
        this.personFullName = peopleFullName;
    }

    public Long getPhoneNumberTypeId() {
        return phoneNumberTypeId;
    }

    public void setPhoneNumberTypeId(Long phoneNumberTypeId) {
        this.phoneNumberTypeId = phoneNumberTypeId;
    }

    public String getPhoneNumberTypeName() {
        return phoneNumberTypeName;
    }

    public void setPhoneNumberTypeName(String phoneNumberTypeName) {
        this.phoneNumberTypeName = phoneNumberTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonPhoneDTO personPhoneDTO = (PersonPhoneDTO) o;
        if (personPhoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personPhoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonPhoneDTO{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", person=" + getPersonId() +
            ", person='" + getPersonFullName() + "'" +
            ", phoneNumberType=" + getPhoneNumberTypeId() +
            ", phoneNumberType='" + getPhoneNumberTypeName() + "'" +
            "}";
    }
}
