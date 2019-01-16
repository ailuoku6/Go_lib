package com.ailuoku6.golib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

public class User_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ExpandingList expandingList = (ExpandingList) findViewById(R.id.expanding_list_main);

        ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
        ExpandingItem item1 = expandingList.createNewItem(R.layout.expanding_layout);

        ((TextView) item.findViewById(R.id.title)).setText("It Works!!");
        item.createSubItems(5);

        View subItemZero = item.getSubItemView(0);
        ((TextView) subItemZero.findViewById(R.id.sub_title)).setText("Cool");

        View subItemOne = item.getSubItemView(1);
        ((TextView) subItemOne.findViewById(R.id.sub_title)).setText("Awesome");

        item.setIndicatorColorRes(R.color.blue);
        item.setIndicatorIconRes(R.drawable.ic_arrow_back);

        ((TextView) item1.findViewById(R.id.title)).setText("item2");

        item1.createSubItems(2);

        View sub0 = item1.getSubItemView(0);

        ((TextView) sub0.findViewById(R.id.sub_title)).setText("niubi");

        item1.setIndicatorColorRes(R.color.blue);
        item1.setIndicatorIconRes(R.drawable.ic_arrow_back);

    }
}
