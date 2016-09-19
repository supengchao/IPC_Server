package com.example.service;


import com.example.aidl.IService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AIDLService extends Service {
	

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface
        return new IService.Stub() {
			@Override
			public String hello(String name) throws RemoteException {
				// TODO Auto-generated method stub
				return "hello"+name;
			}
		};
    }

   
}
