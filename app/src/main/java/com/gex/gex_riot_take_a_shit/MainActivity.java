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
import com.gex.gex_riot_take_a_shit.Background.XMPPServer;
import com.gex.gex_riot_take_a_shit.ThirdParty.Firebase;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.ThirdParty.ValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.FragmentSwitcher;

import org.json.JSONException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements Observer {

    public static Handler UI_Handler = new Handler();
    // Might not need it in Main Activity, Need to go to Fragments
    static FragmentManager fragmentManager;

    public static int BottomNavIndex = 1;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObservableObject.getInstance().addObserver(this);

        // Initialize Ui's
        setContentView(R.layout.activity_main);
        IntalizeBottomNavigation();
        getWindow().getDecorView().setBackgroundColor(Color.argb(255,255,71,85));

        viewModel = new ViewModelProvider(this).get(Current_status_Data.class);

        Firebase firebase = new Firebase(this);

        //Create instance of Api Handler to use as a singleton
        ValorantApi valorantApi = new ValorantApi();


        // Fragment container
        container = findViewById(R.id.fragmentContainerView);

        FragmentSwitcher.getInstance(fragmentManager,container);

        // Count down to make sure the thread is running
        final CountDownLatch latch = new CountDownLatch(1);

        Thread BackGroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Move the instantiation of OfficalValorantApi here
                    OfficalValorantApi officalValorantApi = OfficalValorantApi.getInstance(MainActivity.ContextMethod().getApplicationContext());
                    XMPPServer xmppServer = new XMPPServer(viewModel);
                    xmppServer.Start();
                    latch.countDown();
                } catch (IOException  | NoSuchAlgorithmException |
                         KeyManagementException e) {
                    Log.d("ApiThread", "run: " + e);
                    throw new RuntimeException(e);
                }catch (JSONException e){
                    Log.d("ApiThread", "run: "+e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        BackGroundThread.start();

        // Start with store fragment
        try {
            latch.await();
            FragmentSwitcher.Store_Fragment();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


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
                .addItem(new BottomNavigationItem(R.drawable.controller,"Social"))
                .setInActiveColor(R.color.Inactive_color_bottom_bar)
                .setBarBackgroundColor(R.color.Bottom_bar_color)
                .setActiveColor(R.color.Valo_Color)
                .setFirstSelectedPosition(1)
                .initialise();
        bottomNavigationBar.setElevation(0);
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                BottomNavIndex = position;
                switch (position){
                    case 0:
                        FragmentSwitcher.getInstance().Game_Status_Fragment();
                        break;
                    case 1:
                        FragmentSwitcher.getInstance().Store_Fragment();
                        // Replace with Toasty if possible
                        Toast.makeText(MainActivity.this,"Store is still under development",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        FragmentSwitcher.getInstance().Soical();
                        Toast.makeText(MainActivity.this,"Feature is still under development",Toast.LENGTH_SHORT).show();
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


