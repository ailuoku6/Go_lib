package com.ailuoku6.golib.server;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class getPatcha {
    private Map cookies;
    private String url = "http://www.lib.wust.edu.cn:8780/reader/captcha.php";

    public getPatcha(Map cookies){
        this.cookies = cookies;
    }

    public void updataCha() throws IOException {
        Map<String,String> head = new HashMap<>();
        Connection connection = Jsoup.connect(url+"?"+Math.random());
        connection.timeout(5*1000)
                .method(Connection.Method.GET);
        Connection.Response res = connection.ignoreContentType(true).cookies(cookies).execute();
        byte[] img = res.bodyAsBytes();
        System.out.println("from getcha:"+res.headers());
        //savaImage(img,"test.png");
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
