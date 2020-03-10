package com.epmweb.server.web.rest;

import com.epmweb.server.service.UploadTransactionsExtendService;
import com.epmweb.server.service.dto.UploadTransactionsDTO;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * UploadTransactionsExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class UploadTransactionsExtendResource {

    private final Logger log = LoggerFactory.getLogger(UploadTransactionsExtendResource.class);
    private final UploadTransactionsExtendService uploadTransactionsExtendService;
    private static final String ENTITY_NAME = "uploadTransactionsExtend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    public UploadTransactionsExtendResource(UploadTransactionsExtendService uploadTransactionsExtendService) {
        this.uploadTransactionsExtendService = uploadTransactionsExtendService;
    }

    @DeleteMapping("/upload-transactions-extend/{id}")
    public ResponseEntity<Void> deleteStockItemTemps(@PathVariable Long id) {
        log.debug("REST request to delete UploadTransactions : {}", id);
        try {
            uploadTransactionsExtendService.clearStockItemTemp(id);
        } catch (Exception ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "error");
        }

        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/upload-transactions-extend")
    public List<UploadTransactionsDTO> getAllUploadTransactions(Principal principal) {
        log.debug("REST request to get all UploadTransactions");
        return uploadTransactionsExtendService.findAll(principal);
    }
}
