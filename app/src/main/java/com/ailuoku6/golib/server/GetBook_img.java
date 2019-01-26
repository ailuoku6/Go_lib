package com.ailuoku6.golib.server;

import android.util.Log;

import com.ailuoku6.golib.Api.ApiUrl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.IOException;
import static android.support.constraint.Constraints.TAG;

public class GetBook_img {


    public byte[] updataImg(String ISBN) throws IOException {
//        Log.d(TAG, "updataImg: "+url);
//        Connection connection = Jsoup.connect(url);
//        connection.timeout(6*1000)
//                .method(Connection.Method.GET);
//        Connection.Response res = connection.ignoreContentType(true).execute();
//        byte[] img = res.bodyAsBytes();
//
//        return img;

        Connection connection1 = Jsoup.connect(ApiUrl.DOUBANBOOK+ISBN);
        connection1.timeout(4*1000).method(Connection.Method.GET);

        Connection.Response response = connection1.ignoreContentType(true).execute();
        Log.d(TAG, "updataImg: "+response.body());

        String imgurl = getImageUrl(response.body());

        if(imgurl!=null){
            Connection connection = Jsoup.connect(imgurl);
            connection.timeout(6*1000)
                    .method(Connection.Method.GET);
            Connection.Response res = connection.ignoreContentType(true).execute();
            byte[] img = res.bodyAsBytes();

            return img;
        }else return null;

    }

    public String getImageUrl(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getJSONObject("images").getString("small");
        }catch (Exception e){
            return null;
        }
    }


}
