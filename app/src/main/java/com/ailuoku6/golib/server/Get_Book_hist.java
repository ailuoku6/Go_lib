package com.ailuoku6.golib.server;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.Book_hist;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Get_Book_hist {
    private String Api = ApiUrl.BOOK_HIST;

    public List<Book_hist> getBook_hist(Map<String,String> cookies) throws IOException {
        List<Book_hist> book_hists = new ArrayList<>();

        Connection connection = Jsoup.connect(Api);

        connection.timeout(6*1000)
                .method(Connection.Method.POST)
                .data("para_string","all");

        Connection.Response res = connection.cookies(cookies).execute();

        Document document = res.parse();

        try {
            Elements trs = document.select("tbody > tr");
            for (int i = 1;i<trs.size();i++){
                Elements tds = trs.get(i).select("td");
                Book_hist book_hist = new Book_hist();

                book_hist.setName(tds.get(2).text());
                book_hist.setZuozhe(tds.get(3).text());
                book_hist.setBorrowDate("借:"+tds.get(4).text());
                book_hist.setReturnDate("还:"+tds.get(5).text());
                book_hist.setGuancangdi(tds.get(6).text());

                book_hists.add(book_hist);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return book_hists;
    }
}
