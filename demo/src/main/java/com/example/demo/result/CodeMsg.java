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
