package com.ailuoku6.golib.server;

import com.ailuoku6.golib.Model.Notice;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoticeServer {
    private String ApiUrl = "http://www.lib.wust.edu.cn/";
    private List<Notice> noticeList;

    public NoticeServer(){
        this.noticeList = new ArrayList<>();
    }

    public List<Notice> getNotice() throws IOException {
        Connection connection = Jsoup.connect(ApiUrl);
        connection.timeout(5*1000);
        Document document = connection.get();
        Elements elements = document.select("#gonggao").select("tr > td");
        for (Element e: elements) {
            Notice notice = new Notice(ApiUrl+e.select("a").attr("href"),e.select("a").text(),e.select("font").text());
            noticeList.add(notice);
        }
        return noticeList;
    }
}
