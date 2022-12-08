package com.gex.gex_riot_take_a_shit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelStoreOwner;

public class Svc extends Service  {

    public static Current_status_Data viewModel;
    //public WebsocketServer WebServer = new WebsocketServer();
    public Svc() {
        super();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("service called");

        //WebServer.start();
        //modify this method, more than 10 seconds are hard-coded set
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
