package com.example.omokproject2.api;

public class TokenManager {

    private static TokenManager tokenManager;
    private String token;

    private TokenManager(){}

    public static TokenManager getInstance(){
        if(tokenManager == null)
            return new TokenManager();
        else return tokenManager;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){ return token; }
}
