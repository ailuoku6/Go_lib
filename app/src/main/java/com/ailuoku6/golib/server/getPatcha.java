package com.ailuoku6.golib.server;

import android.util.Log;

import com.ailuoku6.golib.Api.ApiUrl;

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

    public byte[] updataCha(Map<String,String> cookies) throws IOException {
        Map<String,String> head = new HashMap<>();
        Connection connection = Jsoup.connect(url+"?"+Math.random());
        connection.timeout(5*1000)
                .method(Connection.Method.GET);
        Connection.Response res = connection.ignoreContentType(true).cookies(cookies).execute();
        byte[] img = res.bodyAsBytes();
        //System.out.println("from getcha:"+res.headers());
        //savaImage(img,"test.png");

        Log.d(TAG, "updataCha: run here");

        return img;
    }
//    public static void savaImage(byte[] img, String fileName) {
//        BufferedOutputStream bos = null;
//        FileOutputStream fos = null;
//        File file = null;
//        try {
//            file = new File(fileName);
//            fos = new FileOutputStream(file);
//            bos = new BufferedOutputStream(fos);
//            bos.write(img);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            if (bos != null) {
//                try {
//                    bos.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }


}
