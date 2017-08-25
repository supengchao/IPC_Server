package com.supengchao.github.ipc_aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by supengchao on 16/9/20.
 */
public class AidlService extends Service {
    private static final String TAG = "AidlService";
    private Handler mHandler = new Handler();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PrintInterface.Stub(){

            @Override
            public void print(String msg) throws RemoteException {
                AidlService.this.print(msg);
            }
        };
    }

    public void print(String msg) {
        try {
            Log.e(TAG, "Preparing printer...");
            Thread.sleep(1000);
            Log.e(TAG, "Connecting printer...");
            Thread.sleep(1000);
            Log.e(TAG, "Printing.... " + msg);
            Thread.sleep(1000);
            Log.e(TAG, "Done");
        } catch (InterruptedException e) {
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AidlService.this, "via AIDL Printing is done.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
