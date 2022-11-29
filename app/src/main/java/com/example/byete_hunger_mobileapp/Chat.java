package com.example.byete_hunger_mobileapp;

public class Chat {

    public String message;
    public Integer from;
    public Long timestmap;

    public Chat() {

    }

    public Chat(String sender, Integer from) {
        this.message = sender;
        this.from = from;
        this.timestmap = System.currentTimeMillis();
    }

    public String getMessage() {
        return this.message;
    }
    public Integer getFrom() {
        return this.from;
    }
    public Long getTimestmap() {
        return this.timestmap;
    }


}
