package com.electrician.electrician_management.service;

import com.electrician.electrician_management.Entity.Admin;

public interface Adminservice {

    Admin findByEmail(String email);

    Admin createAdmin(Admin admin);
}
