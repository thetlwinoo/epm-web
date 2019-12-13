package com.epmweb.server.repository;

import com.epmweb.server.domain.UploadTransactions;

import java.util.List;

public interface UploadTransactionsExtendRepository extends UploadTransactionsRepository {
    List<UploadTransactions> findAllBySupplierId(Long supplierId);
}
