package com.ailuoku6.golib.Model;

public class Book {
    private String name;
    private String DetailLink;
    private String guancang;
    private String kejie;
    private String zuozhe;
    private String chubanshe;
    public Book(String name,String DetailLink,String guancang,String kejie,String zuozhe,String chubanshe){
        this.name = name;
        this.DetailLink = DetailLink;
        this.guancang = guancang;
        this.kejie = kejie;
        this.zuozhe = zuozhe;
        this.chubanshe = chubanshe;
    }

    public String getName() {
        return name;
    }

    public String getDetailLink() {
        return DetailLink;
    }

    public String getGuancang() {
        return guancang;
    }

    public String getKejie() {
        return kejie;
    }

    public String getZuozhe() {
        return zuozhe;
    }

    public String getChubanshe() {
        return chubanshe;
    }
}
