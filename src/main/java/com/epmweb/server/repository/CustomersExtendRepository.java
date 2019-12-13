package com.epmweb.server.repository;

import com.epmweb.server.domain.Customers;

public interface CustomersExtendRepository extends CustomersRepository {
    Customers findCustomersByUserId(String userId);
}
