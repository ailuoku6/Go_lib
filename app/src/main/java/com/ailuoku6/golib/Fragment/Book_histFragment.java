package com.ailuoku6.golib.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ailuoku6.golib.Adapter.Book_histAdapter;
import com.ailuoku6.golib.CookiesManage;
import com.ailuoku6.golib.Model.Book_hist;
import com.ailuoku6.golib.R;
import com.ailuoku6.golib.server.Get_Book_hist;

import java.io.IOException;
import java.util.List;


public class Book_histFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;

    private View mview;
    private ProgressDialog progressDialog;
    private final int SHOWBOOK_HIST = 1;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOWBOOK_HIST:
                    ShowList((List<Book_hist>) msg.obj);
                    break;
                    default:break;
            }
        }
    };

    public Book_histFragment() {
        // Required empty public constructor
    }

    public static Book_histFragment newInstance() {
        Book_histFragment fragment = new Book_histFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_book_hist, container, false);
        mview = view;

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        GetList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void GetList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Book_hist> book_hists = new Get_Book_hist().getBook_hist(CookiesManage.cookies);
                    Message message = new Message();
                    message.what = SHOWBOOK_HIST;
                    message.obj = book_hists;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        }).start();
    }


    public void ShowList(List<Book_hist> book_hists){
        RecyclerView recyclerView = (RecyclerView) mview.findViewById(R.id.Book_hist_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mview.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Book_histAdapter book_histAdapter = new Book_histAdapter(book_hists);
        recyclerView.setAdapter(book_histAdapter);

        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }

    }
}
