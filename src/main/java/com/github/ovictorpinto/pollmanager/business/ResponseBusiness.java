package com.github.ovictorpinto.pollmanager.business;

import com.github.ovictorpinto.pollmanager.exception.BusinessException;
import org.springframework.stereotype.Component;

/**
 * Symbolic class where will be business rules about responses
 */
@Component
public class ResponseBusiness {

    public void validateVote(long pollId, long idItemPool) throws BusinessException {
        //Suggestion: validate item
        //Suggestion: validate user
        //Suggestion: validate other requisite from vote
    }
}
