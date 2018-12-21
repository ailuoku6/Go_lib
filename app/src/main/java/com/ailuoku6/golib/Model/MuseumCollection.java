package com.ailuoku6.golib.Model;

public class MuseumCollection {
    private String suoshuNum;
    private String tiaomaNum;
    private String nianjuan;
    private String guancangdi;
    private String bookstatus;

    public MuseumCollection(String suoshuNum,String tiaomaNum,String nianjuan,String guancangdi,String bookstatus){
        this.suoshuNum = suoshuNum;
        this.tiaomaNum = tiaomaNum;
        this.nianjuan = nianjuan;
        this.guancangdi = guancangdi;
        this.bookstatus = bookstatus;
    }

    public String getSuoshuNum() {
        return suoshuNum;
    }

    public String getTiaomaNum() {
        return tiaomaNum;
    }

    public String getNianjuan() {
        return nianjuan;
    }

    public String getGuancangdi() {
        return guancangdi;
    }

    public String getBookstatus() {
        return bookstatus;
    }
}
