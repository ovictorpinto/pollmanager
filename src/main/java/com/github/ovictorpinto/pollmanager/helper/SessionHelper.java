package com.github.ovictorpinto.pollmanager.helper;

import com.github.ovictorpinto.pollmanager.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * This bean will be stored on user session to identify who are the user logged
 */
@Component
@SessionScope
public class SessionHelper {

    private User userLogged;

    public void setUserLogged(User userLogged) {
        //Suggestion: register some log
        //Suggestion: notify some service
        this.userLogged = userLogged;
    }

    public void logout() {
        //Suggestion: register some log
        userLogged = null;
    }

    public boolean isLogged() {
        return userLogged != null;
    }

    public User getUserLogged() {
        return userLogged;
    }
}
