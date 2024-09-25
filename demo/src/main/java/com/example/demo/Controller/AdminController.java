package com.example.demo.Controller;


import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path="api/v1/admin")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping("/login")
    public ResponseEntity<Result<Map<String, Object>>> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        Result<Map<String, Object>> result = adminService.login(username, password);

        if (result.getCode() == 0) {
            return ResponseEntity.ok(result); // 200 OK
        }else if(result.getCode() == 500220){
            return ResponseEntity.ok(Result.error(CodeMsg.ADMIN_PASSWORD_ERROR));
        }else if(result.getCode() == 500221){
            return ResponseEntity.ok(Result.error(CodeMsg.ADMIN_NOT_EXIST));
        }else {
            return ResponseEntity.status(400).body(result); // 400 Bad Request
        }
    }


}
