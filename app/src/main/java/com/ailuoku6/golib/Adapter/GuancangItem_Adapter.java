package com.ailuoku6.golib.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ailuoku6.golib.Model.Guancang;
import com.ailuoku6.golib.R;

import java.util.List;

public class GuancangItem_Adapter extends RecyclerView.Adapter<GuancangItem_Adapter.ViewHolder> {

    private List<Guancang> guancangList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View GuancangView;

        TextView suoshuhao,guancangdi,State;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            GuancangView = itemView;
            suoshuhao = (TextView) itemView.findViewById(R.id.suoshuhao);
            guancangdi = (TextView) itemView.findViewById(R.id.guancangdi);
            State = (TextView) itemView.findViewById(R.id.State);
        }
    }

    public GuancangItem_Adapter(List<Guancang> guancangList){
        this.guancangList = guancangList;
    }

    @NonNull
    @Override
    public GuancangItem_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.guancang_item,viewGroup,false);
        final GuancangItem_Adapter.ViewHolder viewHolder = new GuancangItem_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GuancangItem_Adapter.ViewHolder viewHolder, int i) {
        Guancang guancang = guancangList.get(i);
        viewHolder.suoshuhao.setText(guancang.getSuoshuhao());
        viewHolder.guancangdi.setText(guancang.getGuancangdi());
        viewHolder.State.setText(guancang.getState());
    }

    @Override
    public int getItemCount() {
        return guancangList.size();
    }
}
