package com.opay.invite.resp;

public class CodeMsg {

    public static final CodeMsg SUCCESS = new CodeMsg(200, "success");
    public static final CodeMsg ILLEGAL_PARAMETER = new CodeMsg(1004, "illegal parameter ");
    public static final CodeMsg OVER_MAX_LENGTH = new CodeMsg(1005, "over length parameter ");
    public static final CodeMsg SYSTEM_ERROR = new CodeMsg(500, "system error");
    public static final CodeMsg PAHT_NOT_EXIST = new CodeMsg(404, "path does not exist");
    public static final CodeMsg NEED_AUTHORIZATION = new CodeMsg(2005, "you need to be authorization");

    //
    public static final CodeMsg ILLEGAL_CODE = new CodeMsg(1006, "Invalid referral code, please confirm.");
    public static final CodeMsg ILLEGAL_CODE_RELATION = new CodeMsg(1007, "Invalid referral code, please confirm.");

    public static final CodeMsg ILLEGAL_CODE_DAY = new CodeMsg(1008, "Oops, something went wrong, you're not rewarded");


    public static final CodeMsg ILLEGAL_CODE_TIXIAN = new CodeMsg(1009, "Insufficient balance! Refer friends to earn more! ");
    public static final CodeMsg ILLEGAL_CODE_TIXIAN_LIMIT = new CodeMsg(1010, "The withdrawal limit available today has been exhausted. Please withdraw your money tomorrow.");
    public static final CodeMsg ILLEGAL_CODE_TIXIAN_SYSTERM = new CodeMsg(1011, "The withdrawal fail. Please withdraw again.");

    public static final CodeMsg ILLEGAL_CODE_FIRST = new CodeMsg(1012, "First recharge balance completed");

    public static final CodeMsg ILLEGAL_CODE_SIGN = new CodeMsg(1013, "Sign fail");

    public static final CodeMsg ILLEGAL_CODE_ACTIVE = new CodeMsg(1014, "Activity ended");


    public static final CodeMsg LUCKY_DRAW_NOT_START_CODE = new CodeMsg(2001, "The lucky draw has not yet started");
    private int code;
    private String message;

    private CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CodeMsg CustomCodeMsgIllegalParameter(String message) {
        return new CodeMsg(1004, ILLEGAL_PARAMETER.getMessage() + message);
    }

    public static CodeMsg CustomOverMaxLengthsParameter(String message) {
        return new CodeMsg(1005, OVER_MAX_LENGTH.getMessage() + message);
    }

    public static CodeMsg CustomCodeMsg(String message) {
        return new CodeMsg(1000, message);
    }

    public static CodeMsg CustomCodeMsg(Integer code, String message) {
        return new CodeMsg(code, message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
