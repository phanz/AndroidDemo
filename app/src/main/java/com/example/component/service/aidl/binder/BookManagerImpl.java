package com.example.component.service.aidl.binder;

import android.os.RemoteException;

import com.example.demo.IBookManager;
import com.example.component.service.aidl.bean.Book;

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
}
