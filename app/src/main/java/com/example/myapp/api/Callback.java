package com.example.myapp.api;

public interface Callback {
    void onSuccess(String res);
    void onFailure(Exception e);
}
