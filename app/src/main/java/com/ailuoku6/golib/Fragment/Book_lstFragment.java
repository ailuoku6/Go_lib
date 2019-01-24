package com.ailuoku6.golib.Fragment;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ailuoku6.golib.Adapter.Book_lstAdapter;
import com.ailuoku6.golib.CookiesManage;
import com.ailuoku6.golib.Model.Book_lst;
import com.ailuoku6.golib.R;
import com.ailuoku6.golib.server.Get_Book_lst;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Book_lstFragment extends Fragment {

    //private RecyclerView recyclerView;

    private View mview;
    private final int SHOWBOOK_LST = 1;
    private ProgressDialog progressDialog;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case SHOWBOOK_LST:
                    ShowList((List<Book_lst>) msg.obj);
                    break;
                    default :break;
            }

        }
    };

    public Book_lstFragment() {
        // Required empty public constructor
    }

    public static Book_lstFragment newInstance() {
        Book_lstFragment fragment = new Book_lstFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_book_lst, container, false);

        //recyclerView = (RecyclerView) view.findViewById(R.id.Book_lst_list);
        mview = view;

        Log.d(TAG, "onCreateView: ");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("加载中......");
        progressDialog.setCancelable(false);
        progressDialog.show();
        GetBook_lst();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void GetBook_lst(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Book_lst> book_lsts = new Get_Book_lst().getBook_lst(CookiesManage.cookies);

                    Message message = new Message();
                    message.obj = book_lsts;
                    message.what = SHOWBOOK_LST;

                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        }).start();
    }

    public void ShowList(List<Book_lst> book_lsts){
        RecyclerView recyclerView = (RecyclerView) mview.findViewById(R.id.Book_lst_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mview.getContext());

        recyclerView.setLayoutManager(layoutManager);

        Book_lstAdapter book_lstAdapter = new Book_lstAdapter(book_lsts);

        recyclerView.setAdapter(book_lstAdapter);

        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }

    }

}
