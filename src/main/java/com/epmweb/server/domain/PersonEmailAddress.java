package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PersonEmailAddress.
 */
@Entity
@Table(name = "person_email_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonEmailAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "default_ind")
    private Boolean defaultInd;

    @Column(name = "active_ind")
    private Boolean activeInd;

    @ManyToOne
    @JsonIgnoreProperties("personEmailAddresses")
    private People person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public PersonEmailAddress emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public PersonEmailAddress defaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
        return this;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public PersonEmailAddress activeInd(Boolean activeInd) {
        this.activeInd = activeInd;
        return this;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public People getPerson() {
        return person;
    }

    public PersonEmailAddress person(People people) {
        this.person = people;
        return this;
    }

    public void setPerson(People people) {
        this.person = people;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonEmailAddress)) {
            return false;
        }
        return id != null && id.equals(((PersonEmailAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PersonEmailAddress{" +
            "id=" + getId() +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            "}";
    }
}
