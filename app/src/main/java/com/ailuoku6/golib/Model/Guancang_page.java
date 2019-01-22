package com.ailuoku6.golib.Model;

import java.util.ArrayList;
import java.util.List;

public class Guancang_page {
    private String imgUrl;
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

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void AddGuancang(Guancang guancang){
        guancangList.add(guancang);
    }
}
