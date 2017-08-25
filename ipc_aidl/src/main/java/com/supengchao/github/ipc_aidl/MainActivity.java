package com.supengchao.github.ipc_aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.printservice.PrintService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Handler mHandler = new Handler();
    private TextView tv;
    private Button start;
    private PrintInterface printInterface;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            printInterface =PrintInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            printInterface = null;
        }
    };
    private boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(printInterface==null)
                    return;
                try {
                    printInterface.print("hahahha");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        printInterface =PrintInterface.Stub.asInterface(new PrintInterface.Stub(){

            @Override
            public void print(String msg) throws RemoteException {
                print1(msg);
            }
        });
//        bindService();
    }


    public void print1(String msg) {
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
                Toast.makeText(MainActivity.this, "via AIDL Printing is done.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, AidlService.class);
        isBind = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        if (isBind) {
            unbindService(serviceConnection);
            isBind = false;
        }
    }
}
