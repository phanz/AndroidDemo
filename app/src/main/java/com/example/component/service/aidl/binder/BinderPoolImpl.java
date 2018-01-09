package com.example.component.service.aidl.binder;

import android.os.IBinder;
import android.os.RemoteException;

import com.example.demo.IBinderPool;

/**
 * Created by phanz on 2017/4/27.
 */

public class BinderPoolImpl extends IBinderPool.Stub{

    public static final int TYPE_BOOK_MANAGER = 1;
    public static final int TYPE_COMPUTE = 2;

    @Override
    public IBinder query(int type) throws RemoteException {
        IBinder binder = null;
        if(type == TYPE_BOOK_MANAGER){
            binder = new BookManagerImpl();
        }else if(type == TYPE_COMPUTE){
            binder = new ComputeImpl();
        }
        return binder;
    }
}
