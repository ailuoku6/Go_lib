package com.ailuoku6.golib.server;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.Login_State;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class Login {

    private String Api = ApiUrl.LOGIN_URL2;

    private String token;

    private Map<String,String> cookies;

    public Login(){
        token = new String();
        cookies = new HashMap<>();
    }

    public boolean GETform() throws IOException {

        Connection connection = Jsoup.connect(Api);
        connection.timeout(6*1000);
        Connection.Response res = connection.execute();
        this.cookies = res.cookies();
        Document doc =  res.parse();
        Element tokenism = doc.getElementById("csrf_token");
        this.token = tokenism.attr("value");
        Log.d(TAG, "GETform: run here");
        return res.statusCode()==200?true:false;
    }

    public Login_State LogIn(String user_name, String password, String Vericode, Map<String,String> cookies_) throws IOException {
        Login_State loginState = new Login_State();
        Connection connection = Jsoup.connect(ApiUrl.LOGIN_URL2);
        connection.timeout(6*1000);

        connection.method(Connection.Method.POST)
                //       .headers(cookies)
                .data("number",user_name)
                .data("passwd",password)
                .data("captcha",Vericode)
                .data("select","bar_no")
                .data("returnUrl","")
                .data("csrf_token",token);
        Connection.Response res = connection.cookies(cookies_).execute();

        Document document = res.parse();

        Element element = document.getElementById("fontMsg");

        if(element==null){
            loginState.setLog(true);
            loginState.setERRORINFO("");
        }else {
            loginState.setLog(false);
            loginState.setERRORINFO(element.text());
        }

        return loginState;
    }

    public Map<String, String> getCookies() {
        return this.cookies;
    }

}
