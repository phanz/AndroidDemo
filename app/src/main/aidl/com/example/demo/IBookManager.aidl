package com.example.demo;

import com.example.component.service.aidl.bean.Book;
interface IBookManager {
    List<Book> getBooks();
    void addBook(in Book book);
}