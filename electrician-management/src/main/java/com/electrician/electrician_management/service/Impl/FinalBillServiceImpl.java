package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.FinalBill;
import com.electrician.electrician_management.Entity.OwnerPayment;
import com.electrician.electrician_management.Entity.WorkDetails;
import com.electrician.electrician_management.Repository.FinalBillRepository;
import com.electrician.electrician_management.Repository.OwnerPaymentRepository;
import com.electrician.electrician_management.Repository.WorkDetailsRepository;
import com.electrician.electrician_management.dto.FinalBillSummary;
import com.electrician.electrician_management.service.FinalBillService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FinalBillServiceImpl implements FinalBillService {

    private static final String BILL_DIR =
            System.getProperty("user.dir")
                    + File.separator + "uploads"
                    + File.separator + "final-bills";

    private final FinalBillRepository finalBillRepository;
    private final WorkDetailsRepository workRepository;
    private final OwnerPaymentRepository ownerPaymentRepository;

    public FinalBillServiceImpl(FinalBillRepository finalBillRepository,
                                WorkDetailsRepository workRepository,
                                OwnerPaymentRepository ownerPaymentRepository) {
        this.finalBillRepository = finalBillRepository;
        this.workRepository = workRepository;
        this.ownerPaymentRepository = ownerPaymentRepository;
    }

    @Override
    public FinalBill createFinalBill(Long workId,
                                     Double amount,
                                     LocalDate billDate,
                                     String remarks,
                                     MultipartFile billFile) {

        WorkDetails work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));

        if (finalBillRepository.findByWorkDetailsId(workId).isPresent()) {
            throw new RuntimeException("Final bill already exists for this work");
        }

        if (amount == null || amount <= 0) {
            throw new RuntimeException("Final bill amount must be greater than zero");
        }

        String filePath = null;
        String fileName = null;
        String fileType = null;

        if (billFile != null && !billFile.isEmpty()) {

            File dir = new File(BILL_DIR);
            if (!dir.exists()) dir.mkdirs();

            fileName = billFile.getOriginalFilename();
            fileType = billFile.getContentType();

            String uniqueName = UUID.randomUUID() + "_" + fileName;
            filePath = BILL_DIR + File.separator + uniqueName;

            try {
                billFile.transferTo(new File(filePath));
            } catch (Exception e) {
                throw new RuntimeException("Bill upload failed");
            }
        }

        FinalBill bill = new FinalBill();
        bill.setWorkDetails(work);
        bill.setFinalAmount(amount);
        bill.setBillDate(billDate);
        bill.setRemarks(remarks);
        bill.setBillFileName(fileName);
        bill.setBillFilePath(filePath);
        bill.setBillFileType(fileType);

        return finalBillRepository.save(bill);
    }

    @Override
    public FinalBillSummary getFinalBillSummary(Long workId) {

        WorkDetails work = workRepository.findById(workId)
                .orElseThrow(() -> new RuntimeException("Work not found"));

        FinalBill bill = finalBillRepository.findByWorkDetailsId(workId).orElse(null);

        List<OwnerPayment> payments =
                ownerPaymentRepository.findByWorkDetailsId(workId);

        double totalPaid = payments.stream()
                .mapToDouble(OwnerPayment::getAmount)
                .sum();

        double finalAmount = 0;
        double remaining = 0;

        if (bill != null) {
            finalAmount = bill.getFinalAmount();
            remaining = finalAmount - totalPaid;
        }

        FinalBillSummary summary = new FinalBillSummary();

        summary.setWorkId(work.getId());
        summary.setOwnerName(work.getOwnerName());
        summary.setSiteLocation(work.getLocation());
        summary.setFinalBillAmount(finalAmount);
        summary.setTotalPaidByOwner(totalPaid);
        summary.setRemainingAmount(remaining);

        if (bill != null)
            summary.setBillFileName(bill.getBillFileName());

        return summary;
    }

    @Override
    public Resource downloadFinalBill(Long workId) {

        FinalBill bill = finalBillRepository.findByWorkDetailsId(workId)
                .orElseThrow(() -> new RuntimeException("Final bill not found"));

        try {

            Path path = Paths.get(bill.getBillFilePath()).normalize();

            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists())
                throw new RuntimeException("File not found");

            return resource;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error downloading file");
        }
    }
}