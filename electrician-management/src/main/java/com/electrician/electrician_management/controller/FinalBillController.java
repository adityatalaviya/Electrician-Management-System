package com.electrician.electrician_management.controller;


import com.electrician.electrician_management.Entity.FinalBill;
import com.electrician.electrician_management.dto.FinalBillSummary;
import com.electrician.electrician_management.service.FinalBillService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/electrician/final-bill")
public class FinalBillController {

    private final FinalBillService finalBillService;

    public FinalBillController(FinalBillService finalBillService) {
        this.finalBillService = finalBillService;
    }

    // Electrician creates final bill
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public FinalBill createFinalBill(@RequestParam Long workId,
                                     @RequestParam Double amount,
                                     @RequestParam String date,
                                     @RequestParam(required = false) String remarks,
                                     @RequestParam(required = false) MultipartFile billFile) {

        return finalBillService.createFinalBill(
                workId,
                amount,
                LocalDate.parse(date),
                remarks,
                billFile
        );
    }

    @GetMapping("/download/{workId}")
    public ResponseEntity<Resource> downloadFinalBill(@PathVariable Long workId) {


        Resource resource = finalBillService.downloadFinalBill(workId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .body(resource);
    }


    // Electrician / Site Owner views bill summary
    @GetMapping("/summary/{workId}")
    public FinalBillSummary getSummary(@PathVariable Long workId) {
        return finalBillService.getFinalBillSummary(workId);
    }
}
