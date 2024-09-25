package com.example.demo.service;

import com.example.demo.Bean.Admin;
import com.example.demo.repo.AdminRepository;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Result<Map<String, Object>> login(String username, String password) {
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (admin.getPassword().equals(password)) {
                // Create a token or just return success
                Map<String, Object> data = new HashMap<>();
                data.put("adminId", admin.getId());
                data.put("username", admin.getUsername());
                return Result.success(data);
            } else {
                return Result.error(CodeMsg.ADMIN_PASSWORD_ERROR);
            }
        }
        return Result.error(CodeMsg.ADMIN_NOT_EXIST);
    }


}
