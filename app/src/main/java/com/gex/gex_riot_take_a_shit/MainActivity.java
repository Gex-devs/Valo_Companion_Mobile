package com.gex.gex_riot_take_a_shit;

import static com.gex.gex_riot_take_a_shit.MainActivity.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import io.github.muddz.styleabletoast.StyleableToast;


public class MainActivity extends AppCompatActivity implements Observer {


    // Handler is Must to change UI_ElEMENTS outside of the  mainactivity class
    // Might not need it in Main Activity, Need to go to Fragments

    static Handler UI_Handler = new Handler();
    static FragmentManager fragmentManager;
    {
        fragmentManager = getSupportFragmentManager();
    }

    public static Current_status_Data viewModel;

    public static Context ContextMethod() {
        Context context = App.getContext();
        return context;
    }
    //static WebsocketServer Gex = new WebsocketServer();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    @SuppressLint({"NewApi", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObservableObject.getInstance().addObserver(this);
        // Web socket server
        //WebsocketServer Gex = new WebsocketServer();
        setContentView(R.layout.activity_main);
        // Main Activity background color
        getWindow().getDecorView().setBackgroundColor(Color.argb(255,255,71,85));
        //Gex.start();
        // https://github.com/Nightonke/JellyToggleButton#listener
        //JellyToggleButton server_Switch = findViewById(R.id.server_switch);
        //server_Switch.setJelly(Jelly.LAZY_TREMBLE_TAIL_SLIM_JIM);
        viewModel = new ViewModelProvider(this).get(Current_status_Data.class);
        viewModel.getFor_char().observe(this,item->{
            //Gex.broadcast(item, Gex.getConnections());
            System.out.println("Broadcast has been called");
            //Gex.broadcast("pls send", Gex.getConnections());
            System.out.println(item);
            // Works for other Mutalables expect for this, change it or fix it
        });


        /*RemoteInput remoteInput = new RemoteInput.Builder("get_me")
                .setLabel("ans")
                        .build();
        Intent replyintetn = new Intent(this,NotificationRecv.class);
        PendingIntent replyIntent = PendingIntent.getBroadcast(this,0,replyintetn,0);
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(R.drawable.val_1,"mandem",replyIntent).addRemoteInput().build();*/


        /**/
        // STARTS HERE *  Bottom Navigation Bar https://github.com/Ashok-Varma/BottomNavigation
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar)findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.valo,"GAME"))
                .addItem(new BottomNavigationItem(R.drawable.riot, "STORE"))
                .addItem(new BottomNavigationItem(R.drawable.controller,"Party"))
                .setInActiveColor(R.color.Inactive_color_bottom_bar)
                .setBarBackgroundColor(R.color.Bottom_bar_color)
                .setActiveColor(R.color.Valo_Color)
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setElevation(0);
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 0:
                        Game_Status_Fragment();
                        System.out.println("First_fragment");
                        break;
                    case 1:
                        Store_Fragment();
                        System.out.println("SECOND_fragment");
                        break;
                    case 2:
                        party_fragment();
                        System.out.println("Third_fragment");
                        break;
                }
            }
            @Override
            public void onTabUnselected(int position) { }
            @Override
            public void onTabReselected(int position) {}
        });
        // ENDS HERE * Bottom Navigation Bar
        // switch here man
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, Game_Status.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)       // name can be null
                .commit();
    }

    public static void Agent_Select_fragment(){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.ContextMethod(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                MainActivity.ContextMethod().startActivity(intent);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, improved_Agent_sel_fragment.class, null)
                                .setReorderingAllowed(true)
                                .addToBackStack(null) // name can be null
                                .commit();

                    }
                }, 2000);
            }
        });
    }
    public static void Qeue_Menu(){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, menuSelection.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null) // name can be null
                        .commit();
            }
        });
    }
    public static void Game_Status_Fragment(){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, Game_Status.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null) // name can be null
                        .commit();
            }
        });
    }
    public static void Store_Fragment(){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, Store_Fragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null) // name can be null
                        .commit();

            }
        });
    }
    public static void Game_Fragment(){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment_improved_ingame.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null) // name can be null
                        .commit();
            }
        });
    }

    public static void party_fragment(){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, party.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null) // name can be null
                        .commit();
            }
        });
    }
    @Override
    public void update(Observable observable, Object o) {
        System.out.println("called from acitivty");
        viewModel.for_char("Dodge");
    }

    // Use this three functions incase you want to change a Ui element in the main_activity
}

