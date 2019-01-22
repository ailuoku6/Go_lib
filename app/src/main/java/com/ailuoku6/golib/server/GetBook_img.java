package com.ailuoku6.golib.server;

import android.util.Log;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.IOException;
import static android.support.constraint.Constraints.TAG;

public class GetBook_img {
    public byte[] updataCha(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        connection.timeout(6*1000)
                .method(Connection.Method.GET);
        Connection.Response res = connection.ignoreContentType(true).execute();
        byte[] img = res.bodyAsBytes();

        Log.d(TAG, "GetBook_img: run here");

        return img;
    }
}
