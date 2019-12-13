package com.epmweb.server.web.rest;

import com.epmweb.server.service.InvoicesService;
import com.epmweb.server.web.rest.errors.BadRequestAlertException;
import com.epmweb.server.service.dto.InvoicesDTO;
import com.epmweb.server.service.dto.InvoicesCriteria;
import com.epmweb.server.service.InvoicesQueryService;

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
 * REST controller for managing {@link com.epmweb.server.domain.Invoices}.
 */
@RestController
@RequestMapping("/api")
public class InvoicesResource {

    private final Logger log = LoggerFactory.getLogger(InvoicesResource.class);

    private static final String ENTITY_NAME = "invoices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoicesService invoicesService;

    private final InvoicesQueryService invoicesQueryService;

    public InvoicesResource(InvoicesService invoicesService, InvoicesQueryService invoicesQueryService) {
        this.invoicesService = invoicesService;
        this.invoicesQueryService = invoicesQueryService;
    }

    /**
     * {@code POST  /invoices} : Create a new invoices.
     *
     * @param invoicesDTO the invoicesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoicesDTO, or with status {@code 400 (Bad Request)} if the invoices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoices")
    public ResponseEntity<InvoicesDTO> createInvoices(@Valid @RequestBody InvoicesDTO invoicesDTO) throws URISyntaxException {
        log.debug("REST request to save Invoices : {}", invoicesDTO);
        if (invoicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoicesDTO result = invoicesService.save(invoicesDTO);
        return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoices} : Updates an existing invoices.
     *
     * @param invoicesDTO the invoicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoicesDTO,
     * or with status {@code 400 (Bad Request)} if the invoicesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoices")
    public ResponseEntity<InvoicesDTO> updateInvoices(@Valid @RequestBody InvoicesDTO invoicesDTO) throws URISyntaxException {
        log.debug("REST request to update Invoices : {}", invoicesDTO);
        if (invoicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoicesDTO result = invoicesService.save(invoicesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invoices} : get all the invoices.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoices in body.
     */
    @GetMapping("/invoices")
    public ResponseEntity<List<InvoicesDTO>> getAllInvoices(InvoicesCriteria criteria) {
        log.debug("REST request to get Invoices by criteria: {}", criteria);
        List<InvoicesDTO> entityList = invoicesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /invoices/count} : count all the invoices.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/invoices/count")
    public ResponseEntity<Long> countInvoices(InvoicesCriteria criteria) {
        log.debug("REST request to count Invoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(invoicesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invoices/:id} : get the "id" invoices.
     *
     * @param id the id of the invoicesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoicesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoices/{id}")
    public ResponseEntity<InvoicesDTO> getInvoices(@PathVariable Long id) {
        log.debug("REST request to get Invoices : {}", id);
        Optional<InvoicesDTO> invoicesDTO = invoicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoicesDTO);
    }

    /**
     * {@code DELETE  /invoices/:id} : delete the "id" invoices.
     *
     * @param id the id of the invoicesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoices(@PathVariable Long id) {
        log.debug("REST request to delete Invoices : {}", id);
        invoicesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
