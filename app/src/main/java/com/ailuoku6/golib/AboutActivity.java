package com.ailuoku6.golib;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.App_Version;
import com.ailuoku6.golib.server.Version_Check;
import com.eminayar.panter.PanterDialog;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;

import java.io.IOException;

import ezy.ui.view.RoundButton;

public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton imageButton;
    private TextView App_info;
    private RoundButton check_version;

    private String App_name;
    private int versionCode = 1;

    private final int HaveNew = 1;
    private final int Isnew = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);

            switch (msg.what){
                case HaveNew:
                    haveNew((App_Version) msg.obj);
                    break;
                case Isnew:
                    isNew((App_Version) msg.obj);
                    break;
                    default: break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(100)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.1f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setClosePercent(0.8f);//触发关闭Activity百分比

        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) findViewById(R.id.About_toolbar);
        imageButton = (ImageButton) findViewById(R.id.giuhub_img);

        App_info = (TextView) findViewById(R.id.app_Name);
        check_version = (RoundButton) findViewById(R.id.check_update);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/ailuoku6/Go_lib"));
                startActivity(intent);
            }
        });


        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(),0);
            versionCode = packageInfo.versionCode;

            App_name = getString(R.string.app_name) + "  v" + packageInfo.versionName;

            App_info.setText(App_name);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        check_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示进度条
                Check_update();
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    private void Check_update(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Version_Check versionCheck = new Version_Check();
                try {
                    App_Version appVersion = versionCheck.Check_update();
                    if (appVersion.VersionCode > versionCode){
                        Message message = new Message();
                        message.what = HaveNew;
                        message.obj = appVersion;
                        handler.sendMessage(message);
                    }else {
                        Message message = new Message();
                        message.what = Isnew;
                        message.obj = appVersion;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //关闭进度条
                }
            }
        }).start();
    }

    private void haveNew(App_Version appVersion){
        new PanterDialog(this)
                .setHeaderBackground(R.drawable.pattern_bg_blue)
                .setTitle("新版本")
                .setPositive("更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(ApiUrl.DOWNLOAD_URL);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })// You can pass also View.OnClickListener as second param
                .setNegative("不更新")
                .setMessage(appVersion.VersionName)
                .isCancelable(true)
                .show();
    }

    private void isNew(App_Version appVersion){
        //App_info.setText(appVersion.VersionName+"("+appVersion.VersionCode+")");
        Snackbar.make(findViewById(R.id.About_Acti), "已经是最新版!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
