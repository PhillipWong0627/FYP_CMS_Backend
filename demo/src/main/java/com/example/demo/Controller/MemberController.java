package com.example.demo.Controller;

import com.example.demo.Bean.Member;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/user")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

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

    @PostMapping("/members")  // Change the path to avoid conflicts with the GET method
    public void registerNewMember(@RequestBody Member member){
        memberService.addNewMember(member);
    }


}
