package com.gex.gex_riot_take_a_shit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gex.gex_riot_take_a_shit.Background.WebsocketServer;

import java.io.IOException;


public class NotificationRecv extends BroadcastReceiver  {

    Current_status_Data viewModel;
    Game_Status game_status = new Game_Status();
    public void MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("DODGE_ACTION")){
            Log.d("Notification Event", "onReceive: Dodged");
            try {
                LocalApiHandler.Dodge();
                assert WebsocketServer.getInstance() != null;
                WebsocketServer.getInstance().setAllowNotification(true);
            } catch (IOException e) {
                Log.d("Notification Event","onReceive: Failed To Dodge");
            }
        }

        if (intent.getAction().equals("GONE_ACTION")){
            assert WebsocketServer.getInstance() != null;
            WebsocketServer.getInstance().setAllowNotification(true);
        }

        if (intent.getAction().equals("DISCONNECT")){
            assert WebsocketServer.getInstance() != null;
            WebsocketServer.getInstance().stopAndDestroy();
        }

    }

}
