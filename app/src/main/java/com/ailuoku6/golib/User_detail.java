package com.ailuoku6.golib;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ailuoku6.golib.Model.userInfo;
import com.ailuoku6.golib.server.Get_User_info;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import java.io.IOException;

public class User_detail extends AppCompatActivity {

    private userInfo userinfo;
    private ExpandingList expandingList;
    private final int GETUSERINFO = 1;
    private TextView name;

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
        setContentView(R.layout.activity_user_detail);

        expandingList = (ExpandingList) findViewById(R.id.expanding_list_main);
        name = (TextView) findViewById(R.id.name_text);

//        ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
//        ExpandingItem item1 = expandingList.createNewItem(R.layout.expanding_layout);
//
//        ((TextView) item.findViewById(R.id.title)).setText("It Works!!");
//        item.createSubItems(5);
//
//        View subItemZero = item.getSubItemView(0);
//        ((TextView) subItemZero.findViewById(R.id.sub_title)).setText("Cool");
//
//        View subItemOne = item.getSubItemView(1);
//        ((TextView) subItemOne.findViewById(R.id.sub_title)).setText("Awesome");
//
//        item.setIndicatorColorRes(R.color.blue);
//        item.setIndicatorIconRes(R.drawable.ic_arrow_back);
//
//        ((TextView) item1.findViewById(R.id.title)).setText("item2");
//
//        item1.createSubItems(2);
//
//        View sub0 = item1.getSubItemView(0);
//
//        ((TextView) sub0.findViewById(R.id.sub_title)).setText("niubi");
//
//        item1.setIndicatorColorRes(R.color.blue);
//        item1.setIndicatorIconRes(R.drawable.ic_arrow_back);
        InitData();

    }

    public void InitData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userinfo = new Get_User_info().getUserinfo(CookiesManage.cookies);
                    Message message = new Message();
                    message.what = GETUSERINFO;
                    message.obj = userinfo;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
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
    }

}
