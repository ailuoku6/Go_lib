package com.ailuoku6.golib.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ailuoku6.golib.Model.Book;
import com.ailuoku6.golib.R;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private List<Book> bookList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View BookView;

        TextView book_name,zuozhe,guancang,chubanshe,kejie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BookView = itemView;
            book_name = (TextView) itemView.findViewById(R.id.book_name);
            zuozhe = (TextView) itemView.findViewById(R.id.zuozhe);
            guancang = (TextView) itemView.findViewById(R.id.guancang);
            chubanshe = (TextView) itemView.findViewById(R.id.chubanshe);
            kejie = (TextView) itemView.findViewById(R.id.kejie);
        }
    }

    public BooksAdapter(List<Book> bookList){
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_item,viewGroup,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.BookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder viewHolder, int i) {
        Book book = bookList.get(i);
        viewHolder.book_name.setText(book.getName());
        viewHolder.zuozhe.setText(book.getZuozhe());
        viewHolder.guancang.setText(book.getGuancang());
        viewHolder.chubanshe.setText(book.getChubanshe());
        viewHolder.kejie.setText(book.getKejie());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
