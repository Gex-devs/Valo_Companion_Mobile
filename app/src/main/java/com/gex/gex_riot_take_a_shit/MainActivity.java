package com.gex.gex_riot_take_a_shit;

import static com.gex.gex_riot_take_a_shit.MainActivity.ContextMethod;
import static com.gex.gex_riot_take_a_shit.MainActivity.UI_Handler;
import static com.gex.gex_riot_take_a_shit.MainActivity.viewModel;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gex.gex_riot_take_a_shit.ThirdParty.Firebase;
import com.gex.gex_riot_take_a_shit.ThirdParty.ValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.FragmentSwitcher;
import com.gex.gex_riot_take_a_shit.Utils.util;
import com.gex.gex_riot_take_a_shit.enums.InfoType;

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
    public static FragmentSwitcher fragmentSwitcher;
    static FragmentManager fragmentManager;
    @SuppressLint("StaticFieldLeak")
    private static ViewGroup container;

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

        // Initialize Ui's
        setContentView(R.layout.activity_main);
        IntalizeBottomNavigation();
        getWindow().getDecorView().setBackgroundColor(Color.argb(255,255,71,85));


        // https://github.com/Nightonke/JellyToggleButton#listener
        viewModel = new ViewModelProvider(this).get(Current_status_Data.class);
        viewModel.getFor_char().observe(this,item->{
            //Gex.broadcast(item, Gex.getConnections());
            System.out.println("Broadcast has been called");
            //Gex.broadcast("pls send", Gex.getConnections());
            System.out.println(item);
            // Works for other Mutalables expect for this, change it or fix it
        });


        Firebase firebase = new Firebase(this);
        //Create instance of Api Handler to use as a singleton
        try {
            LocalApiHandler apiHandler = new LocalApiHandler();
            ValorantApi valorantApi = new ValorantApi();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        // Fragment container
        container = findViewById(R.id.fragmentContainerView);


        fragmentSwitcher = new FragmentSwitcher(fragmentManager,container);

        //flutterFragment = (FlutterFragment) fragmentManager.findFragmentByTag("flutter_fragment");


        // Start Fragment
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, Game_Status.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)       // name can be null
                .commit();
    }
    @Override
    public void update(Observable observable, Object o) {
        System.out.println("called from acitivty");
    }

    private void IntalizeBottomNavigation(){
        // Bottom Navigation Bar https://github.com/Ashok-Varma/BottomNavigation
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
    }
}

class WebsocketServer extends WebSocketClient {
    public WebsocketServer(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("Websocket","Connection Opened");
        send("Phone Connected");


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
        SetFirstFragment();

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
        FragmentSwitcher.Game_Status_Fragment();
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
        switch (message){
            case "MainMenu":
                System.out.println("ya main menu");
                try {
                    FragmentSwitcher.Qeue_Menu();
                } catch (JSONException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Agent_sel":
                FragmentSwitcher.Agent_Select_fragment();
                break;
            case "In_Game":
                FragmentSwitcher.Game_Fragment();
                break;
            default:
                try {
                    if (util.IdentifyDataType(message) == InfoType.Chat){
                        viewModel.SetPartyChat(message);
                    }
                    viewModel.Selection(message);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    @Override
    public void onError(Exception ex) {
        //ex.printStackTrace();
        System.out.println(ex);
    }

    private void SetFirstFragment(){
        Log.d("FirstFragment", "SetFirstFragment: Called Function");
        try {
            switch (LocalApiHandler.current_state()){
                case "MainMenu":
                    FragmentSwitcher.Qeue_Menu();
                    break;
                case "Agent_sel":
                    FragmentSwitcher.Agent_Select_fragment();
                    break;
                case "In_Game":
                    FragmentSwitcher.Game_Fragment();
                    break;
            }

        } catch (IOException | ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }
}


