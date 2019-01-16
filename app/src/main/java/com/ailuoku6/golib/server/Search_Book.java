package com.ailuoku6.golib.server;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.Book;
import com.ailuoku6.golib.Model.MuseumCollection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Search_Book {
    private String Api = ApiUrl.SEARCH_BOOK;

    private List<Book> books;

    public Search_Book(){
        books = new ArrayList<>();
    }

    public List<Book> GetBooks(String keyWord) throws IOException {
        Document document;
        Connection connection = Jsoup.connect(Api);
        connection.timeout(5*1000)
                .method(Connection.Method.GET)
                .data("strSearchType","title")
                .data("strText",keyWord);
        Connection.Response response = connection.execute();
        document = response.parse();
        Elements elements = document.select(".book_list_info");
        for (Element e : elements) {

            Element text1 = e.select("p > span").get(0);
            Element text2 = e.select("p").get(0);

            Book book = new Book(e.select("h3 > *:not(span)").text(),e.select("h3 > a").attr("href"),
                    text1.childNode(0).toString(),text1.childNode(2).toString()+" ",
                    text2.childNode(2).toString(),text2.childNode(4).toString().replace("&nbsp;"," "));
            books.add(book);
        }
        return books;
    }

}
