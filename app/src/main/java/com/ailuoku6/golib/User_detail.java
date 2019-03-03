package com.ailuoku6.golib;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.ailuoku6.golib.Model.userInfo;
import com.ailuoku6.golib.server.Get_User_info;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.jude.swipbackhelper.SwipeBackHelper;

import java.io.IOException;

public class User_detail extends AppCompatActivity {

    private userInfo userinfo;
    private ExpandingList expandingList;
    private final int GETUSERINFO = 1;
    private TextView name;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case GETUSERINFO:
                    fillData((userInfo) msg.obj);
                    break;
                default:break;
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
        setContentView(R.layout.activity_user_detail);

        expandingList = (ExpandingList) findViewById(R.id.expanding_list_main);
        name = (TextView) findViewById(R.id.name_text);

        toolbar = (Toolbar) findViewById(R.id.User_detail_toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);

        InitData();

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

    public void InitData(){
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userinfo = new Get_User_info().getUserinfo(CookiesManage.cookies);
                    Message message = new Message();
                    message.what = GETUSERINFO;
                    message.obj = userinfo;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        }).start();
    }

    //http://www.lib.wust.edu.cn:8780/reader/ajax_renew.php?bar_code=A1091327&check=567D8F2A&captcha=bgsf&time=1547723823193
//    bar_code: A1091327
//    check: 567D8F2A
//    captcha: bgsf
//    time: 1547723823193

    public void fillData(userInfo userinfo){
        name.setText(userinfo.getUserName());
        ((TextView) expandingList.createNewItem(R.layout.expanding_layout).findViewById(R.id.title)).setText("姓名: "+userinfo.getUserName());
        ((TextView) expandingList.createNewItem(R.layout.expanding_layout).findViewById(R.id.title)).setText(userinfo.getBorrowTime());
        ((TextView) expandingList.createNewItem(R.layout.expanding_layout).findViewById(R.id.title)).setText(userinfo.getBreakrulesTime());
        ((TextView) expandingList.createNewItem(R.layout.expanding_layout).findViewById(R.id.title)).setText(userinfo.getDebt());
        ((TextView) expandingList.createNewItem(R.layout.expanding_layout).findViewById(R.id.title)).setText(userinfo.getFiveDay_out());
        ((TextView) expandingList.createNewItem(R.layout.expanding_layout).findViewById(R.id.title)).setText(userinfo.getMaxBookNum());
        ((TextView) expandingList.createNewItem(R.layout.expanding_layout).findViewById(R.id.title)).setText(userinfo.getOut_dateBook());
        ((TextView) expandingList.createNewItem(R.layout.expanding_layout).findViewById(R.id.title)).setText(userinfo.getPrifileStart());
        ((TextView) expandingList.createNewItem(R.layout.expanding_layout).findViewById(R.id.title)).setText(userinfo.getPrifileEnd());
        ((TextView) expandingList.createNewItem(R.layout.expanding_layout).findViewById(R.id.title)).setText(userinfo.getUserType());

        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

}
