package com.ailuoku6.golib.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ailuoku6.golib.Api.ApiUrl;
import com.ailuoku6.golib.CookiesManage;
import com.ailuoku6.golib.Model.Book_lst;
import com.ailuoku6.golib.Model.Login_State;
import com.ailuoku6.golib.R;
import com.ailuoku6.golib.server.Renew_Book;
import com.ailuoku6.golib.server.getPatcha;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.enums.Animation;

import java.io.IOException;
import java.util.List;
import java.util.logging.LogRecord;

import ezy.ui.view.RoundButton;

public class Book_lstAdapter extends RecyclerView.Adapter<Book_lstAdapter.ViewHolder>{
    private List<Book_lst> book_lsts;

    private Dialog mydialog;
    private EditText vercode;
    private ImageView imageView;
    //private Button button;
    private RoundButton button;
    private Context mycontext;

    private final int UPDATEIMG = 1;
    private final int UPDATESTATE = 2;

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case UPDATEIMG:
                    imageView.setImageBitmap((Bitmap) msg.obj);
                    break;
                case UPDATESTATE:
                    UpdateState((String) msg.obj);
                    break;
                default:break;
            }
        }
    };

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

        mycontext = viewGroup.getContext();
        final Book_lstAdapter.ViewHolder viewHolder = new Book_lstAdapter.ViewHolder(view);

        viewHolder.Book_lstView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return viewHolder;
    }

    //http://www.lib.wust.edu.cn:8780/reader/ajax_renew.php?bar_code=A1091327&check=567D8F2A&captcha=owqz&time=1551591299277

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Book_lst book_lst = book_lsts.get(i);
        viewHolder.xujie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(v,book_lst);
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


    public void showCustomDialog(View v, final Book_lst book_lst){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        View view = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(view);

        vercode = (EditText) view.findViewById(R.id.vercod_input);
        imageView = (ImageView) view.findViewById(R.id.yanzhengma);
        button = (RoundButton) view.findViewById(R.id.renew);

        UpdateImg();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
                ReNew(book_lst,vercode.getText().toString());
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateImg();
            }
        });

        mydialog = builder.create();
        mydialog.show();

    }

    public void UpdateImg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getPatcha getPatcha = new getPatcha();
                    byte[] data;
                    data = getPatcha.updataCha(CookiesManage.cookies);
                    if (data!=null){
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        Message message = new Message();
                        message.what = UPDATEIMG;
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void ReNew(final Book_lst book_lst, final String vercode){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Renew_Book renew_book = new Renew_Book();
                try {
                    String out = renew_book.Renew(CookiesManage.cookies,book_lst,vercode);
                    Message message = new Message();
                    message.what = UPDATESTATE;
                    message.obj = out;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void UpdateState(String info){
        Toast.makeText(mycontext, info, Toast.LENGTH_SHORT).show();
    }

}
