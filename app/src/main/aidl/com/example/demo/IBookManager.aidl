package com.example.demo;

import com.example.model.Book;
interface IBookManager {
    List<Book> getBooks();
    void addBook(in Book book);

    //验证tag： in out inout
    void addBookIn(in Book book);
    void addBookOut(out Book book);
    void addBookInOut(inout Book book);
}