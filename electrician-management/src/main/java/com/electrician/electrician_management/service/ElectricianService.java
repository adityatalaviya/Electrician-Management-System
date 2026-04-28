package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.Electrician;

import java.util.List;

public interface ElectricianService {

    //Admin use
    Electrician addElectrician(Electrician electrician);

    //electrician login
    Electrician loginByPhone(String phone);

    //electrician use
    Electrician subscribePlan(Long electricianId, Long planId);

    List<Electrician> getAllElectricians();
    List<Electrician> getActiveElectricians();
    List<Electrician> getExpiredElectricians();

    Electrician getById(Long id);



}
