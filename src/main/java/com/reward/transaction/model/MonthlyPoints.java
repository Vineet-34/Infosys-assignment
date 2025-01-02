package com.reward.transaction.model;

public class MonthlyPoints {
    private int year;
    private int month;
    private int points;

    public MonthlyPoints(int year, int month, int points) {
        this.year = year;
        this.month = month;
        this.points = points;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