class WebsocketServer extends WebSocketClient {

    WifiManager wifiMgr = (WifiManager) MainActivity.ContextMethod().getApplicationContext().getSystemService(WIFI_SERVICE);
    public WebsocketServer(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("Websocket","Connection Opened");
        try {
            switch (pythonRestApi.current_state()){
                case "MainMenu":
                    Qeue_Menu();
                    break;
                case "Agent_sel":
                    Agent_Select_fragment();
                    break;
                case "In_Game":
                    Game_Fragment();
            }

        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        new StyleableToast
                .Builder(ContextMethod())
                .text("Connected")
                .textColor(Color.WHITE)
                .backgroundColor(Color.BLUE)
                .iconStart(R.drawable.riot)
                .show();


    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("Socket","Closed connection ");
        Game_Status_Fragment();
        new StyleableToast
                .Builder(ContextMethod())
                .text("Disconnected")
                .textColor(Color.WHITE)
                .backgroundColor(Color.RED)
                .iconStart(R.drawable.riot)
                .show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMessage(String message) {
        System.out.println("Game Log: " + message);
        viewModel.Selection(message.toString());
        switch (message){
            case "/Game/Maps/Triad/Triad":
                viewModel.set_map("Haven");
                Intent intent = new Intent(MainActivity.ContextMethod(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.ContextMethod(), 0, intent, 0);
                Intent broadcastInt = new Intent(MainActivity.ContextMethod(),NotificationRecv.class);
                broadcastInt.putExtra("test_1","your mother");
                PendingIntent actionIntent = PendingIntent.getBroadcast(MainActivity.ContextMethod(),0,broadcastInt,PendingIntent.FLAG_UPDATE_CURRENT);



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                        .setSmallIcon(R.drawable.valo)
                        .setContentTitle("Game Status")
                        .setContentText("MATCH FOUND")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.riot,"Dodge",actionIntent)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Haven"+" Map"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat managerCompact = NotificationManagerCompat.from(MainActivity.ContextMethod());
                managerCompact.notify(1,builder.build());
                break;
            case "/Game/Maps/Duality/Duality":
                viewModel.set_map("Bind");
                Intent intent2 = new Intent(MainActivity.ContextMethod(), MainActivity.class);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(MainActivity.ContextMethod(), 0, intent2, 0);
                Intent broadcastInt2 = new Intent(MainActivity.ContextMethod(),NotificationRecv.class);
                broadcastInt2.putExtra("test_1","your mother");
                PendingIntent actionIntent2 = PendingIntent.getBroadcast(MainActivity.ContextMethod(),0,broadcastInt2,PendingIntent.FLAG_UPDATE_CURRENT);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder2 = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                        .setSmallIcon(R.drawable.valo)
                        .setContentTitle("Game Status")
                        .setContentText("MATCH FOUND")
                        .setContentIntent(pendingIntent2)
                        .setAutoCancel(true)
                        .addAction(R.drawable.riot,"Dodge",actionIntent2)
                        .addAction(R.drawable.riot,"Select Agent",actionIntent2)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Bind"+" Map"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat managerCompact2 = NotificationManagerCompat.from(MainActivity.ContextMethod());
                managerCompact2.notify(1,builder2.build());
                break;
            case "/Game/Maps/Bonsai/Bonsai":
                viewModel.set_map("Split");
                Intent intent3 = new Intent(MainActivity.ContextMethod(), MainActivity.class);
                PendingIntent pendingIntent3 = PendingIntent.getActivity(MainActivity.ContextMethod(), 0, intent3, 0);
                Intent broadcastInt3 = new Intent(MainActivity.ContextMethod(),NotificationRecv.class);
                broadcastInt3.putExtra("test_1","your mother");
                PendingIntent actionIntent3 = PendingIntent.getBroadcast(MainActivity.ContextMethod(),0,broadcastInt3,PendingIntent.FLAG_UPDATE_CURRENT);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder3 = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                        .setSmallIcon(R.drawable.valo)
                        .setContentTitle("Game Status")
                        .setContentText("MATCH FOUND")
                        .setContentIntent(pendingIntent3)
                        .setAutoCancel(true)
                        .addAction(R.drawable.riot,"Dodge",actionIntent3)
                        .addAction(R.drawable.riot,"Select Agent",actionIntent3)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Split"+" Map"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat managerCompact3 = NotificationManagerCompat.from(MainActivity.ContextMethod());
                managerCompact3.notify(1,builder3.build());
                break;
            case "/Game/Maps/Ascent/Ascent":
                viewModel.set_map("Ascent");
                Intent intent4 = new Intent(MainActivity.ContextMethod(), MainActivity.class);
                PendingIntent pendingIntent4 = PendingIntent.getActivity(MainActivity.ContextMethod(), 0, intent4, 0);
                Intent broadcastInt4 = new Intent(MainActivity.ContextMethod(),NotificationRecv.class);
                broadcastInt4.putExtra("test_1","your mother");
                PendingIntent actionIntent4 = PendingIntent.getBroadcast(MainActivity.ContextMethod(),0,broadcastInt4,PendingIntent.FLAG_UPDATE_CURRENT);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder4 = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                        .setSmallIcon(R.drawable.valo)
                        .setContentTitle("Game Status")
                        .setContentText("MATCH FOUND")
                        .setContentIntent(pendingIntent4)
                        .setAutoCancel(true)
                        .addAction(R.drawable.riot,"Dodge",actionIntent4)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Ascent"+" Map"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat managerCompact4 = NotificationManagerCompat.from(MainActivity.ContextMethod());
                managerCompact4.notify(1,builder4.build());
                break;
            case "/Game/Maps/Port/Port":
                viewModel.set_map("Icebox");
                Intent intent5 = new Intent(MainActivity.ContextMethod(), MainActivity.class);
                PendingIntent pendingIntent5 = PendingIntent.getActivity(MainActivity.ContextMethod(), 0, intent5, 0);
                Intent broadcastInt5 = new Intent(MainActivity.ContextMethod(),NotificationRecv.class);
                broadcastInt5.putExtra("test_1","your mother");
                PendingIntent actionIntent5 = PendingIntent.getBroadcast(MainActivity.ContextMethod(),0,broadcastInt5,PendingIntent.FLAG_UPDATE_CURRENT);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder5 = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                        .setSmallIcon(R.drawable.valo)
                        .setContentTitle("Game Status")
                        .setContentText("MATCH FOUND")
                        .setContentIntent(pendingIntent5)
                        .setAutoCancel(true)
                        .addAction(R.drawable.riot,"Dodge",actionIntent5)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Icebox"+" Map"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat managerCompact5 = NotificationManagerCompat.from(MainActivity.ContextMethod());
                managerCompact5.notify(1,builder5.build());
                break;
            case "/Game/Maps/Foxtrot/Foxtrot":
                viewModel.set_map("Breeze");
                Intent intent6 = new Intent(MainActivity.ContextMethod(), MainActivity.class);
                PendingIntent pendingIntent6 = PendingIntent.getActivity(MainActivity.ContextMethod(), 0, intent6, 0);
                Intent broadcastInt6 = new Intent(MainActivity.ContextMethod(),NotificationRecv.class);
                broadcastInt6.putExtra("test_1",-1);
                PendingIntent actionIntent6 = PendingIntent.getBroadcast(MainActivity.ContextMethod(),0,broadcastInt6,PendingIntent.FLAG_UPDATE_CURRENT);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder6 = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                        .setSmallIcon(R.drawable.valo)
                        .setContentTitle("Game Status")
                        .setContentText("MATCH FOUND")
                        .setContentIntent(pendingIntent6)
                        .setAutoCancel(true)
                        .addAction(R.drawable.riot,"Dodge",actionIntent6)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Breeze"+" Map"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat managerCompact6 = NotificationManagerCompat.from(MainActivity.ContextMethod());
                managerCompact6.notify(1,builder6.build());
                break;
            case "/Game/Maps/Canyon/Canyon":
                viewModel.set_map("Fracture");
                Intent intent7 = new Intent(MainActivity.ContextMethod(), MainActivity.class);
                PendingIntent pendingIntent7 = PendingIntent.getActivity(MainActivity.ContextMethod(), 0, intent7, 0);
                Intent broadcastInt7 = new Intent(MainActivity.ContextMethod(),NotificationRecv.class);
                broadcastInt7.putExtra("test_1","your mother");
                PendingIntent actionIntent7 = PendingIntent.getBroadcast(MainActivity.ContextMethod(),0,broadcastInt7,PendingIntent.FLAG_UPDATE_CURRENT);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder7 = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                        .setSmallIcon(R.drawable.valo)
                        .setContentTitle("Game Status")
                        .setContentText("MATCH FOUND")
                        .setContentIntent(pendingIntent7)
                        .setAutoCancel(true)
                        .addAction(R.drawable.riot,"Dodge",actionIntent7)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Fracture"+" Map"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat managerCompact7 = NotificationManagerCompat.from(MainActivity.ContextMethod());
                managerCompact7.notify(1,builder7.build());
                break;
            case "/Game/Maps/Pitt/Pitt":
                viewModel.set_map("Pearl");
                Intent intent8 = new Intent(MainActivity.ContextMethod(), MainActivity.class);
                PendingIntent pendingIntent8 = PendingIntent.getActivity(MainActivity.ContextMethod(), 0, intent8, 0);
                Intent broadcastInt8 = new Intent(MainActivity.ContextMethod(),NotificationRecv.class);
                broadcastInt8.putExtra("test_1","your mother");
                PendingIntent actionIntent8 = PendingIntent.getBroadcast(MainActivity.ContextMethod(),0,broadcastInt8,PendingIntent.FLAG_UPDATE_CURRENT);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder8 = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                        .setSmallIcon(R.drawable.valo)
                        .setContentTitle("Game Status")
                        .setContentText("MATCH FOUND")
                        .setContentIntent(pendingIntent8)
                        .setAutoCancel(true)
                        .addAction(R.drawable.riot,"Dodge",actionIntent8)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Pearl"+" Map"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat managerCompact8 = NotificationManagerCompat.from(MainActivity.ContextMethod());
                managerCompact8.notify(1,builder8.build());
                break;
        }



        if(message.equals("Game starting")){
            System.out.println("match started");
            Game_Fragment();
        }else if(message.equals("CharacterSelectPersistentLevel")){
            // Create a dodge Class and Intent to open Agent Select Class
            Log.d("Fragment","Agent select fragment");
            //viewModel.for_char("get_map");
            send("get_map");
            System.out.println("Norification map has been called");
            Agent_Select_fragment();
        }else if(message.equals("game_end")){
            Game_Status_Fragment();
        }else if(message.equals("MainMenu")) {
            Qeue_Menu();
        }
    }

    @Override
    public void onError(Exception ex) {
        //ex.printStackTrace();

        System.out.println(ex);
    }


}

