package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.Entity.Quotation;
import com.electrician.electrician_management.service.QuotationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/electrician/quotation")
public class QuotationController {

    private final QuotationService quotationService;

    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping(value = "/upload/{workId}", consumes = "multipart/form-data")
    public Quotation uploadQuotation(@PathVariable Long workId,
                                     @RequestParam("file") MultipartFile file,
                                     @RequestParam(value = "remarks", required = false)
                                     String remarks) {

        return quotationService.uploadQuotation(workId, file, remarks);
    }

    @GetMapping("/work/{workId}")
    public List<Quotation> getQuotations(@PathVariable Long workId) {
        return quotationService.getQuotationsByWork(workId);
    }

    @GetMapping("/download/{quotationId}")
    public ResponseEntity<Resource> downloadQuotation(@PathVariable Long quotationId) {

        Resource resource = quotationService.downloadQuotation(quotationId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
