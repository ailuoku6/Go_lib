package com.ailuoku6.golib.server;

import android.util.Log;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.Book_lst;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Get_Book_lst {
    private String Api = ApiUrl.BOOK_LST;

    public List<Book_lst> getBook_lst(Map<String,String> cookies) throws IOException {
        List<Book_lst> book_lsts = new ArrayList<>();

        Connection connection = Jsoup.connect(Api);

        connection.timeout(6*1000)
                .method(Connection.Method.GET);

        Connection.Response res = connection.cookies(cookies).execute();

        Document document = res.parse();

        try {
            Elements trs = document.select("tbody > tr");

            for (int i = 1;i<trs.size();i++){
                Elements tds = trs.get(i).select("td");
                Book_lst book_lst = new Book_lst();

                book_lst.setBar_code(tds.get(0).text());
                book_lst.setName_lst(tds.get(1).text());
                book_lst.setBorrowDate("借阅日期:"+tds.get(2).text());
                book_lst.setReturnDate("应还日期:"+tds.get(3).text());
                book_lst.setXujieliang(tds.get(4).text());
                book_lst.setGuancangdi(tds.get(5).text());

                book_lsts.add(book_lst);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return book_lsts;
    }
}
