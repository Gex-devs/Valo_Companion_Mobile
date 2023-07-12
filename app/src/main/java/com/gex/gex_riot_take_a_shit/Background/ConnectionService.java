package com.gex.gex_riot_take_a_shit.Background;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.gex.gex_riot_take_a_shit.R;
import java.net.URI;

public class ConnectionService extends Service {

    public ConnectionService() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("created channel");
            NotificationChannel channel = new NotificationChannel("valoCompanionNotify", "gamenotify",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLightColor(Color.BLUE);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String addr = intent.getStringExtra("addr");
        WebsocketServer client;
        String status;
        Log.d("onStartCommand", "onStartCommand: "+addr);

        try {
            client = WebsocketServer.getInstance(new URI(addr),this,this);
            if (!client.isOpen()){
                client.connect();
                status = "connected";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "valoCompanionNotify")
                        .setOngoing(true)
                        .setContentTitle("Valo Companion")
                        .setContentText("Your Device is "+status)
                        .setSmallIcon(R.drawable.valo)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                startForeground(1, builder.build());
            }

        } catch (Exception e) {
            Log.d("onStartCommand", "onStartCommand: "+e);
            throw new RuntimeException(e);
        }


        return Service.START_STICKY_COMPATIBILITY;
    }

}