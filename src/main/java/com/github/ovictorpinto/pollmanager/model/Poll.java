package com.github.ovictorpinto.pollmanager.model;

import com.github.ovictorpinto.pollmanager.helper.HashIdHelper;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String description;

    private boolean needAuth;

    private boolean hasResponse = false;

    @ManyToOne
    private User user;

    public String getIdHashed(){
        try {
            return new HashIdHelper().encrypt(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollItem> itemList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNeedAuth() {
        return needAuth;
    }

    public void setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
    }

    public List<PollItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<PollItem> itemList) {
        this.itemList = itemList;
    }

    public boolean isHasResponse() {
        return hasResponse;
    }

    public void setHasResponse(boolean hasResponse) {
        this.hasResponse = hasResponse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
