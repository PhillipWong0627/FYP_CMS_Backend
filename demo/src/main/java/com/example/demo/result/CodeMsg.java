package com.example.demo.result;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Getter
@ToString
public class CodeMsg {
    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"server error");

    //User
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500213,"password is not correct, please try again!");
    public static CodeMsg EMAIL_NOT_EXIST = new CodeMsg(500214,"Email is not exist");

    public static CodeMsg EMAIL_EXIST = new CodeMsg(500215,"Email has already existed, please try another username");

    //User Join Event
    public static CodeMsg EVENT_JOIN = new CodeMsg(500216,"User has already join this event");
    //User Redeem Reward
    public static CodeMsg REWARD_NOT_EXIST = new CodeMsg(500217,"Reward Not Exist");
    public static CodeMsg MEMBER_NOT_EXIST = new CodeMsg(500218,"Member Not Exist");
    public static CodeMsg NOT_ENOUGH_POINTS = new CodeMsg(500218,"Points Not Enough");
    public static CodeMsg OUT_OF_STOCK = new CodeMsg(500218,"Product out of stock");
    public static CodeMsg ALREADY_REDEEMED = new CodeMsg(500219, "You have already redeemed this reward.");


    //Facility
    public static CodeMsg FACILITY_NOT_EXIST = new CodeMsg(500215,"Facility Not Existed");

    private CodeMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }


    public CodeMsg fillArgs(Object...args){
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code,message);
    }

}
