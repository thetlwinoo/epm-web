package com.epmweb.server.web.rest;

import com.epmweb.server.service.InvoiceLinesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.InvoiceLinesDTO;
import com.epmweb.server.service.dto.InvoiceLinesCriteria;
import com.epmweb.server.service.InvoiceLinesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.epmweb.server.domain.InvoiceLines}.
 */
@RestController
@RequestMapping("/api")
public class InvoiceLinesResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceLinesResource.class);

    private static final String ENTITY_NAME = "invoiceLines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceLinesService invoiceLinesService;

    private final InvoiceLinesQueryService invoiceLinesQueryService;

    public InvoiceLinesResource(InvoiceLinesService invoiceLinesService, InvoiceLinesQueryService invoiceLinesQueryService) {
        this.invoiceLinesService = invoiceLinesService;
        this.invoiceLinesQueryService = invoiceLinesQueryService;
    }

    /**
     * {@code POST  /invoice-lines} : Create a new invoiceLines.
     *
     * @param invoiceLinesDTO the invoiceLinesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceLinesDTO, or with status {@code 400 (Bad Request)} if the invoiceLines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-lines")
    public ResponseEntity<InvoiceLinesDTO> createInvoiceLines(@Valid @RequestBody InvoiceLinesDTO invoiceLinesDTO) throws URISyntaxException {
        log.debug("REST request to save InvoiceLines : {}", invoiceLinesDTO);
        if (invoiceLinesDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoiceLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceLinesDTO result = invoiceLinesService.save(invoiceLinesDTO);
        return ResponseEntity.created(new URI("/api/invoice-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-lines} : Updates an existing invoiceLines.
     *
     * @param invoiceLinesDTO the invoiceLinesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceLinesDTO,
     * or with status {@code 400 (Bad Request)} if the invoiceLinesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceLinesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-lines")
    public ResponseEntity<InvoiceLinesDTO> updateInvoiceLines(@Valid @RequestBody InvoiceLinesDTO invoiceLinesDTO) throws URISyntaxException {
        log.debug("REST request to update InvoiceLines : {}", invoiceLinesDTO);
        if (invoiceLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceLinesDTO result = invoiceLinesService.save(invoiceLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceLinesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invoice-lines} : get all the invoiceLines.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceLines in body.
     */
    @GetMapping("/invoice-lines")
    public ResponseEntity<List<InvoiceLinesDTO>> getAllInvoiceLines(InvoiceLinesCriteria criteria) {
        log.debug("REST request to get InvoiceLines by criteria: {}", criteria);
        List<InvoiceLinesDTO> entityList = invoiceLinesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /invoice-lines/count} : count all the invoiceLines.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/invoice-lines/count")
    public ResponseEntity<Long> countInvoiceLines(InvoiceLinesCriteria criteria) {
        log.debug("REST request to count InvoiceLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(invoiceLinesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invoice-lines/:id} : get the "id" invoiceLines.
     *
     * @param id the id of the invoiceLinesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceLinesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-lines/{id}")
    public ResponseEntity<InvoiceLinesDTO> getInvoiceLines(@PathVariable Long id) {
        log.debug("REST request to get InvoiceLines : {}", id);
        Optional<InvoiceLinesDTO> invoiceLinesDTO = invoiceLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceLinesDTO);
    }

    /**
     * {@code DELETE  /invoice-lines/:id} : delete the "id" invoiceLines.
     *
     * @param id the id of the invoiceLinesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-lines/{id}")
    public ResponseEntity<Void> deleteInvoiceLines(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceLines : {}", id);
        invoiceLinesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
