package com.ailuoku6.golib.server;

import android.util.Log;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.Guancang;
import com.ailuoku6.golib.Model.Guancang_page;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class GetGuancang {
    public Guancang_page getGuancang(String url) throws IOException {

        Guancang_page guancang_page = new Guancang_page();


        Connection connection = Jsoup.connect(url);

        connection.timeout(6*1000);

        Document document = connection.get();
        Element table = document.selectFirst("#item");
        Elements trs = table.select("tbody > tr");

        try{
            guancang_page.setImgUrl(ApiUrl.BOOK_COVER+document.select(".booklist").get(2).select("dd").text());
        }catch (Exception e){
            e.printStackTrace();
        }

        //guancang_page.setImgUrl(document.selectFirst("#book_pic > img").attr("src"));
        //Log.d(TAG, "getGuancang: "+document.select("#book_pic").html());

        try {
            guancang_page.setBook_name(document.selectFirst(".booklist > dd").text());
        }catch (Exception e){
            e.printStackTrace();
            guancang_page.setBook_name("暂无书名");
        }

        if(trs.get(0).select("td").size()<=1){//此书刊可能正在订购中或者处理中
            Guancang guancang = new Guancang();
            guancang.setSuoshuhao(trs.get(0).text());
            guancang.setGuancangdi(trs.get(1).text());
            guancang.setState("不可借");

            guancang_page.AddGuancang(guancang);
        }else {
            for (int i = 1;i<trs.size();i++){
                Guancang guancang = new Guancang();
                Elements tds = trs.get(i).select("td");
                guancang.setSuoshuhao(tds.get(0).text());
                guancang.setGuancangdi(tds.get(3).attr("title"));
                guancang.setState(tds.get(4).text());

                guancang_page.AddGuancang(guancang);
            }
        }

        return guancang_page;
    }
}
