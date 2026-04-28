package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.Quotation;
import com.electrician.electrician_management.Entity.WorkDetails;
import com.electrician.electrician_management.Repository.QuotationRepository;
import com.electrician.electrician_management.Repository.WorkDetailsRepository;
import com.electrician.electrician_management.service.QuotationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;
    private final WorkDetailsRepository workRepository;

    // folder where files will be stored
    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "quotations";


    public QuotationServiceImpl(QuotationRepository quotationRepository,
                                WorkDetailsRepository workRepository) {
        this.quotationRepository = quotationRepository;
        this.workRepository = workRepository;
    }

    @Override
    public Quotation uploadQuotation(Long workId,
                                     MultipartFile file,
                                     String remarks) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        WorkDetails work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("Could not create upload directory");
        }

        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = UPLOAD_DIR + File.separator + uniqueFileName;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        Quotation quotation = new Quotation();
        quotation.setFileName(file.getOriginalFilename());
        quotation.setFilePath(filePath);
        quotation.setFileType(file.getContentType());
        quotation.setCreatedDate(LocalDate.now());
        quotation.setRemarks(remarks);
        quotation.setWorkDetails(work);

        return quotationRepository.save(quotation);
    }


    @Override
    public List<Quotation> getQuotationsByWork(Long workId) {
        return quotationRepository.findByWorkDetailsId(workId);
    }

    @Override
    public Resource downloadQuotation(Long quotationId) {

        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new RuntimeException("Quotation not found"));

        try {
            Path path = Paths.get(quotation.getFilePath());
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("File not found");
            }

            return resource;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error while downloading file");
        }
    }

}
