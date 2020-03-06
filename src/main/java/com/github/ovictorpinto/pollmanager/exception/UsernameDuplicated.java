package com.github.ovictorpinto.pollmanager.exception;

public class UsernameDuplicated extends BusinessException {

    public UsernameDuplicated() {
        super("Username already used");
    }
}
