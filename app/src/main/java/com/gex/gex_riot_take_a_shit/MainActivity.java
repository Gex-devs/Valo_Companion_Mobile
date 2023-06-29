package com.gex.gex_riot_take_a_shit;

import static com.gex.gex_riot_take_a_shit.MainActivity.Agent_Select_fragment;
import static com.gex.gex_riot_take_a_shit.MainActivity.ContextMethod;
import static com.gex.gex_riot_take_a_shit.MainActivity.Game_Fragment;
import static com.gex.gex_riot_take_a_shit.MainActivity.Game_Status_Fragment;
import static com.gex.gex_riot_take_a_shit.MainActivity.Qeue_Menu;
import static com.gex.gex_riot_take_a_shit.MainActivity.UI_Handler;
import static com.gex.gex_riot_take_a_shit.MainActivity.WIFI_SERVICE;
import static com.gex.gex_riot_take_a_shit.MainActivity.viewModel;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gex.gex_riot_take_a_shit.Utils.util;
import com.gex.gex_riot_take_a_shit.fragments.gameFragments.fragment_improved_ingame;
import com.gex.gex_riot_take_a_shit.fragments.gameFragments.improved_Agent_sel_fragment;
import com.gex.gex_riot_take_a_shit.fragments.gameFragments.menuSelection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import io.flutter.embedding.android.FlutterFragment;
import io.github.muddz.styleabletoast.StyleableToast;


public class MainActivity extends AppCompatActivity implements Observer {

    public static FlutterFragment flutterFragment;
    // Handler is Must to change UI_ElEMENTS outside of the  mainactivity class
    static Handler UI_Handler = new Handler();
    // Might not need it in Main Activity, Need to go to Fragments

    static FragmentManager fragmentManager;
    boolean  netOpen;
    private static ViewGroup container;

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

        //Create instance of Api Handler
        try {
            LocalApiHandler apiHandler = new LocalApiHandler();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("CloudMessaging", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("CloudMessaging", token);
                        Toast.makeText(MainActivity.this, "Got the token", Toast.LENGTH_SHORT).show();
                    }
                });

        // Fragment container
        container = (ViewGroup) findViewById(R.id.fragmentContainerView);


        flutterFragment = (FlutterFragment) fragmentManager
                .findFragmentByTag("flutter_fragment");
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
                .addItem(new BottomNavigationItem(R.drawable.controller,"EXTRA"))
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
                        //Game_Status_Fragment();
                        System.out.println("First_fragment");
                        break;
                    case 1:
                        //Store_Fragment();
                        // Replace with Toasty if possible
                        Toast.makeText(MainActivity.this,"Store is still under development",Toast.LENGTH_SHORT).show();
                        Log.e("Bottom_Tab", "onTabSelected: Store Fragment is Under Development" );
                        System.out.println("Second_fragment");
                        break;
                    case 2:
                        //party_fragment();
                        Toast.makeText(MainActivity.this,"Feature is still under development",Toast.LENGTH_SHORT).show();
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
        Fragment fragment = new improved_Agent_sel_fragment();
        new FragmentTransactionTask(fragmentManager, container, fragment).execute();
    }
    public static void Qeue_Menu() throws JSONException, IOException {

        Fragment fragment = new menuSelection(viewModel);
        new FragmentTransactionTask(fragmentManager, container, fragment).execute();

        /*UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, menuSelection.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null) // name can be null
                        .commit();
            }
        });*/
    }
    public static void Game_Status_Fragment(){
        Fragment fragment = new Game_Status();
        new FragmentTransactionTask(fragmentManager, container, fragment).execute();
    }
    public  static void Store_Fragment(){
        //Fragment fragment = new Store_Fragment();
        //new FragmentTransactionTask(fragmentManager, container, fragment).execute();
        if(flutterFragment == null){
            flutterFragment = FlutterFragment.createDefault();
        }
        new FragmentTransactionTask(fragmentManager, container, flutterFragment).execute();

    }
    public static void Game_Fragment(){
        Fragment fragment = new fragment_improved_ingame();
        new FragmentTransactionTask(fragmentManager, container, fragment).execute();
    }

    public static void party_fragment(){
        Fragment fragment = new party();
        new FragmentTransactionTask(fragmentManager, container, fragment).execute();
    }
    @Override
    public void update(Observable observable, Object o) {
        System.out.println("called from acitivty");
    }

    // Use this three functions incase you want to change a Ui element in the main_activity
}

