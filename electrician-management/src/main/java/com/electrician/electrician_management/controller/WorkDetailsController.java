package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.Entity.WorkDetails;
import com.electrician.electrician_management.service.WorkDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electrician/work")
public class WorkDetailsController {

    private final WorkDetailsService workService;

    public WorkDetailsController(WorkDetailsService workService) {
        this.workService = workService;
    }

    @PostMapping("/add/{electricianId}")
    public WorkDetails addWork(@RequestBody WorkDetails workDetails,
                               @PathVariable Long electricianId) {
        return workService.addWork(workDetails, electricianId);
    }

    @GetMapping("/list/{electricianId}")
    public List<WorkDetails> getWorks(@PathVariable Long electricianId) {
        return workService.getWorksByElectrician(electricianId);
    }

    @PutMapping("/complete/{workId}")
    public WorkDetails completeWork(@PathVariable Long workId) {
        return workService.markWorkCompleted(workId);
    }
}
