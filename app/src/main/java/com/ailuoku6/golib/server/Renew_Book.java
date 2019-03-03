package com.ailuoku6.golib.server;

import android.provider.ContactsContract;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.Book_lst;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class Renew_Book {
    private String Api = ApiUrl.RENEW;

    public String Renew(Map<String,String> cookies_, Book_lst book_lst, String vercode) throws IOException {

        Connection connection = Jsoup.connect(Api);

        //http://www.lib.wust.edu.cn:8780/reader/ajax_renew.php?bar_code=A1091327&check=567D8F2A&captcha=owqz&time=1551591299277

        connection.timeout(4*1000)
                .method(Connection.Method.GET)
                .data("bar_code",book_lst.getBar_code())
                .data("check",book_lst.getCheck())
                .data("captcha",vercode)
                .data("time",Long.toString(System.currentTimeMillis()));
        Connection.Response res = connection.cookies(cookies_).execute();

        //Document document = res.parse();

        //return document.select("font").text();
        return res.body();
    }
}
