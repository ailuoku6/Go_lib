package com.ailuoku6.golib.Model;

public class Book_lst {
    private String name;
    private String borrowDate;
    private String returnDate;
    private String bar_code;
    private String guancangdi;
    private String xujieliang;

    public String getReturnDate() {
        return returnDate;
    }

    public String getName_lst() {
        return name;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getGuancangdi() {
        return guancangdi;
    }

    public String getBar_code() {
        return bar_code;
    }

    public String getXujieliang() {
        return xujieliang;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setName_lst(String name) {
        this.name = name;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setGuancangdi(String guancangdi) {
        this.guancangdi = guancangdi;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public void setXujieliang(String xujieliang) {
        this.xujieliang = xujieliang;
    }

}
