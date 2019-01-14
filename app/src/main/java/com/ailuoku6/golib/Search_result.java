package com.ailuoku6.golib;

import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");
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

        InitData(this,keyword);

    }

    private void InitData(final Context context, final String keyword){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Search_Book searchBook = new Search_Book();
                try {
                    bookList = searchBook.GetBooks(keyword);
                    ShowResult(bookList,context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void ShowResult(final List<Book> bookList, final Context context){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Books_list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                BooksAdapter booksAdapter = new BooksAdapter(bookList);
                recyclerView.setAdapter(booksAdapter);
            }
        });
    }
}
