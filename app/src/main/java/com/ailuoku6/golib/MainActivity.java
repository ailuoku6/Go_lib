package com.ailuoku6.golib;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ailuoku6.golib.Adapter.NoticeAdapter;
import com.ailuoku6.golib.Model.Notice;
import com.ailuoku6.golib.Model.userInfo;
import com.ailuoku6.golib.server.NoticeServer;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MaterialSearchView searchView;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private ImageView imageView;
    private TextView name;

    private ProgressDialog progressDialog;

    private final int SHOENOTICE = 1;
    private List<Notice> noticeList = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case SHOENOTICE:
                    showNotice();
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("武科大图书馆");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //navigationView加载完成后才能获取imageview对象，否则会造成空指针异常
        imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.name_text);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent("com.ailuoku6.golib.Search_Result");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.putExtra("keyword",query);
                searchView.closeSearch();
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CookiesManage.IsLoged){
                    Intent intent = new Intent("com.ailuoku6.golib.USERDETAIL");
                    intent.addCategory("android.intent.category.DEFAULT");
                    startActivity(intent);
                }else {
                    Intent intent = new Intent("com.ailuoku6.golib.LOGIN");
                    intent.addCategory("android.intent.category.DEFAULT");
                    startActivity(intent);
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中......");
        progressDialog.setCancelable(false);

        progressDialog.show();
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        ReadData();
        if(CookiesManage.IsLoged){
            name.setText(userInfo.userName);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)||searchView.isSearchOpen()) {
            drawer.closeDrawer(GravityCompat.START);
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            if(CookiesManage.IsLoged){
                Intent intent = new Intent("com.ailuoku6.golib.USERDETAIL");
                intent.addCategory("android.intent.category.DEFAULT");
                startActivity(intent);
            }else {
                Intent intent = new Intent("com.ailuoku6.golib.LOGIN");
                intent.addCategory("android.intent.category.DEFAULT");
                startActivity(intent);
            }
        } else if (id == R.id.nav_gallery) {
//            Intent intent = new Intent("com.ailuoku6.golib.MYBORROW");
//            intent.addCategory("android.intent.category.DEFAULT");
//            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent("com.ailuoku6.golib.LOGIN");
            intent.addCategory("android.intent.category.DEFAULT");
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initData() {

        //获取通知

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NoticeServer noticeServer = new NoticeServer();
                    noticeList = noticeServer.getNotice();
                    Message message = new Message();
                    message.what = SHOENOTICE;
                    //message.obj = noticeList;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        }).start();

    }

    private void showNotice(){
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rececle_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            NoticeAdapter noticeAdapter = new NoticeAdapter(noticeList);
            recyclerView.setAdapter(noticeAdapter);

            progressDialog.dismiss();

    }

    public void ReadData(){
        SharedPreferences sp = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        String json = sp.getString("cookies", "");
        String name = sp.getString("name","");
        Gson gson = new Gson();
        if(CookiesManage.cookies!=null&&json != ""){
            CookiesManage.cookies = gson.fromJson(json, CookiesManage.cookies.getClass());
            CookiesManage.IsLoged = true;
        }
        if(name!=""&&name!=null){
            userInfo.userName = name;
        }
    }

}
