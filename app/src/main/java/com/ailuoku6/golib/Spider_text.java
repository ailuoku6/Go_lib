package com.ailuoku6.golib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Spider_text extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spider_text);

        TextView textView = (TextView) findViewById(R.id.Spider_text);
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");

        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        textView.setText(text);

    }
}
