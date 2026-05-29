package com.example.todolist.domain.model;

public class Note {
    private int id;
    private String text;
    private double priceRub;
    private boolean bought;
    private int priority;

    public Note(int id, String text, double priceRub, boolean bought, int priority) {
        this.id = id;
        this.text = text;
        this.priceRub = priceRub;
        this.bought = bought;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getPriceRub() {
        return priceRub;
    }

    public void setPriceRub(double priceRub) {
        this.priceRub = priceRub;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
