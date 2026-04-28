package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.SubscriptionPlan;
import com.electrician.electrician_management.Repository.SubscriptionPlanRepository;
import com.electrician.electrician_management.service.SubscriptionPlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanServiceImpl(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }


    @Override
    public SubscriptionPlan addPlan(SubscriptionPlan plan) {
        return subscriptionPlanRepository.save(plan);
    }

    @Override
    public List<SubscriptionPlan> getAllPlans() {
        return subscriptionPlanRepository.findAll();
    }
}
