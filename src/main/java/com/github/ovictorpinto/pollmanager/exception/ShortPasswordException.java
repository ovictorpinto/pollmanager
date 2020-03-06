package com.github.ovictorpinto.pollmanager.exception;

public class ShortPasswordException extends BusinessException{

    public ShortPasswordException() {
        super("Password is too short");
    }
}
