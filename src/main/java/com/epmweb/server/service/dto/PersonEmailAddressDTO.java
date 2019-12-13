package com.epmweb.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.PersonEmailAddress} entity.
 */
public class PersonEmailAddressDTO implements Serializable {

    private Long id;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String emailAddress;

    private Boolean defaultInd;

    private Boolean activeInd;


    private Long personId;

    private String personFullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonEmailAddressDTO personEmailAddressDTO = (PersonEmailAddressDTO) o;
        if (personEmailAddressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personEmailAddressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonEmailAddressDTO{" +
            "id=" + getId() +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", person=" + getPersonId() +
            ", person='" + getPersonFullName() + "'" +
            "}";
    }
}
