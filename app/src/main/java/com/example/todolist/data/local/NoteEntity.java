package com.example.todolist.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "priceRub")
    private double priceRub;

    @ColumnInfo(name = "bought")
    private boolean bought;

    @ColumnInfo(name = "priority")
    private int priority;

    public NoteEntity(int id, String text, double priceRub, boolean bought, int priority) {
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
