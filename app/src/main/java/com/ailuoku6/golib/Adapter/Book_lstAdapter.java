package com.ailuoku6.golib.Adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ailuoku6.golib.Model.Book_lst;
import com.ailuoku6.golib.R;

import java.util.List;

import ezy.ui.view.RoundButton;

public class Book_lstAdapter extends RecyclerView.Adapter<Book_lstAdapter.ViewHolder>{
    private List<Book_lst> book_lsts;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View Book_lstView;

        TextView book_name,guancang,borrowDate,returnDate;

        RoundButton xujie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Book_lstView = itemView;
            book_name = (TextView) itemView.findViewById(R.id.Book_lst_name);
            guancang = (TextView) itemView.findViewById(R.id.guancang_lst);
            borrowDate = (TextView) itemView.findViewById(R.id.Borrow_date_lst);
            returnDate = (TextView) itemView.findViewById(R.id.return_date_lst);
            xujie = (RoundButton) itemView.findViewById(R.id.xujie);
        }
    }

    public Book_lstAdapter(List<Book_lst> book_lsts){
        this.book_lsts = book_lsts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_lst_item,viewGroup,false);
        final Book_lstAdapter.ViewHolder viewHolder = new Book_lstAdapter.ViewHolder(view);

        viewHolder.Book_lstView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Book_lst book_lst = book_lsts.get(i);
        viewHolder.xujie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "暂未开放", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        viewHolder.book_name.setText(book_lst.getName_lst());
        viewHolder.borrowDate.setText(book_lst.getBorrowDate());
        viewHolder.returnDate.setText(book_lst.getReturnDate());
        viewHolder.guancang.setText(book_lst.getGuancangdi());
    }

    @Override
    public int getItemCount() {
        return book_lsts.size();
    }
}
