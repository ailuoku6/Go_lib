package com.ailuoku6.golib;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.CollapsingToolbarLayout;

import com.ailuoku6.golib.Adapter.BooksAdapter;
import com.ailuoku6.golib.Adapter.GuancangItem_Adapter;
import com.ailuoku6.golib.Model.Guancang_page;
import com.ailuoku6.golib.Model.Login_State;
import com.ailuoku6.golib.Util.FastBlurUtil;
import com.ailuoku6.golib.server.GetBook_img;
import com.ailuoku6.golib.server.GetGuancang;

import java.io.IOException;

public class Book_detail extends AppCompatActivity {

    private ImageView imageView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProgressDialog progressDialog;
    private CoordinatorLayout coordinatorLayout;

    private final int UPDATAIMG = 1;
    private final int UPDATAGUANCANG = 2;
    private String url;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case UPDATAIMG:
//                    imageView.setImageBitmap((Bitmap) msg.obj);
//                    progressDialog.dismiss();
                    setImg((Bitmap) msg.obj);
                    break;
                case UPDATAGUANCANG:
                    //Judge_state((Login_State) msg.obj);
                    UpdataGuancang((Guancang_page)msg.obj);
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
        url = intent.getStringExtra("url");

        toolbar = (Toolbar) findViewById(R.id.Book_detail_toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.Collapsing_toolbar);
        //collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.GRAY));
        collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.DKGRAY));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        imageView = (ImageView) findViewById(R.id.Book_img);

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

        progressDialog.show();
        InitData(url);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void InitData(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Guancang_page guancang_page = new GetGuancang().getGuancang(url);

                    Message message = new Message();
                    message.what = UPDATAGUANCANG;
                    message.obj = guancang_page;

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

    private void UpdataGuancang(Guancang_page guancang_page){
        UpdataImg(guancang_page.getISBN());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.guancang_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        GuancangItem_Adapter guancangItemAdapter = new GuancangItem_Adapter(guancang_page.getGuancangList());
        recyclerView.setAdapter(guancangItemAdapter);

        toolbar.setTitle(guancang_page.getBook_name());
        collapsingToolbarLayout.setTitle(guancang_page.getBook_name());
    }

    private void UpdataImg(final String ISBN){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] data;
                    data = new GetBook_img().updataImg(ISBN);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Message message = new Message();
                    message.what = UPDATAIMG;
                    message.obj = bitmap;
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

    private void setImg(Bitmap bitmap){

        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            Bitmap blurBitmap = FastBlurUtil.toBlur(bitmap, 3);
            //collapsingToolbarLayout.setBackground(new BitmapDrawable(blurBitmap));
            coordinatorLayout.setBackground(new BitmapDrawable(blurBitmap));
        }else {
            Bitmap blurBitmap = FastBlurUtil.toBlur(BitmapFactory.decodeResource(getResources(),R.drawable.book),3);
            //collapsingToolbarLayout.setBackground(new BitmapDrawable(blurBitmap));
            coordinatorLayout.setBackground(new BitmapDrawable(blurBitmap));
        }

        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

}
