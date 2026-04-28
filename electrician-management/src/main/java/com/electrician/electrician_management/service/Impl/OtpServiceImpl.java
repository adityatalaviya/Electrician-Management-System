package com.electrician.electrician_management.service.Impl;

import com.electrician.electrician_management.Entity.ElectricianOtp;
import com.electrician.electrician_management.Repository.ElectricianOtpRepo;
import com.electrician.electrician_management.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final ElectricianOtpRepo otpRepository ;

    @Override
    public void generateOtp(String phone) {
        String otp =
                String.valueOf(new Random().nextInt(900000) + 100000);

        ElectricianOtp electricianOtp =
                otpRepository.findByPhone(phone)
                        .orElse(new ElectricianOtp());

        electricianOtp.setPhone(phone);
        electricianOtp.setOtp(otp);
        electricianOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(electricianOtp);

        System.out.println(" OTP for " + phone + " is: " + otp);

    }

    @Override
    public boolean verifyOtp(String phone, String otp) {
        ElectricianOtp electricianOtp =
                otpRepository.findByPhone(phone)
                        .orElseThrow(() ->
                                new RuntimeException("OTP not found"));

        if (electricianOtp.getExpiryTime()
                .isBefore(LocalDateTime.now())) {
            return false;
        }

        boolean valid = electricianOtp.getOtp().equals(otp);

        if(valid){
            otpRepository.delete(electricianOtp); // ⭐ remove after login
        }

        return valid;
    }
}
