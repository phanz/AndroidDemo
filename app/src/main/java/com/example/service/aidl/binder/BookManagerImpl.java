package com.example.service.aidl.binder;

import android.os.RemoteException;
import android.util.Log;

import com.example.demo.IBookManager;
import com.example.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phanz on 2017/4/27.
 */

public class BookManagerImpl extends IBookManager.Stub {

    private List<Book> mBooks;

    public BookManagerImpl(){
        mBooks = new ArrayList<>();
    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        return mBooks;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        mBooks.add(book);
    }

    @Override
    public void addBookIn(Book book) throws RemoteException {
        synchronized (this){
            if(mBooks == null){
                mBooks = new ArrayList<>();
            }
            if(book == null){
                book = new Book();
                book.setPrice(2333);
                book.setName("Python");
            }

            if(!mBooks.contains(book)){
                mBooks.add(book);
                Log.d("TAG","Add Book:"+book.getName());
            }
        }
    }

    @Override
    public void addBookOut(Book book) throws RemoteException {
        synchronized (this){
            if(mBooks == null){
                mBooks = new ArrayList<>();
            }
            if(book == null){
                book = new Book();
                book.setPrice(2333);
                book.setName("Python");
            }

            if(!mBooks.contains(book)){
                mBooks.add(book);
                Log.d("TAG","Add Book:"+book.getName());
            }
        }
    }

    @Override
    public void addBookInOut(Book book) throws RemoteException {
        synchronized (this){
            if(mBooks == null){
                mBooks = new ArrayList<>();
            }
            if(book == null){
                book = new Book();
                book.setPrice(2333);
                book.setName("Python");
            }

            if(!mBooks.contains(book)){
                mBooks.add(book);
                Log.d("TAG","Add Book:"+book.getName());
            }
        }
    }
}
