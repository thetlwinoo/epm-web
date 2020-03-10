package com.epmweb.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.epmweb.server.domain.enumeration.Gender;

/**
 * A People.
 */
@Entity
@Table(name = "people")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class People implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "preferred_name", nullable = false)
    private String preferredName;

    @NotNull
    @Column(name = "search_name", nullable = false)
    private String searchName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Column(name = "is_permitted_to_logon", nullable = false)
    private Boolean isPermittedToLogon;

    @Column(name = "logon_name")
    private String logonName;

    @NotNull
    @Column(name = "is_external_logon_provider", nullable = false)
    private Boolean isExternalLogonProvider;

    @NotNull
    @Column(name = "is_system_user", nullable = false)
    private Boolean isSystemUser;

    @NotNull
    @Column(name = "is_employee", nullable = false)
    private Boolean isEmployee;

    @NotNull
    @Column(name = "is_sales_person", nullable = false)
    private Boolean isSalesPerson;

    @NotNull
    @Column(name = "is_guest_user", nullable = false)
    private Boolean isGuestUser;

    @NotNull
    @Column(name = "email_promotion", nullable = false)
    private Integer emailPromotion;

    @Column(name = "user_preferences")
    private String userPreferences;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "photo")
    private String photo;

    @Column(name = "custom_fields")
    private String customFields;

    @Column(name = "other_languages")
    private String otherLanguages;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToOne(mappedBy = "cartUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private ShoppingCarts cart;

    @OneToOne(mappedBy = "wishlistUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Wishlists wishlist;

    @OneToOne(mappedBy = "compareUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Compares compare;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public People fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public People preferredName(String preferredName) {
        this.preferredName = preferredName;
        return this;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getSearchName() {
        return searchName;
    }

    public People searchName(String searchName) {
        this.searchName = searchName;
        return this;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Gender getGender() {
        return gender;
    }

    public People gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean isIsPermittedToLogon() {
        return isPermittedToLogon;
    }

    public People isPermittedToLogon(Boolean isPermittedToLogon) {
        this.isPermittedToLogon = isPermittedToLogon;
        return this;
    }

    public void setIsPermittedToLogon(Boolean isPermittedToLogon) {
        this.isPermittedToLogon = isPermittedToLogon;
    }

    public String getLogonName() {
        return logonName;
    }

    public People logonName(String logonName) {
        this.logonName = logonName;
        return this;
    }

    public void setLogonName(String logonName) {
        this.logonName = logonName;
    }

    public Boolean isIsExternalLogonProvider() {
        return isExternalLogonProvider;
    }

    public People isExternalLogonProvider(Boolean isExternalLogonProvider) {
        this.isExternalLogonProvider = isExternalLogonProvider;
        return this;
    }

    public void setIsExternalLogonProvider(Boolean isExternalLogonProvider) {
        this.isExternalLogonProvider = isExternalLogonProvider;
    }

    public Boolean isIsSystemUser() {
        return isSystemUser;
    }

    public People isSystemUser(Boolean isSystemUser) {
        this.isSystemUser = isSystemUser;
        return this;
    }

    public void setIsSystemUser(Boolean isSystemUser) {
        this.isSystemUser = isSystemUser;
    }

    public Boolean isIsEmployee() {
        return isEmployee;
    }

    public People isEmployee(Boolean isEmployee) {
        this.isEmployee = isEmployee;
        return this;
    }

    public void setIsEmployee(Boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public Boolean isIsSalesPerson() {
        return isSalesPerson;
    }

    public People isSalesPerson(Boolean isSalesPerson) {
        this.isSalesPerson = isSalesPerson;
        return this;
    }

    public void setIsSalesPerson(Boolean isSalesPerson) {
        this.isSalesPerson = isSalesPerson;
    }

    public Boolean isIsGuestUser() {
        return isGuestUser;
    }

    public People isGuestUser(Boolean isGuestUser) {
        this.isGuestUser = isGuestUser;
        return this;
    }

    public void setIsGuestUser(Boolean isGuestUser) {
        this.isGuestUser = isGuestUser;
    }

    public Integer getEmailPromotion() {
        return emailPromotion;
    }

    public People emailPromotion(Integer emailPromotion) {
        this.emailPromotion = emailPromotion;
        return this;
    }

    public void setEmailPromotion(Integer emailPromotion) {
        this.emailPromotion = emailPromotion;
    }

    public String getUserPreferences() {
        return userPreferences;
    }

    public People userPreferences(String userPreferences) {
        this.userPreferences = userPreferences;
        return this;
    }

    public void setUserPreferences(String userPreferences) {
        this.userPreferences = userPreferences;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public People phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public People emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoto() {
        return photo;
    }

    public People photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCustomFields() {
        return customFields;
    }

    public People customFields(String customFields) {
        this.customFields = customFields;
        return this;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getOtherLanguages() {
        return otherLanguages;
    }

    public People otherLanguages(String otherLanguages) {
        this.otherLanguages = otherLanguages;
        return this;
    }

    public void setOtherLanguages(String otherLanguages) {
        this.otherLanguages = otherLanguages;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public People validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public People validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public User getUser() {
        return user;
    }

    public People user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ShoppingCarts getCart() {
        return cart;
    }

    public People cart(ShoppingCarts shoppingCarts) {
        this.cart = shoppingCarts;
        return this;
    }

    public void setCart(ShoppingCarts shoppingCarts) {
        this.cart = shoppingCarts;
    }

    public Wishlists getWishlist() {
        return wishlist;
    }

    public People wishlist(Wishlists wishlists) {
        this.wishlist = wishlists;
        return this;
    }

    public void setWishlist(Wishlists wishlists) {
        this.wishlist = wishlists;
    }

    public Compares getCompare() {
        return compare;
    }

    public People compare(Compares compares) {
        this.compare = compares;
        return this;
    }

    public void setCompare(Compares compares) {
        this.compare = compares;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof People)) {
            return false;
        }
        return id != null && id.equals(((People) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "People{" +
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
            "}";
    }
}
