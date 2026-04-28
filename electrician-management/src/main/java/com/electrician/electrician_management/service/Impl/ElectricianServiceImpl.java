package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.Electrician;
import com.electrician.electrician_management.Entity.SubscriptionPlan;
import com.electrician.electrician_management.Repository.ElectricianRepository;
import com.electrician.electrician_management.Repository.SubscriptionPlanRepository;
import com.electrician.electrician_management.service.ElectricianService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ElectricianServiceImpl implements ElectricianService {

    private final ElectricianRepository electricianRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public ElectricianServiceImpl(ElectricianRepository electricianRepository,
                                  SubscriptionPlanRepository subscriptionPlanRepository) {
        this.electricianRepository = electricianRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    //  ADMIN ONLY
    @Override
    public Electrician addElectrician(Electrician electrician) {

        electrician.setSubscriptionPlan(null);
        electrician.setMembershipStart(null);
        electrician.setMembershipEnd(null);
        electrician.setStatus("CREATED");

        return electricianRepository.save(electrician);
    }

    @Override
    public Electrician loginByPhone(String phone) {
        return electricianRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new RuntimeException("Electrician not found with this phone number"));
    }


    //  ELECTRICIAN SUBSCRIPTION
    @Override
    public Electrician subscribePlan(Long electricianId, Long planId) {

        Electrician electrician = electricianRepository.findById(electricianId)
                .orElseThrow(() -> new RuntimeException("Electrician not found"));

        SubscriptionPlan plan = subscriptionPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        LocalDate today = LocalDate.now();

        electrician.setSubscriptionPlan(plan);
        electrician.setMembershipStart(today);
        electrician.setMembershipEnd(today.plusDays(plan.getDurationDays()));
        electrician.setStatus("ACTIVE");

        return electricianRepository.save(electrician);
    }

    @Override
    public List<Electrician> getAllElectricians() {
        return electricianRepository.findAll();
    }

    @Override
    public List<Electrician> getActiveElectricians() {
        return electricianRepository.findByStatus("ACTIVE");
    }

    @Override
    public List<Electrician> getExpiredElectricians() {
        return electricianRepository.findByStatus("EXPIRED");
    }

    @Override
    public Electrician getById(Long id) {
        return electricianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Electrician not found"));
    }
}
