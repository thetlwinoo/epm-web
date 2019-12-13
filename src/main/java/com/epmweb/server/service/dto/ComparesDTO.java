package com.epmweb.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epmweb.server.domain.Compares} entity.
 */
public class ComparesDTO implements Serializable {

    private Long id;


    private Long compareUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompareUserId() {
        return compareUserId;
    }

    public void setCompareUserId(Long peopleId) {
        this.compareUserId = peopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComparesDTO comparesDTO = (ComparesDTO) o;
        if (comparesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comparesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComparesDTO{" +
            "id=" + getId() +
            ", compareUser=" + getCompareUserId() +
            "}";
    }
}
