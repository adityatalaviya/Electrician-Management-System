package com.electrician.electrician_management.Repository;

import com.electrician.electrician_management.Entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuotationRepository extends JpaRepository <Quotation,Long>{

    List<Quotation> findByWorkDetailsId(Long workId);
}
