package com.gex.gex_riot_take_a_shit.Background;

import static com.gex.gex_riot_take_a_shit.MainActivity.viewModel;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.gex.gex_riot_take_a_shit.LocalApiHandler;
import com.gex.gex_riot_take_a_shit.NotificationRecv;
import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.Utils.FragmentSwitcher;
import com.gex.gex_riot_take_a_shit.Utils.PlayerStaticInfos;
import com.gex.gex_riot_take_a_shit.Utils.util;
import com.gex.gex_riot_take_a_shit.enums.InfoType;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class WebsocketServer extends WebSocketClient{

    private static WebsocketServer instance;
    private boolean allowNotification = true;
    private static Context Thecontext;
    private static Service Theservice;
    private static NotificationManagerCompat notificationManager;
    private URI serverUri;

    private WebsocketServer(URI serverUri) {
        super(serverUri, new Draft_6455());
        this.serverUri = serverUri; // Assign the value to the instance variable
        Log.d("Websocket", "Constructor called");

    }

    public static WebsocketServer getInstance() {
        if (instance == null) {
            return null;
            //throw new IllegalStateException("WebsocketServer instance has not been initialized.");
        }
        return instance;
    }

    public static WebsocketServer getInstance(URI serverUri,Context context,Service service) {
        if (instance == null) {
            instance = new WebsocketServer(serverUri);
            Thecontext = context;
            Theservice = service;
            notificationManager = NotificationManagerCompat.from(Thecontext);
        }
        return instance;
    }

    public void stopAndDestroy() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("Websocket", "Connection Opened");
        System.out.println("Connection Opened");
        send("Phone Connected");
        try {
            SetBaseInfo();
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        SetFirstFragment();

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("Websocket", "Closed connection: " + reason + code);
        Theservice.stopForeground(true);
        notificationManager.cancel(1);
        FragmentSwitcher.getInstance().Game_Status_Fragment();
        stopAndDestroy();

    }

    @SuppressLint({"SetTextI18n", "NotificationTrampoline"})
    @Override
    public void onMessage(String message) {
        System.out.println("Game Log: " + message);
        switch (message) {
            case "MainMenu":
                notificationManager.cancel(2);
                setAllowNotification(true);
                FragmentSwitcher.getInstance().Qeue_Menu();
                break;
            case "Agent_sel":
                try {
                    CheckForGameStartEvent();
                } catch (IOException | ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                FragmentSwitcher.getInstance().Agent_Select_fragment();
                break;
            case "In_Game":
                Log.d("WebSocket", "onMessage: InGame called");
                notificationManager.cancel(2);
                setAllowNotification(true);
                FragmentSwitcher.getInstance().Game_Fragment();
                break;
            default:
                if (util.IdentifyDataType(message) == InfoType.Chat) {
                    viewModel.SetPartyChat(message);
                } else {
                    viewModel.Selection(message);
                }
                break;
        }
    }

    @Override
    public void onError(Exception ex) {
        Log.d("WebSocket", "onError: "+ex);
    }

    public static void SetBaseInfo() throws IOException, ExecutionException, InterruptedException {
        PlayerStaticInfos.setMyID(LocalApiHandler.getMyID());
    }
    public static void SetFirstFragment() {
        Log.d("FirstFragment", "SetFirstFragment Called Function");

        try {
            switch (LocalApiHandler.current_state()) {
                case "MainMenu":
                    FragmentSwitcher.getInstance().Qeue_Menu();
                    break;
                case "Agent_sel":
                    FragmentSwitcher.getInstance().Agent_Select_fragment();
                    break;
                case "In_Game":
                    FragmentSwitcher.getInstance().Game_Fragment();
                    break;
                default:
                    FragmentSwitcher.getInstance().Game_Status_Fragment();
                    break;
            }

        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void CheckForGameStartEvent() throws IOException, ExecutionException, InterruptedException {
        if (allowNotification){
            Intent intent = new Intent(Thecontext, NotificationRecv.class);
            intent.setAction("DODGE_ACTION");
            Intent dismissIntent = new Intent(Thecontext, NotificationRecv.class);
            dismissIntent.setAction("GONE_ACTION");
            PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(
                    Thecontext, 1, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    Thecontext,
                    1,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder = new NotificationCompat.Builder(Thecontext, "gameEvent")
                    .setSmallIcon(R.drawable.valo)
                    .setContentTitle("Match Found")
                    .setContentText(util.get_respective_map_name(LocalApiHandler.get_map_name()))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setDeleteIntent(dismissPendingIntent)
                    .addAction(R.drawable.valo, "Dodge", pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Thecontext);

            // Create a notification channel for Android Oreo and higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        "gameEvent",
                        "gameChannel",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                notificationManager.createNotificationChannel(channel);
            }

            // Show the notification
            notificationManager.notify(2, builder.build());

            allowNotification = false;
        }


    }

    public void setAllowNotification(boolean set){
        allowNotification = set;
    }


}
