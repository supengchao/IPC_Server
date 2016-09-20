package com.supengchao.github.ipc_messenger;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by supengchao on 16/9/19.
 */
public class MessengerService extends Service {
    public static final int MSG_PRINT = 1;
    public static final String TAG = "MessengerService";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_PRINT:
                    print("hahaha", (TextView) msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    Messenger messenger = new Messenger(handler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }


    public void print(String msg, TextView tv) {
        try {
            Log.e(TAG, "Preparing printer...");
            if (tv != null) {
                tv.setText("Preparing printer...");
            }
            Thread.sleep(1000);
            Log.e(TAG, "Connecting printer...");
            if (tv != null) {
                tv.setText("Connecting printer...");
            }
            Thread.sleep(1000);
            Log.e(TAG, "Printing.... " + msg);
            if (tv != null) {
                tv.setText("Printing.... ");
            }
            Thread.sleep(1000);
            Log.e(TAG, "Done");
        } catch (InterruptedException e) {
        }
        if (tv != null ) {
            tv.setText(msg);
        }
        Toast.makeText(this, "Messenger Printing is done.", Toast.LENGTH_LONG).show();
    }
}
