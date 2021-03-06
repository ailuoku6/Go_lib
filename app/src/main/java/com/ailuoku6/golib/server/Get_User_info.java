package com.ailuoku6.golib.server;

import android.util.Log;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.userInfo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class Get_User_info {
    private userInfo userinfo;
    private String Api  = ApiUrl.USERINFO;

    public Get_User_info(){
        userinfo = new userInfo();
    }
    public userInfo getUserinfo(Map<String,String> cookies_) throws IOException {
        Connection connection = Jsoup.connect(Api);

        connection.timeout(6*1000);

        connection.method(Connection.Method.POST);

        Connection.Response res = connection.cookies(cookies_).execute();

        Document document = res.parse();

        Element element = document.selectFirst("#mylib_info > table > tbody");
        Element element1 = document.selectFirst(".mylib_msg");

        Log.d(TAG, "getUserinfo: "+cookies_);

        try {
            Elements elements = element.select("tr");

            userinfo.setPrifileStart(elements.get(1).select("td").get(2).text());
            userinfo.setPrifileEnd(elements.get(1).select("td").get(0).text());
            userinfo.setFiveDay_out(element1.select("a").get(0).text());
            userinfo.setOut_dateBook(element1.select("a").get(1).text());
            userinfo.setMaxBookNum(elements.get(2).select("td").get(0).text());
            userinfo.setUserType(elements.get(3).select("td").get(0).text());
            userinfo.setBorrowTime(elements.get(3).select("td").get(2).text());
            userinfo.setBreakrulesTime(elements.get(4).select("td").get(0).text());
            userinfo.setDebt(elements.get(4).select("td").get(1).text());
        }catch (Exception e){
            e.printStackTrace();
        }


        return userinfo;

        //userinfo.setUserName(elements.get(0).select("td").get(1).text());
        //PHPSESSID=pnd47oq9c2dc4mfpr1ajhv1f11
        //PHPSESSID=8oqd3dslp3fg1nicosd49n5vu3
    }
}
