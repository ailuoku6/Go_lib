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
        connection.timeout(8*1000)
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
            Element book_name = e.selectFirst("h3 > a");

            Book book = new Book(book_name.text()+book_name.nextSibling().toString(),book_name.attr("href"),
                    text1.childNode(0).toString(),text1.childNode(2).toString()+" ",
                    text2.childNode(2).toString(),text2.childNode(4).toString().replace("&nbsp;"," "));
            searchPages.addBook(book);
        }

        try{
            searchPages.setResult_count(document.selectFirst(".search_form.bulk-actions").select("strong.red").text());
        }catch (Exception e){
            e.printStackTrace();
            searchPages.setResult_count("0");
        }

//        if(document.selectFirst(".pagination > b").text()==null){
//            searchPages.setPre("");
//            searchPages.setNext("");
//            searchPages.setNum_pages("1/1");
//            return searchPages;
//        }

        try{
            searchPages.setNum_pages(document.selectFirst(".pagination > b").text());//结果较少时会崩溃
        }catch (Exception e){
            e.printStackTrace();
            searchPages.setPre("");
            searchPages.setNext("");
            searchPages.setNum_pages("1/1");
            return searchPages;
        }

        //找b标签的上一个和下一个兄弟结点

        Elements PageUrls = document.select(".pagination > a");

        if(PageUrls.size()==1){
            if(PageUrls.get(0).text().equals("上一页")){//如果是最后一页
                searchPages.setPre(PageUrls.get(0).attr("href"));
                searchPages.setNext("");
            }else {//如果是第一页
                searchPages.setPre("");
                searchPages.setNext(PageUrls.get(0).attr("href"));
            }
        }else {
            searchPages.setPre(PageUrls.get(0).attr("href"));
            searchPages.setNext(PageUrls.get(1).attr("href"));
        }

//        searchPages.setPre(document.selectFirst(".pagination").childNode(0).attr("href").toString());
//        searchPages.setNext(document.selectFirst(".pagination").childNode(3).attr("href").toString());

        return searchPages;
    }

    public Search_pages GetpageByUrl(String url) throws IOException {
        Search_pages searchPages = new Search_pages();

        Document document;
        Connection connection = Jsoup.connect(url);
        connection.timeout(8*1000)
                .method(Connection.Method.GET);

        Connection.Response response = connection.execute();
        document = response.parse();
        Elements elements = document.select(".book_list_info");
        for (Element e : elements) {

            Element text1 = e.select("p > span").get(0);
            Element text2 = e.select("p").get(0);
            Element book_name = e.selectFirst("h3 > a");

            Book book = new Book(book_name.text()+book_name.nextSibling().toString(),book_name.attr("href"),
                    text1.childNode(0).toString(),text1.childNode(2).toString()+" ",
                    text2.childNode(2).toString(),text2.childNode(4).toString().replace("&nbsp;"," "));
            searchPages.addBook(book);
        }

        searchPages.setResult_count(document.selectFirst(".search_form.bulk-actions").select("strong.red").text());
        searchPages.setNum_pages(document.selectFirst(".pagination > b").text());
        //找b标签的上一个和下一个兄弟结点

        Elements PageUrls = document.select(".pagination > a");

        if(PageUrls.size()==1){
            if(PageUrls.get(0).text().equals("上一页")){//如果是最后一页
                searchPages.setPre(PageUrls.get(0).attr("href"));
                searchPages.setNext("");
            }else {//如果是第一页
                searchPages.setPre("");
                searchPages.setNext(PageUrls.get(0).attr("href"));
            }
        }else {
            searchPages.setPre(PageUrls.get(0).attr("href"));
            searchPages.setNext(PageUrls.get(1).attr("href"));
        }

        //searchPages.setPre(document.selectFirst(".pagination").childNode(0).attr("href").toString());
        Log.d(TAG, "GetpageByUrl: "+searchPages.getPre());
        //searchPages.setNext(document.selectFirst(".pagination").childNode(3).attr("href").toString());
        Log.d(TAG, "GetpageByUrl: "+searchPages.getNext());

        return searchPages;

    }

}
