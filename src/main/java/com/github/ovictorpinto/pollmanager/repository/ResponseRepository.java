package com.github.ovictorpinto.pollmanager.repository;

import com.github.ovictorpinto.pollmanager.model.Response;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ResponseRepository {

    private final EntityManager em;

    public ResponseRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Response response) {
        em.merge(response);
    }
}
