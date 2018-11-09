package com.ailuoku6.golib.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.ailuoku6.golib.Notice.Notice;
import com.ailuoku6.golib.R;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private List<Notice> NoticeList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View noticeView;

        TextView subTitle;
        TextView notice_Date;
        public ViewHolder(View view){
            super(view);
            noticeView = view;
            subTitle = (TextView) view.findViewById(R.id.subtitle_text);
            notice_Date = (TextView) view.findViewById(R.id.date_text);
        }
    }

    public NoticeAdapter(List<Notice> noticeList){
        this.NoticeList = noticeList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_item,viewGroup,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.noticeView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Notice notice = NoticeList.get(position);
                //Toast.makeText(v.getContext(),notice.getSubTitle()+notice.getLink(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("com.ailuoku6.golib.NOTICE_DETAIL");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.putExtra("url",notice.getLink());
                v.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Notice notice = NoticeList.get(i);
        viewHolder.subTitle.setText(notice.getSubTitle());
        viewHolder.notice_Date.setText(notice.getDate());
        //viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return NoticeList.size();
    }


}
