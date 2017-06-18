package com.bolex.androidhook.remoteservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bolex.remoteservice.SimpleAIDL;


/**
 * Created by Bolex on 2017/6/18.
 */

public class LocService extends Service {

    private SimpleAIDL simpleAIDL;
    private Thread thread;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
        Intent in = new Intent("com.bolex.aidl");
        in.setPackage("com.bolex.remoteservice");
        startService(in);
        bindService(in, connection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("myLog", "本地服务挂掉了");
            // 启动远程服务
            Intent in = new Intent("com.bolex.aidl");
            in.setPackage("com.bolex.remoteservice");
            startService(in);
            // 绑定远程服务
            bindService(in, connection, Context.BIND_IMPORTANT);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            simpleAIDL = SimpleAIDL.Stub.asInterface(service);
            try {
                simpleAIDL.sendMsg("123");
                String msg = simpleAIDL.getMsg();
                Log.d("myLog", "本地调用远程服务收到消息:" + msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }
    };
}

