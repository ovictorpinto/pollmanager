package com.github.ovictorpinto.pollmanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PollItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int pos;

    private String description;

    public PollItem() {
    }

    public PollItem(int pos, String description) {
        this.pos = pos;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
