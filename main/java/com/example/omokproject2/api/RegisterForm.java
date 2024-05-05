package com.example.omokproject2.api;

public class RegisterForm {
    private String userId;
    private String password;
    private String nickname;

    public RegisterForm(String userId, String password, String nickname){
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }

    public String getUserId(){
        return userId;
    }
    public String getPassword(){
        return password;
    }
    public String getNickname(){
        return nickname;
    }

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
