package com.ailuoku6.golib.Model;

import java.util.ArrayList;
import java.util.List;

public class Guancang_page {
    private String imgUrl;
    private String book_name;
    private String ISBN;
    private List<Guancang> guancangList;

    public Guancang_page(){
        this.guancangList = new ArrayList<>();
    }

    public List<Guancang> getGuancangList() {
        return guancangList;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void AddGuancang(Guancang guancang){
        guancangList.add(guancang);
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
