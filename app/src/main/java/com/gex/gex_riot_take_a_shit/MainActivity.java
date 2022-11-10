package com.gex.gex_riot_take_a_shit;

import static com.gex.gex_riot_take_a_shit.MainActivity.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
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
        WebsocketServer Gex = new WebsocketServer();
        setContentView(R.layout.activity_main);
        // Main Activity background color
        getWindow().getDecorView().setBackgroundColor(Color.argb(255,255,71,85));
        Gex.start();
        // https://github.com/Nightonke/JellyToggleButton#listener
        //JellyToggleButton server_Switch = findViewById(R.id.server_switch);
        //server_Switch.setJelly(Jelly.LAZY_TREMBLE_TAIL_SLIM_JIM);
        viewModel = new ViewModelProvider(this).get(Current_status_Data.class);
        viewModel.getFor_char().observe(this,item->{
            Gex.broadcast(item, Gex.getConnections());
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


        // Map on Notification
        viewModel.get_map().observe(this,item->{
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            Intent broadcastInt = new Intent(this,NotificationRecv.class);
            broadcastInt.putExtra("test_1","your mother");
            PendingIntent actionIntent = PendingIntent.getBroadcast(this,0,broadcastInt,PendingIntent.FLAG_UPDATE_CURRENT);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                    .setSmallIcon(R.drawable.valo)
                    .setContentTitle("Game Status")
                    .setContentText("MATCH FOUND")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.riot,"Dodge",actionIntent)
                    .addAction(R.drawable.riot,"Select Agent",actionIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(item+" Map"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat managerCompact = NotificationManagerCompat.from(MainActivity.ContextMethod());
            managerCompact.notify(1,builder.build());
        });

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
                .replace(R.id.fragmentContainerView, fragment_improved_ingame.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)       // name can be null
                .commit();
        // change this to the switch button
        /*
        server_Switch.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                // add logic with State variable
                if(state.equals(State.RIGHT)){
                    new StyleableToast
                            .Builder(ContextMethod())
                            .text("Hosted")
                            .length(2)
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.BLUE)
                            .iconStart(R.drawable.riot)
                            .show();
                    viewModel.Game_state("Connected");
                    Gex.start();
                }else if (state.equals(State.LEFT)){
                    new StyleableToast
                            .Builder(ContextMethod())
                            .text("Disabled")
                            .length(1)
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.RED)
                            .iconStart(R.drawable.riot)
                            .show();
                }
            }
        });

         */
    }

    public static void Agent_Select_fragment(){
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                fragmentManager.beginTransaction()
                        //.replace(R.id.fragmentContainerView, Agent_Selection_Menu.class, null)
                        .replace(R.id.fragmentContainerView, improved_Agent_sel_fragment.class, null)
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
                        .replace(R.id.fragmentContainerView, In_game.class, null)
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

class WebsocketServer extends WebSocketServer{

    WifiManager wifiMgr = (WifiManager) MainActivity.ContextMethod().getApplicationContext().getSystemService(WIFI_SERVICE);
    private static int TCP_PORT = 4444;
    private Set<WebSocket> conns;
    public WebsocketServer() {
        super(new InetSocketAddress(TCP_PORT));
        conns = new HashSet<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        Log.d("Websocket","Connection Opened");
        Log.d("Websocket","Host Name: "+ conn.getLocalSocketAddress().getHostString());
        Log.d("Websocket","Port: "+TCP_PORT);
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        viewModel.setIP_Addr("Connected");
        new StyleableToast
                .Builder(ContextMethod())
                .text("Connected")
                .textColor(Color.WHITE)
                .backgroundColor(Color.BLUE)
                .iconStart(R.drawable.riot)
                .show();


    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
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
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Game Log: " + message);
        for (WebSocket sock : conns) {
            sock.send(message);
        }

        viewModel.Selection(message.toString());

        switch (message){
            case "/Game/Maps/Triad/Triad":
                viewModel.set_map("Haven");
                break;
            case "/Game/Maps/Duality/Duality":
                viewModel.set_map("Bind");
                break;
            case "/Game/Maps/Bonsai/Bonsai":
                viewModel.set_map("Split");
                break;
            case "/Game/Maps/Ascent/Ascent":
                viewModel.set_map("Ascent");
                break;
            case "/Game/Maps/Port/Port":
                viewModel.set_map("Icebox");
                break;
            case "/Game/Maps/Foxtrot/Foxtrot":
                viewModel.set_map("Breeze");
                break;
            case "/Game/Maps/Canyon/Canyon":
                viewModel.set_map("Fracture");
                break;
            case "/Game/Maps/Pitt/Pitt":
                viewModel.set_map("Pearl");
                break;
        }



        /*
        switch (message) {
            case "MainMenu":
                viewModel.Game_state("MainMenu");
            case "CharacterSelectPersistentLevel":
                System.out.println("Agent select");
                Agent_Select_fragment();
            case "Game starting":
                System.out.println("Game Starting");
            case "match_start":
                System.out.println("Match Started");
            case "sup":
                System.out.println("Real game started");
                //Game_Fragment();
            default:
                viewModel.Selection(message.toString());
        }
        */


        if(message.equals("Game starting")){
            System.out.println("match started");
            Game_Fragment();
        }else if(message.equals("CharacterSelectPersistentLevel")){
            // Create a dodge Class and Intent to open Agent Select Class
            viewModel.for_char("get_map");
            Agent_Select_fragment();
        }else if(message.equals("game_end")){
            Game_Status_Fragment();
        }

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //ex.printStackTrace();
        if (conn != null) {
            conns.remove(conn);
            // do some thing if required
        }
        System.out.println(ex);
    }

    @Override
    public void onStart() {
        Log.d("Websocket","Started");
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipString = String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
        viewModel.setIP_Addr(ipString);
        viewModel.setPort_Addr(Integer.toString(TCP_PORT));
        /*
        Intent intent = new Intent(MainActivity.ContextMethod(), Agent_Selection_Menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.ContextMethod(), 0, intent, PendingIntent.FLAG_IMMUTABLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                .setSmallIcon(R.drawable.valo)
                .setContentTitle("Game Status")
                .setContentText("MATCH FOUND")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.riot,"Dodge",pendingIntent)
                .addAction(R.drawable.riot,"Select Agent",pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notify_map))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat managerCompact = NotificationManagerCompat.from(MainActivity.ContextMethod());
        managerCompact.notify(1,builder.build());*/

    }

    @Override
    public  void stop(){

    }
    @Override
    public void broadcast(String text) {
        broadcast(text, conns);
    }


}

