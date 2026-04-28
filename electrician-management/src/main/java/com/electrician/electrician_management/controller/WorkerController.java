package com.electrician.electrician_management.controller;

import com.electrician.electrician_management.Entity.Worker;
import com.electrician.electrician_management.service.WorkerService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electrician/worker")
public class  WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/login/{phone}")
    public Worker workerLogin(@PathVariable String phone) {
        return workerService.getWorkerByPhone(phone);
    }


    @PostMapping("/add/{electricianId}")
    public Worker addWorker(@RequestBody Worker worker,
                            @PathVariable Long electricianId) {
        return workerService.addWorker(worker, electricianId);
    }

    @GetMapping("/list/{electricianId}")
    public List<Worker> getWorkers(@PathVariable Long electricianId) {
        return workerService.getWorkersByElectrician(electricianId);
    }

    @GetMapping("/{workerId}")
    public Worker getWorkerById(@PathVariable Long workerId) {
        return workerService.getWorkerById(workerId);
    }


    @PutMapping("/update/{workerId}")
    public Worker updateWorker(@PathVariable Long workerId,
                               @RequestBody Worker worker) {
        return workerService.updateWorker(workerId, worker);
    }

    // ✅ DELETE
    @DeleteMapping("/delete/{workerId}")
    public void deleteWorker(@PathVariable Long workerId) {
        workerService.deleteWorker(workerId);
    }





}
