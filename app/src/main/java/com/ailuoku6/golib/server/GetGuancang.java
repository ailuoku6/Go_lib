package com.ailuoku6.golib.server;

import com.ailuoku6.golib.Model.Guancang_page;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetGuancang {
    public Guancang_page getGuancang(String url) throws IOException {

        Guancang_page guancang_page = new Guancang_page();


        Connection connection = Jsoup.connect(url);

        connection.timeout(6*1000);

        Document document = connection.get();

        guancang_page.setImgUrl(document.selectFirst("#book_pic > img").attr("src"));

        Element table = document.selectFirst("#item");

        Elements elements = table.select("tbody > tr");

        for (int i = 1;i<elements.size();i++){
            
        }

        return guancang_page;
    }
}
