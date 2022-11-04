package com.gex.gex_riot_take_a_shit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NotificationRecv extends BroadcastReceiver  {

    Current_status_Data viewModel;
    public void MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String m = intent.getStringExtra("test_1");
        System.out.println("Pls call me son of bitch");
        ObservableObject.getInstance().updateValue(intent);
        //viewModel.for_char("Dodge");
    }

}
