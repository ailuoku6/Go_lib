package com.ailuoku6.golib;

import android.annotation.SuppressLint;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ailuoku6.golib.Adapter.NoticeAdapter;
import com.ailuoku6.golib.Model.Notice;
import com.ailuoku6.golib.server.NoticeServer;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MaterialSearchView searchView;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("武科大图书馆");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //navigationView加载完成后才能获取imageview对象，否则会造成空指针异常
        ImageView imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);

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
                if(true){
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        ReadCookies();
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
        } else if (id == R.id.nav_gallery) {
//            Intent intent = new Intent("com.ailuoku6.golib.MYBORROW");
//            intent.addCategory("android.intent.category.DEFAULT");
//            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

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

                }finally {

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
    }

    public void ReadCookies(){
        SharedPreferences sp = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        String json = sp.getString("cookies", "");
        Gson gson = new Gson();
        if(CookiesManage.cookies!=null){
            CookiesManage.cookies = gson.fromJson(json, CookiesManage.cookies.getClass());
        }
    }

}
