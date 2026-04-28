package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.Quotation;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuotationService {

    Quotation uploadQuotation(Long workId,
                              MultipartFile file,
                              String remarks);

    List<Quotation> getQuotationsByWork(Long workId);
    Resource downloadQuotation(Long quotationId);

}
