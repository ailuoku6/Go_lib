package com.ailuoku6.golib.server;

import com.ailuoku6.golib.Api.ApiUrl;
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
    private String Api = ApiUrl.LIB_HOME;
    private List<Notice> noticeList;

    public NoticeServer(){
        this.noticeList = new ArrayList<>();
    }

    public List<Notice> getNotice() throws IOException {
        Connection connection = Jsoup.connect(Api);
        connection.timeout(5*1000);
        Document document = connection.get();
        Elements elements = document.select("#gonggao").select("tr > td");
        for (Element e: elements) {
            Notice notice = new Notice(Api+e.select("a").attr("href"),e.select("a").text(),e.select("font").text());
            noticeList.add(notice);
        }
        return noticeList;
    }
}
