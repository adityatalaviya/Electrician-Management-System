package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.Entity.SubscriptionPlan;
import com.electrician.electrician_management.service.SubscriptionPlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/plan")
public class SubscriptionPlanController {

    private final SubscriptionPlanService planService;

    public SubscriptionPlanController(SubscriptionPlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/add")
    public SubscriptionPlan addPlan(@RequestBody SubscriptionPlan plan) {
        return planService.addPlan(plan);
    }

    @GetMapping("/all")
    public List<SubscriptionPlan> getAllPlans() {
        return planService.getAllPlans();
    }
}
