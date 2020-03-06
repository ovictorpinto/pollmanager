package com.github.ovictorpinto.pollmanager.model;

public class ChartItem {

    private String description;
    private long total;

    public ChartItem() {
    }

    public ChartItem(String description, long total) {
        this.description = description;
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