class WebsocketServer extends WebSocketClient {
    // This is the NotificationManager
    private NotificationManager mNotificationManager;
    improved_Agent_sel_fragment Agent_sel_frag = new improved_Agent_sel_fragment();
    WifiManager wifiMgr = (WifiManager) MainActivity.ContextMethod().getApplicationContext().getSystemService(WIFI_SERVICE);
    public WebsocketServer(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("Websocket","Connection Opened");
        send("from_phone");
        UI_Handler.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("valo_connection","valo_network", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_connection")
                        .setSmallIcon(R.drawable.valo)
                        .setContentTitle("Connected")
                        .setContentText("Your Device is connected to Valorant")
                        .setAutoCancel(false)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat managerCompact = NotificationManagerCompat.from(MainActivity.ContextMethod());
                managerCompact.notify(2,builder.build());
            }
        });
        try {
            switch (LocalApiHandler.current_state()){
                case "MainMenu":
                    System.out.println("ya main menu");
                    Qeue_Menu();
                    break;
                case "Agent_sel":
                    Agent_Select_fragment();
                    break;
                case "In_Game":
                    Game_Fragment();
            }

        } catch (IOException | ExecutionException | InterruptedException | JSONException e) {
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
        Log.d("Socket","Closed connection: "+reason+code    );
        Game_Status_Fragment();
        NotificationManagerCompat managerCompact = NotificationManagerCompat.from(MainActivity.ContextMethod());
        managerCompact.cancel(2);
        try{
            managerCompact.cancel(1);
        }catch (Exception e){
            System.out.println("Notification most likely doesn't exist");
        }

    }

    @SuppressLint({"SetTextI18n", "NotificationTrampoline"})
    @Override
    public void onMessage(String message) {
        System.out.println("Game Log: " + message);
        viewModel.Selection(message);

        // Notification Backup
        /*Intent intent = new Intent(MainActivity.ContextMethod(), MainActivity.class);
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
                managerCompact.notify(1,builder.build());*/


        /*if(message.equals("Game starting")){
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
        }*/
        switch (message) {
            case "Game starting":
                System.out.println("match started");
                Game_Fragment();
                break;
            case "CharacterSelectPersistentLevel":
                Intent intent = new Intent(MainActivity.ContextMethod(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.ContextMethod(), 0, intent, 0);
                Intent broadcastInt = new Intent(MainActivity.ContextMethod(),NotificationRecv.class);
                broadcastInt.putExtra("test_1","your mother");
                PendingIntent actionIntent = PendingIntent.getBroadcast(MainActivity.ContextMethod(),0,broadcastInt,PendingIntent.FLAG_UPDATE_CURRENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("valo_Start","valo_Start", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = MainActivity.ContextMethod().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder = null;
                try {
                    builder = new NotificationCompat.Builder(MainActivity.ContextMethod(), "valo_Start")
                            .setSmallIcon(R.drawable.valo)
                            .setContentTitle("Game Status")
                            .setContentText("Select Agent")
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .addAction(R.drawable.riot,"Dodge",actionIntent)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(util.get_respective_map_name(LocalApiHandler.get_map_name())))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                } catch (IOException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                NotificationManagerCompat managerCompact = NotificationManagerCompat.from(MainActivity.ContextMethod());
                managerCompact.notify(1,builder.build());
                Log.d("Fragment", "Agent select fragment");
                Agent_Select_fragment();
                break;
            case "game_end":
                //Game_Status_Fragment();
                break;
            case "MainMenu":
                try {
                    Qeue_Menu();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onError(Exception ex) {
        //ex.printStackTrace();

        System.out.println(ex);
    }


}

class FragmentTransactionTask extends AsyncTask<Void, Void, Void> {

    private FragmentManager fragmentManager;
    private ViewGroup container;
    private Fragment fragment;

    public FragmentTransactionTask(FragmentManager fragmentManager, ViewGroup container, Fragment fragment) {
        this.fragmentManager = fragmentManager;
        this.container = container;
        this.fragment = fragment;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Start the fragment transaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Add, remove, or replace the fragment
        transaction.add(container.getId(), fragment);

        // Commit the transaction
        transaction.commitAllowingStateLoss();

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // Update the user interface to reflect the changes
    }
}


