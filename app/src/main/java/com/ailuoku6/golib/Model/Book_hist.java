package com.ailuoku6.golib.Model;

public class Book_hist {
    private String name;
    private String zuozhe;
    private String guancangdi;
    private String borrowDate;
    private String returnDate;

    public void setGuancangdi(String guancangdi) {
        this.guancangdi = guancangdi;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setZuozhe(String zuozhe) {
        this.zuozhe = zuozhe;
    }

    public String getGuancangdi() {
        return guancangdi;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getName() {
        return name;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getZuozhe() {
        return zuozhe;
    }
}
