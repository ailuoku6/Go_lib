package com.ailuoku6.golib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ailuoku6.golib.Model.Login_State;
import com.ailuoku6.golib.server.Login;
import com.ailuoku6.golib.server.getPatcha;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText user_name,password,vericode;
    private ImageView Verimage;
    private Button login;
    private Login login_;
    private getPatcha patcha;
    //private Map<String,String> cookies = new HashMap<>();
    private Login_State loginState;
    private final int UPDATA_PATCHA = 1;
    //private Context context;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case UPDATA_PATCHA:
                    Verimage.setImageBitmap((Bitmap) msg.obj);
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.Login_toolbar);
        toolbar.setTitle("登陆");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        vericode = (EditText) findViewById(R.id.vericode_input);
        Verimage = (ImageView) findViewById(R.id.vericode);
        login = (Button) findViewById(R.id.login);

        login_ = new Login();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            loginState = login_.LogIn(user_name.getText().toString(),password.getText().toString(),vericode.getText().toString(),CookiesManage.cookies);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                th.start();

                //预留进度条代码位置

                try {
                    th.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(loginState.getIslog()){
                    Snackbar.make(v, "scu", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    Snackbar.make(v, loginState.getERRORINFO(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        Verimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updata_patcha();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            InitData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void InitData() throws InterruptedException {

        patcha = new getPatcha();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    byte[] data;
                    data = patcha.FirstUpdataCha();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Message message = new Message();
                    message.what = UPDATA_PATCHA;
                    message.obj = bitmap;
                    handler.sendMessage(message);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public void Updata_patcha(){

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    byte[] data;
                    data = patcha.updataCha(CookiesManage.cookies);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Message message = new Message();
                    message.what = UPDATA_PATCHA;
                    message.obj = bitmap;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
