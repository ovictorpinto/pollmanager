package com.github.ovictorpinto.pollmanager.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private PollItem pollItem;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp  = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PollItem getPollItem() {
        return pollItem;
    }

    public void setPollItem(PollItem pollItem) {
        this.pollItem = pollItem;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
