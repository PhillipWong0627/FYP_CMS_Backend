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
