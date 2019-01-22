package com.ailuoku6.golib;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import com.ailuoku6.golib.Model.Login_State;

public class Book_detail extends AppCompatActivity {

    private ImageView imageView;
    private Toolbar toolbar;
    private final int UPDATAIMG = 1;
    private final int UPDATAGUANCANG = 2;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case UPDATAIMG:
                    //Verimage.setImageBitmap((Bitmap) msg.obj);
                    break;
                case UPDATAGUANCANG:
                    //Judge_state((Login_State) msg.obj);
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        toolbar = (Toolbar) findViewById(R.id.Book_detail_toolbar);
        imageView = (ImageView) findViewById(R.id.Book_img);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
