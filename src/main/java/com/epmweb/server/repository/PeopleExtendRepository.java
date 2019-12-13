package com.epmweb.server.repository;

import com.epmweb.server.domain.People;

import java.util.Optional;

public interface PeopleExtendRepository extends PeopleRepository {
    Optional<People> findPeopleByEmailAddress(String email);
}
