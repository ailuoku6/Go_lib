package com.ailuoku6.golib.Model;

public class Book {
    private String name;
    private String lang;
    private String DetailLink;
    private String suoshuhao;
    private String guancang;
    private String kejie;
    private String zuozhe;
    private String chubanshe;
    public Book(String name,String lang,String DetailLink,String suoshuhao,String guancang,String kejie,String zuozhe,String chubanshe){
        this.name = name;
        this.lang = lang;
        this.DetailLink = DetailLink;
        this.suoshuhao = suoshuhao;
        this.guancang = guancang;
        this.kejie = kejie;
        this.zuozhe = zuozhe;
        this.chubanshe = chubanshe;
    }

    public String getName() {
        return name;
    }

    public String getLang() {
        return lang;
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

    public String getSuoshuhao() {
        return suoshuhao;
    }

    public String getZuozhe() {
        return zuozhe;
    }

    public String getChubanshe() {
        return chubanshe;
    }
}
