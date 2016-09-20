package com.supengchao.github.ipc_binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by supengchao on 16/9/19.
 * Android Binder 通信
 */
public class BinderService extends Service {
    public static final String TAG = "BinderService";
    private IBinder iBinder;
    @Override
    public void onCreate() {
        super.onCreate();
        iBinder = new ProxyService(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
    private void print(String msg,TextView tv){
        try {
            Log.e(TAG, "Preparing printer...");
            tv.setText("Preparing printer...");
            Thread.sleep(1000);
            Log.e(TAG, "Connecting printer...");
            tv.setText("Connecting printer...");
            Thread.sleep(1000);
            Log.e(TAG, "Printing.... " + msg);
            tv.setText("Printing.... ");
            Thread.sleep(1000);
            Log.e(TAG, "Done");
        } catch (InterruptedException e) {
        }
        tv.setText(msg);
        Toast.makeText(this, "Printing is done.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 服务的代理类，对服务进行封装，暴露封装后的方法给外部使用
     */
    class ProxyService extends Binder{
        private BinderService binderService;
        public ProxyService(BinderService binderService){
            this.binderService = binderService;
        }
        public void print(String msg,TextView tv){
            binderService.print(msg,tv);
        }
    }
}
