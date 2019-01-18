package com.ailuoku6.golib.server;

import android.util.Log;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.Book;
import com.ailuoku6.golib.Model.MuseumCollection;
import com.ailuoku6.golib.Model.Search_pages;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class Search_Book {
    private String Api = ApiUrl.SEARCH_BOOK;

//    private Search_pages searchPages;
//
//    public Search_Book(){
//        searchPages = new Search_pages();
//    }

    public Search_pages GetSearch_page(String keyWord) throws IOException {

        Search_pages searchPages = new Search_pages();

        Log.d(TAG, "GetSearch_page: "+keyWord);
        Document document;
        Connection connection = Jsoup.connect(Api);
        connection.timeout(5*1000)
                .method(Connection.Method.GET)
                .data("strSearchType","title")
                .data("strText",keyWord)
                .data("loginsubmit.x","51")
                .data("loginsubmit.y","10");
        Connection.Response response = connection.execute();
        document = response.parse();
        Elements elements = document.select(".book_list_info");
        for (Element e : elements) {

            Element text1 = e.select("p > span").get(0);
            Element text2 = e.select("p").get(0);

            Book book = new Book(e.select("h3 > *:not(span)").text(),e.select("h3 > a").attr("href"),
                    text1.childNode(0).toString(),text1.childNode(2).toString()+" ",
                    text2.childNode(2).toString(),text2.childNode(4).toString().replace("&nbsp;"," "));
            searchPages.addBook(book);
        }

        searchPages.setResult_count(document.selectFirst(".search_form.bulk-actions").select("strong.red").text());
        searchPages.setNum_pages(document.selectFirst(".pagination > b").text());
        //找b标签的上一个和下一个兄弟结点

        searchPages.setPre(document.selectFirst(".pagination").childNode(0).attr("href").toString());
        searchPages.setNext(document.selectFirst(".pagination").childNode(3).attr("href").toString());

        return searchPages;
    }

    public Search_pages GetpageByUrl(String url) throws IOException {
        Search_pages searchPages = new Search_pages();

        Document document;
        Connection connection = Jsoup.connect(Api);
        connection.timeout(5*1000)
                .method(Connection.Method.GET);

        Connection.Response response = connection.execute();
        document = response.parse();
        Elements elements = document.select(".book_list_info");
        for (Element e : elements) {

            Element text1 = e.select("p > span").get(0);
            Element text2 = e.select("p").get(0);

            Book book = new Book(e.select("h3 > *:not(span)").text(),e.select("h3 > a").attr("href"),
                    text1.childNode(0).toString(),text1.childNode(2).toString()+" ",
                    text2.childNode(2).toString(),text2.childNode(4).toString().replace("&nbsp;"," "));
            searchPages.addBook(book);
        }

        searchPages.setResult_count(document.selectFirst(".search_form.bulk-actions").select("strong .red").text());
        searchPages.setNum_pages(document.selectFirst(".pagination > b").text());
        //找b标签的上一个和下一个兄弟结点

        searchPages.setPre(document.selectFirst(".pagination").childNode(0).attr("href").toString());
        searchPages.setNext(document.selectFirst(".pagination").childNode(3).attr("href").toString());

        return searchPages;
    }

}
