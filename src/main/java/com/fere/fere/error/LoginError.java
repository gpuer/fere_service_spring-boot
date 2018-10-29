package com.fere.fere.error;

public class LoginError {
    private int error;
    private String massage;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LoginError(){
        error=0;
        massage="success";
    }
    public LoginError(String uid){
        error=0;
        massage="success";
        this.uid=uid;
    }
    public LoginError(int type){
        switch (type){
            case 1:
                error=1;
                massage="密码错误";
                break;
            case 2:
                error=2;
                massage="用户名已被占用";
                break;
            case 3:
                error=3;
                massage="用户不存在";
                break;
            case 4:
                error=4;
                massage="用户名或密码为空";
                break;
            case 5:
                error=5;
                massage="Cookie信息错误";
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
