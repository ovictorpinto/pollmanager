package com.github.ovictorpinto.pollmanager.business;

import com.github.ovictorpinto.pollmanager.exception.BusinessException;
import com.github.ovictorpinto.pollmanager.exception.ShortPasswordException;
import com.github.ovictorpinto.pollmanager.exception.UsernameDuplicated;
import com.github.ovictorpinto.pollmanager.model.User;
import com.github.ovictorpinto.pollmanager.repository.UserRepository;
import org.springframework.stereotype.Component;

/**
 * Class where will be business rules about users. Some rules i assumed like password size
 */
@Component
public class UserBusiness {


    private UserRepository userRepository;

    public UserBusiness(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateNewUser(User user) throws BusinessException {

        //non security password
        if(user.getPassword().length() < 3)
            throw new ShortPasswordException();

        //Suggestion: invalid email

        //Suggestion: domains in black list

        //pre existent email
        if(userRepository.findBy(user.getUsername())!= null)
            throw new UsernameDuplicated();

    }

    public void validateLogin(User user) throws BusinessException {

        //Suggestion: payment not in day

        //Suggestion: invalidate by some reason
    }
}
