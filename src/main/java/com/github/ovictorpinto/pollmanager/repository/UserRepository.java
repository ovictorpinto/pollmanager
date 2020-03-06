package com.github.ovictorpinto.pollmanager.repository;

import com.github.ovictorpinto.pollmanager.exception.UserNotFoundExeception;
import com.github.ovictorpinto.pollmanager.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepository {

    private final EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public void save(User user) {
        String hashedPass = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(hashedPass);
        em.persist(user);
        System.out.println(user.getId());
    }

    public User findBy(String username, String password) throws UserNotFoundExeception {
        List<User> resultList = em.createQuery("select u from User u where u.username = :username and u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", DigestUtils.md5Hex(password))
                .getResultList();
        if(resultList.isEmpty()){
            throw new UserNotFoundExeception();
        }
        return resultList.get(0);
    }

    public User findBy(String username) {
        List<User> resultList = em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
        if(resultList.isEmpty()){
            return null;
        }
        return resultList.get(0);
    }
}
