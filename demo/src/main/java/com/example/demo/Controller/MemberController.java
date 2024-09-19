package com.example.demo.Controller;

import com.example.demo.Bean.Member;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="api/v1/user")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //Get All Member List
    @GetMapping("/getMember")
    public Result<List<Member>> getMember() {
        try {
            List<Member> members = memberService.getMember();
            return Result.success(members);
        } catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR);
        }

//        List<Member> members = memberService.getMember();
//
////        if (members != null && !members.isEmpty()) {
//            return Result.success(members);
////            return new ResponseEntity<>(members, HttpStatus.OK);
////        }
////        else {
////            return Result.success(members);
////            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////        }
    }

//    @PostMapping("/members")  // Change the path to avoid conflicts with the GET method
//    public void registerNewMember(@RequestBody Member member){
//        memberService.addNewMember(member);
//    }
    @PostMapping("/addMembers")
    public ResponseEntity<Result<Boolean>> registerNewMember(@RequestBody Member member){
        Result<Boolean> result = memberService.addNewMember(member);
        if (result.getCode() == CodeMsg.SUCCESS.getCode()) {
            return ResponseEntity.ok(result); // Return 200 OK for success
        } else {
            return ResponseEntity.status(400).body(result); // Return 400 Bad Request for errors
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Result<Map<String, Object>>> login(@RequestBody Member loginRequest) {
        // Call the login method from the service layer
        Result<Map<String, Object>> result = memberService.login(loginRequest.getEmail(), loginRequest.getPassword());

        // If the login is successful, return the result with HTTP 200 OK status
        if (result.getCode() == 0) {
            return ResponseEntity.ok(result);
        } else {
            // If login fails, return the error result with HTTP 400 Bad Request status
            return ResponseEntity.status(400).body(result);
        }
    }

    // Endpoint to get member points
    @GetMapping("/{memberId}/points")
    public ResponseEntity<Result<Integer>> getMemberPoints(@PathVariable Long memberId) {
        Result<Integer> result = memberService.getMemberPoints(memberId);
        if (result.getCode() == 0) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    // Endpoint to add points to a member after payment
    @PostMapping("/{memberId}/add-points")
    public ResponseEntity<Result<Boolean>> addPointsToMember(@PathVariable Long memberId, @RequestBody Map<String, Integer> request) {
        int points = request.get("points");
        Result<Boolean> result = memberService.addPoints(memberId, points);
        if (result.getCode() == 0) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }






}
