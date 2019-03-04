package com.ailuoku6.golib.server;

import com.ailuoku6.golib.Api.ApiUrl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

public class Test_Cookies {//测试cookies是否有效
    public static boolean Test(Map<String,String> cookies_) throws IOException {
        Connection connection = Jsoup.connect(ApiUrl.USERINFO);
        connection.timeout(3*1000);
        connection.method(Connection.Method.POST);
        Connection.Response res = connection.cookies(cookies_).execute();
//        if (res.statusCode()==200) return true;
//        return false;

        Document document = res.parse();

        Element element = document.selectFirst("#mylib_info > table > tbody");

        return element!=null;

        //return res.statusCode()==200;
    }
}
