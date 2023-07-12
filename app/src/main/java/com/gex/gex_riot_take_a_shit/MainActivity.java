package com.gex.gex_riot_take_a_shit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gex.gex_riot_take_a_shit.Background.WebsocketServer;
import com.gex.gex_riot_take_a_shit.ThirdParty.Firebase;
import com.gex.gex_riot_take_a_shit.ThirdParty.ValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.FragmentSwitcher;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer {

    public static Handler UI_Handler = new Handler();
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
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }

        // Fragment container
        container = findViewById(R.id.fragmentContainerView);


        fragmentSwitcher = new FragmentSwitcher(fragmentManager,container);


        if (WebsocketServer.getInstance() != null){
            WebsocketServer.SetFirstFragment();
        }
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

    private void IntalizeBottomNavigation()  {
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


