package com.example.demo.Controller;

import com.example.demo.Bean.Event;
import com.example.demo.Bean.Member;
import com.example.demo.Bean.Reward;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        @GetMapping("/check-email")
    public ResponseEntity<Result<Boolean>> checkEmailExists(@RequestParam String email) {
        boolean emailExists = memberService.emailExists(email);
        if (emailExists) {
            return ResponseEntity.ok(Result.success(true)); // Email exists
        } else {
            return ResponseEntity.ok(Result.success(false)); // Email does not exist
        }
    }

    @PostMapping("/addMembers")
    public ResponseEntity<Result<Boolean>> registerNewMember(@RequestBody Member member){
        Result<Boolean> result = memberService.addNewMember(member);
        if (result.getCode() == CodeMsg.SUCCESS.getCode()) {
            return ResponseEntity.ok(result); // Return 200 OK for success
        } else {
            return ResponseEntity.ok(result); // Return 400 Bad Request for errors
        }
    }

    @GetMapping("/getById/{id}")
    public Result<Optional<Member>> getMemberById(@PathVariable() Long id){
        Optional<Member> getMemberById = memberService.getMemberById(id);

        if(getMemberById.isPresent()){
            return Result.success(getMemberById);
        }
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PatchMapping("updateById/{Id}")
    public Result<Member> updateMemberById(@PathVariable() Long Id, @RequestBody Member member){
        try{
            Member updatedMember = memberService.updateMember(Id, member);
            return Result.success(updatedMember);
        } catch (RuntimeException e) {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteById/{id}")
    public Result<Boolean> deleteMemberById(@PathVariable() Long id){
        try {
            Result<Boolean> isDeleted = memberService.deleteMember(id); // Call the service to delete
            if (isDeleted.getData()) {
                return Result.success(true); // Return success if the deletion was successful
            } else {
                return Result.error(CodeMsg.SERVER_ERROR); // Return error if the member doesn't exist
            }
        } catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR.fillArgs("Failed to delete member"));
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
            return ResponseEntity.ok(result);
        }
    }
    // Get events joined by a specific member
    @GetMapping("/{memberId}/joined-events")
    public ResponseEntity<Result<List<Event>>> getJoinedEvents(@PathVariable Long memberId) {
        try {
            List<Event> events = memberService.getJoinedEvents(memberId);
            return ResponseEntity.ok(Result.success(events));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Result.error(CodeMsg.SERVER_ERROR.fillArgs("Error retrieving joined events")));
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
    // New endpoint to update the avatar
    @PutMapping("/{memberId}/update-avatar")
    public Result<Member> updateAvatar(@PathVariable Long memberId, @RequestBody Map<String, String> request) {
        String avatarUrl = request.get("avatarUrl");
        Result<Member> result = memberService.updateAvatar(memberId, avatarUrl);
        if (result.getCode() == CodeMsg.SUCCESS.getCode()) {
            return Result.success(result.getData());
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    // Endpoint to get the redeemed rewards for a specific member
    @GetMapping("/{memberId}/redeemed-rewards")
    public ResponseEntity<Result<List<Reward>>> getRedeemedRewards(@PathVariable Long memberId) {
        Result<List<Reward>> result = memberService.getRedeemedRewards(memberId);
        if (result.getCode() == 0) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }
    @PostMapping("/{memberId}/redeem")
    public ResponseEntity<Result<Boolean>> redeemReward(
            @PathVariable Long memberId,
            @RequestBody Map<String, Long> request // Expecting { "rewardId": 1 }
    ) {
        Long rewardId = request.get("rewardId");
        Result<Boolean> result = memberService.redeemReward(memberId, rewardId);

        if (result.getCode() == 0) {
            return ResponseEntity.ok(result); // 200 OK
        } else if (result.getCode() == CodeMsg.ALREADY_REDEEMED.getCode()) {
            // Instead of 409 Conflict, return 200 OK with different code/message
            return ResponseEntity.ok(Result.error(CodeMsg.ALREADY_REDEEMED)); // 200 OK but with "already redeemed" message
        } else if (result.getCode() == CodeMsg.NOT_ENOUGH_POINTS.getCode()) {
            return ResponseEntity.ok(Result.error(CodeMsg.NOT_ENOUGH_POINTS)); // 200 OK but with "insufficient points" message
        } else {
            return ResponseEntity.ok(Result.error(CodeMsg.SERVER_ERROR)); // General error, return 200 OK
        }

    }









}
