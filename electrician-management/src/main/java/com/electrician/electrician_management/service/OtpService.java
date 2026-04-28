package com.electrician.electrician_management.service;

public interface OtpService {

    void generateOtp(String phone);

    boolean verifyOtp(String phone, String otp);
}
