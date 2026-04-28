package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.FinalBill;
import com.electrician.electrician_management.dto.FinalBillSummary;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface FinalBillService {

    FinalBill createFinalBill(Long workId,
                              Double amount,
                              LocalDate billDate,
                              String remarks,
                              MultipartFile billFile);

    FinalBillSummary getFinalBillSummary(Long workId);

    Resource downloadFinalBill(Long workId);

}
