package com.bolex.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Bolex on 2017/6/18.
 */

public class RemoteService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SimpleAIDL.Stub(){

            private String data1;

            @Override
            public int sendMsg(String data) throws RemoteException {
                if (data != null) {
                    Log.d("myLog","服务端收到:"+data);
                    data1 = data;
                    return 1;
                }else{
                    return 0;
                }
            }

            @Override
            public String getMsg() throws RemoteException {
                return "来自服务器的消息"+data1;
            }
        };
    }
}
