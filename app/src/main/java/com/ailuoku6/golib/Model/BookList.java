package com.ailuoku6.golib.Model;

import java.util.ArrayList;
import java.util.List;

public class BookList {
    private List<Book> bookList;
    public BookList(){
        bookList = new ArrayList<>();
    }

    public List<Book> getBookList() {
        return this.bookList;
    }

    public void addBookList(Book b){
        this.bookList.add(b);
    }

    public void cleanList(){
        this.bookList.clear();
    }

}
