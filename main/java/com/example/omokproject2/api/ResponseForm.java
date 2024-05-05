package com.example.omokproject2.api;

public class ResponseForm {
    private String result, message, data;

    public ResponseForm(String result, String message, String data){
        setMessage(message);
        setResult(result);
        setData(data);
    }

    public void setResult(String result){
        this.result = result;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setData(String Data){
        this.data = data;
    }

    public String getMessage(){ return message; }
    public String getResult(){ return result; }
    public String getData(){ return data; }
}
