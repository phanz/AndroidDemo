package com.example.service.aidl.binder;

import android.os.RemoteException;

import com.example.demo.ICompute;

/**
 * Created by phanz on 2017/4/27.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
