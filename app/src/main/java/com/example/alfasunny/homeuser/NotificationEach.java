package com.example.alfasunny.homeuser;

public class NotificationEach {
    String from;
    Integer amount;
    Double cost;
    String to;
    String fromRestaurantName;
    String toPersonName;
    String key;

    public NotificationEach() {}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public NotificationEach(String from, Integer amount, Double cost, String to, String fromRestaurantName, String toPersonName, String key) {
        this.from = from;
        this.amount = amount;
        this.cost = cost;
        this.to = to;
        this.fromRestaurantName = fromRestaurantName;
        this.toPersonName = toPersonName;
        this.key = key;
    }

    public NotificationEach(String from, Integer amount, Double cost, String to, String fromRestaurantName, String toPersonName) {
        this.from = from;
        this.amount = amount;
        this.cost = cost;
        this.to = to;
        this.fromRestaurantName = fromRestaurantName;
        this.toPersonName = toPersonName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromRestaurantName() {
        return fromRestaurantName;
    }

    public void setFromRestaurantName(String fromRestaurantName) {
        this.fromRestaurantName = fromRestaurantName;
    }

    public String getToPersonName() {
        return toPersonName;
    }

    public void setToPersonName(String toPersonName) {
        this.toPersonName = toPersonName;
    }
}
