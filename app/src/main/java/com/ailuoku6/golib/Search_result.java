package com.ailuoku6.golib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ailuoku6.golib.Adapter.BooksAdapter;
import com.ailuoku6.golib.Model.Book;
import com.ailuoku6.golib.server.Search_Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Search_result extends AppCompatActivity {

    List<Book> bookList = new ArrayList<>();
    private String keyword;
    private final int SHOWRESULT = 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case SHOWRESULT:
                    ShowResult((List<Book>) msg.obj);
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        InitData(this,keyword);
    }

    private void InitData(final Context context, final String keyword){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Search_Book searchBook = new Search_Book();
                try {
                    bookList = searchBook.GetBooks(keyword);
                    Message message = new Message();
                    message.what = SHOWRESULT;
                    message.obj = bookList;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void ShowResult(List<Book> bookList){
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Books_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            BooksAdapter booksAdapter = new BooksAdapter(bookList);
            recyclerView.setAdapter(booksAdapter);
    }
}
