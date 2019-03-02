package com.ailuoku6.golib.server;

import android.util.Log;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.App_Version;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

import static android.support.constraint.Constraints.TAG;

public class Version_Check {
    private String Check_api = ApiUrl.CHECK_UPDATE;
    //private String DownLoad_url = ApiUrl.DOWNLOAD_URL;

    public App_Version Check_update() throws IOException {
        App_Version appVersion = new App_Version();

        Connection connection = Jsoup.connect(Check_api);
        connection.timeout(6*1000)
                .method(Connection.Method.GET);

        Connection.Response response = connection.ignoreContentType(true).execute();

        //response.body();

        Log.d(TAG, "Check_update: "+response.body());

        JsonObject jsonObject = ((JsonArray) new JsonParser().parse(response.body())).get(0).getAsJsonObject();

        appVersion.VersionCode = jsonObject.get("apkInfo").getAsJsonObject().get("versionCode").getAsInt();

        appVersion.VersionName = jsonObject.get("apkInfo").getAsJsonObject().get("versionName").getAsString();

        return appVersion;
    }

}
