package com.github.ovictorpinto.pollmanager.exception;

public class UserNotFoundExeception extends BusinessException {
    public UserNotFoundExeception() {
        super("Email/password not found");
    }
}
