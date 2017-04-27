package com.example.demo;

import com.example.demo.aidl.bean.Book;
interface IBookManager {
    List<Book> getBooks();
    void addBook(in Book book);
}