package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.Entity.Electrician;
import com.electrician.electrician_management.service.ElectricianService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/electrician")
public class ElectricianController {

    private final ElectricianService electricianService;

    public ElectricianController(ElectricianService electricianService) {
        this.electricianService = electricianService;
    }

    //  ADMIN: create electrician (NO PLAN)
    @PostMapping("/add")
    public Electrician addElectrician(@RequestBody Electrician electrician) {
        return electricianService.addElectrician(electrician);
    }

    // ELECTRICIAN: subscribe plan
    @PostMapping("/subscribe/{electricianId}/{planId}")
    public Electrician subscribe(@PathVariable Long electricianId,
                                 @PathVariable Long planId) {
        return electricianService.subscribePlan(electricianId, planId);
    }

    @GetMapping("/all")
    public List<Electrician> getAll() {
        return electricianService.getAllElectricians();
    }

    @GetMapping("/active")
    public List<Electrician> getActive() {
        return electricianService.getActiveElectricians();
    }

    @GetMapping("/expired")
    public List<Electrician> getExpired() {
        return electricianService.getExpiredElectricians();
    }
}
