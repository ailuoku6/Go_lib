package com.ailuoku6.golib.Model;

import java.util.ArrayList;
import java.util.List;

public class Search_pages {
    private String pre;
    private String next;
    private String num_pages;
    private String result_count;
    private List<Book> books;

    public Search_pages(){
        books = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getNext() {
        return next;
    }

    public String getNum_pages() {
        return num_pages;
    }

    public String getPre() {
        return pre;
    }

    public String getResult_count() {
        return result_count;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setNum_pages(String num_pages) {
        this.num_pages = num_pages;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public void setResult_count(String result_count) {
        this.result_count = result_count;
    }

    public void addBook(Book book){
        books.add(book);
    }

}
