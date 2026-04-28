package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.SubscriptionPlan;

import java.util.List;

public interface SubscriptionPlanService {

    SubscriptionPlan addPlan(SubscriptionPlan plan);
    List<SubscriptionPlan> getAllPlans();
}
