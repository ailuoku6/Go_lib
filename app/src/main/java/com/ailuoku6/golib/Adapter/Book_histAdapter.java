package com.ailuoku6.golib.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ailuoku6.golib.Model.Book_hist;
import com.ailuoku6.golib.R;

import java.util.List;

public class Book_histAdapter extends RecyclerView.Adapter<Book_histAdapter.ViewHolder>{
    private List<Book_hist> book_hists;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View Book_histView;

        TextView book_name,zuozhe,guancang,borrowDate,returnDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Book_histView = itemView;
            book_name = (TextView) itemView.findViewById(R.id.Book_hist_name);
            zuozhe = (TextView) itemView.findViewById(R.id.zuozhe_hist);
            guancang = (TextView) itemView.findViewById(R.id.guancang_hist);
            borrowDate = (TextView) itemView.findViewById(R.id.Borrow_date);
            returnDate = (TextView) itemView.findViewById(R.id.return_date);
        }
    }

    public Book_histAdapter(List<Book_hist> book_hists){
        this.book_hists = book_hists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_hist_item,viewGroup,false);
        final Book_histAdapter.ViewHolder viewHolder = new Book_histAdapter.ViewHolder(view);
        viewHolder.Book_histView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Book_hist book_hist = book_hists.get(i);
        viewHolder.book_name.setText(book_hist.getName());
        viewHolder.zuozhe.setText(book_hist.getZuozhe());
        viewHolder.borrowDate.setText(book_hist.getBorrowDate());
        viewHolder.returnDate.setText(book_hist.getReturnDate());
        viewHolder.guancang.setText(book_hist.getGuancangdi());
    }

    @Override
    public int getItemCount() {
        return book_hists.size();
    }


}
