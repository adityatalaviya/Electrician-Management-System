package com.electrician.electrician_management.dto;

import lombok.Data;

@Data
public class FinalBillSummary {

    private Long workId;
    private String ownerName;
    private String siteLocation;

    private Double finalBillAmount;
    private Double totalPaidByOwner;
    private Double remainingAmount;

    private String billFileName;
}
