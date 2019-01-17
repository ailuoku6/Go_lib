package com.ailuoku6.golib.server;

import android.util.Log;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.CookiesManage;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;


public class getPatcha {
    private String url = ApiUrl.GETPATCHA;

    public getPatcha(){
    }

    public byte[] FirstUpdataCha() throws IOException {
        Connection connection = Jsoup.connect(url+"?"+Math.random());
        connection.timeout(6*1000)
                .method(Connection.Method.GET);
        Connection.Response res = connection.ignoreContentType(true).execute();
        byte[] img = res.bodyAsBytes();
        CookiesManage.cookies = res.cookies();
        return img;
    }

    public byte[] updataCha(Map<String,String> cookies) throws IOException {
        //Map<String,String> head = new HashMap<>();
        Connection connection = Jsoup.connect(url+"?"+Math.random());
        connection.timeout(6*1000)
                .method(Connection.Method.GET);
        Connection.Response res = connection.ignoreContentType(true).cookies(cookies).execute();
        byte[] img = res.bodyAsBytes();
        //System.out.println("from getcha:"+res.headers());
        //savaImage(img,"test.png");

        Log.d(TAG, "updataCha: run here");

        return img;
    }

}
