package com.ailuoku6.golib;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ailuoku6.golib.Adapter.BooksAdapter;
import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.Model.Book;
import com.ailuoku6.golib.Model.Search_pages;
import com.ailuoku6.golib.server.Search_Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.URLEncoder;

import ezy.ui.view.RoundButton;

public class Search_result extends AppCompatActivity {

    //List<Book> bookList = new ArrayList<>();
    private String keyword;
    private final int SHOWRESULT = 1;
    private Search_pages searchPages;
    private TextView pages_index;
    private ProgressDialog progressDialog;

    private String preUrl;
    private String nextUrl;
//    private Button pre;
//    private Button next;
    private Search_Book searchBook;

    private RoundButton pre;
    private RoundButton next;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case SHOWRESULT:
                    ShowResult((Search_pages) msg.obj);
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        Toolbar toolbar = (Toolbar) findViewById(R.id.result_toolbar);
        toolbar.setTitle("\""+keyword+"\""+"搜索结果");
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

        searchBook = new Search_Book();

        pages_index = (TextView) findViewById(R.id.num_pages);
//        pre = (Button) findViewById(R.id.pre);
//        next = (Button) findViewById(R.id.next);

        pre = (RoundButton) findViewById(R.id.pre);
        next = (RoundButton) findViewById(R.id.next);

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preUrl!=""&&preUrl!=null){

                    GetPageByUrl(preUrl);

                }else {
                    Snackbar.make(findViewById(R.id.Search_result_view),R.string.firstPage,1500).setAction("Action", null).show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nextUrl!=""&&nextUrl!=null){
                    GetPageByUrl(nextUrl);
                }else {
                    Snackbar.make(findViewById(R.id.Search_result_view),R.string.lastPage,1500).setAction("Action", null).show();
                }
            }
        });

        progressDialog.show();

        InitData(this,keyword);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void InitData(final Context context, final String keyword){

        new Thread(new Runnable() {
            @Override
            public void run() {
                //Search_Book searchBook = new Search_Book();
                try {
                    //String DecodeKey = URLEncoder.encode(keyword,"UTF-8");
                    searchPages = searchBook.GetSearch_page(keyword);
                    Message message = new Message();
                    message.what = SHOWRESULT;
                    message.obj = searchPages;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        }).start();
    }

    private void GetPageByUrl(final String url){
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    message.what = SHOWRESULT;
                    message.obj = searchBook.GetpageByUrl(ApiUrl.SEARCH_BOOK+url);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        }).start();
    }

    private void ShowResult(Search_pages search_pages){
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Books_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            BooksAdapter booksAdapter = new BooksAdapter(search_pages.getBooks());
            recyclerView.setAdapter(booksAdapter);
            pages_index.setText(search_pages.getNum_pages());
            preUrl = search_pages.getPre();
            nextUrl = search_pages.getNext();
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
    }
}
