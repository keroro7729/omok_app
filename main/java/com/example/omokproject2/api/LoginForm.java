package com.example.omokproject2.api;

public class LoginForm {
    private String userId, password;

    public LoginForm(String userId, String password){
        setUserId(userId);
        setPassword(password);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){ return password; }
    public String getUserId(){ return userId; }

    public boolean check(){
        if(userId.length() < 5 || 30 < userId.length()) return false;
        if(password.length() < 8 || 30 < password.length()) return false;
        if(!checkId()) return false;
        if(!checkPassword()) return false;
        return true;
    }
    public boolean checkId(){
        for(int i=0; i<userId.length(); i++){
            if(userId.charAt(i) != '_' && !isEngOrNum(userId.charAt(i)))
                return false;
        }
        return true;
    }
    public boolean checkPassword(){
        for(int i=0; i<password.length(); i++){
            if(isValidMark(password.charAt(i)) && isEngOrNum(password.charAt(i)))
                return false;
        }
        return true;
    }
    private boolean isEngOrNum(char c){
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || ('0' <= c && c <= '9');
    }
    private String validMark = "!@#$%^&*-_+=~`";
    private boolean isValidMark(char c){
        return validMark.indexOf(c) != -1;
    }
}
