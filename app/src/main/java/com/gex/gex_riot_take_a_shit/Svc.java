package com.gex.gex_riot_take_a_shit;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelStoreOwner;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;

import org.java_websocket.drafts.Draft_6455;

import java.net.URI;
import java.net.URISyntaxException;

import es.dmoral.toasty.Toasty;

public class Svc extends Service  {

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new LocalBinder();

    // This is the class that we will use to bind to the service
    public class LocalBinder extends Binder {
        Svc getService() {
            return Svc.this;
        }
    }

    // This method is called when the service is first created
    @Override
    public void onCreate() {
        super.onCreate();
        Thread mBackgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                WebsocketServer client = null;
                try {
                    client = new WebsocketServer(new URI("ws://192.168.1.19:8765"), new Draft_6455());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                if (client != null) {
                    client.connect();
                }
            }
        });
        mBackgroundThread.start();
        // Show the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "valo_Start")
                .setSmallIcon(R.drawable.valo)
                .setContentTitle("Your notification title")
                .setContentText("Your notification text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        startForeground(1, builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
