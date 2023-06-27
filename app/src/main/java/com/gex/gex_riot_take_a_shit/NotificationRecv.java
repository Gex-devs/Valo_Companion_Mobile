package com.gex.gex_riot_take_a_shit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;


public class NotificationRecv extends BroadcastReceiver  {

    Current_status_Data viewModel;
    Game_Status game_status = new Game_Status();
    public void MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String m = intent.getStringExtra("test_1");
        System.out.println("Pls call me son of bitch");
        ObservableObject.getInstance().updateValue(intent);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.cancel(1);
        try {
            RestApiCalls.Dodge();
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        MainActivity.ContextMethod().startActivity(intent);
        //context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

    }

}
