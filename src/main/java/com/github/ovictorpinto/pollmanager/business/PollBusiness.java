package com.github.ovictorpinto.pollmanager.business;

import com.github.ovictorpinto.pollmanager.model.Poll;
import org.springframework.stereotype.Component;

/**
 * Symbolic class where will be business rules about polls
 */
@Component
public class PollBusiness {

    public void validatePoll(Poll poll){
        //Suggestion: validate minimum itens
        //Suggestion: validate itens with same description
        //Suggestion: validate maximum itens
    }
}
