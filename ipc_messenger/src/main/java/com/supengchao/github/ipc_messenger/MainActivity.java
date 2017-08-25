package com.supengchao.github.ipc_messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Messenger messenger = null;
    private TextView tv;
    private Button start;

    private Messenger clientMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String txt = msg.getData().getString("msg");
            Toast.makeText(MainActivity.this,txt,Toast.LENGTH_LONG).show();
        }
    });

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
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
                if (messenger == null)
                    return;
                try {
                    Message msg = Message.obtain();
                    msg.what = MessengerService.MSG_PRINT;
                    msg.obj = tv;
                    msg.replyTo = clientMessenger;
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unBindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindService();
    }

    private void bindService() {
        Intent intent = new Intent(this, MessengerService.class);
        isBind = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBindService() {
        if (serviceConnection == null)
            return;
        if (isBind) {
            unbindService(serviceConnection);
            isBind = false;
        }
    }

}
