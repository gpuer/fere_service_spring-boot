package com.fere.fere.error;

public class UserError {
    private int error;
    private String massage;
    public UserError(){
        error=0;
        massage="success";
    }
    public UserError(String msg){
        error=0;
        massage=msg;
    }
    public UserError(int type){
        switch (type){
            case 11:
                error=11;
                massage="绑定成功";
                break;
            case 12:
                error=12;
                massage="绑定失败";
                break;
            case 13:
                error=13;
                massage="欲绑定的用户不存在";
                break;
            case 14:
                error=14;
                massage="昵称修改成功";
                break;
            case 15:
                error=15;
                massage="昵称非法";
                break;
            case 16:
                error=16;
                massage="绑定紧急联系人失败";
                break;
            default:
                error=999;
                massage="未知错误";
                break;
        }
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
