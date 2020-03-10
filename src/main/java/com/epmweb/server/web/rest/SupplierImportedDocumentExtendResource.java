package com.epmweb.server.web.rest;

import com.epmweb.server.service.SupplierImportedDocumentExtendService;
import com.epmweb.server.service.dto.SupplierImportedDocumentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * SupplierImportedDocumentExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class SupplierImportedDocumentExtendResource {

    private final Logger log = LoggerFactory.getLogger(SupplierImportedDocumentExtendResource.class);
    private final SupplierImportedDocumentExtendService supplierImportedDocumentExtendService;

    public SupplierImportedDocumentExtendResource(SupplierImportedDocumentExtendService supplierImportedDocumentExtendService) {
        this.supplierImportedDocumentExtendService = supplierImportedDocumentExtendService;
    }

    @RequestMapping(value = "/supplier-imported-document-extend/download/{id}/{handle}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@PathVariable Long id, @PathVariable String handle) {
        Optional<SupplierImportedDocumentDTO> supplierImportedDocumentDTOOptional = supplierImportedDocumentExtendService.getOneByUploadTransactionId(id);
        byte[] file;
        HttpHeaders header = new HttpHeaders();
        switch (handle) {
            case "template":
                header.setContentType(MediaType.valueOf(supplierImportedDocumentDTOOptional.get().getImportedTemplateContentType()));
                header.setContentLength(supplierImportedDocumentDTOOptional.get().getImportedTemplate().length);
                file = supplierImportedDocumentDTOOptional.get().getImportedTemplate();
                break;
            default:
                header.setContentType(MediaType.valueOf(supplierImportedDocumentDTOOptional.get().getImportedTemplateContentType()));
                header.setContentLength(supplierImportedDocumentDTOOptional.get().getImportedTemplate().length);
                file = supplierImportedDocumentDTOOptional.get().getImportedTemplate();
        }

        header.set("Content-Disposition", "attachment; filename=" + handle.toString() + ".xlsx");

        return new ResponseEntity<>(file, header, HttpStatus.OK);
    }
}
