package com.gex.gex_riot_take_a_shit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.imageview.ShapeableImageView;
import com.hanks.htextview.base.HTextView;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.unstoppable.submitbuttonview.SubmitButton;

import org.apache.commons.io.compress.tar.TarEntry;
import org.java_websocket.drafts.Draft_6455;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Game_Status extends Fragment implements View.OnClickListener{
    Current_status_Data viewModel;
    String myString;
    TableLayout disco;
    NsdManager nsdManager = (NsdManager) MainActivity.ContextMethod().getApplicationContext().getSystemService(Context.NSD_SERVICE);
    //public WebsocketServer WebServer = new WebsocketServer();

    private List<NsdServiceInfo> mDiscoveredServices = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            myString = savedInstanceState.getString("myString","default");
        }
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT,enter,200);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        String myString = null;
        if (savedInstanceState != null) {
            myString = savedInstanceState.getString("myString");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game__status, container, false);

        if (savedInstanceState != null) {
            myString = savedInstanceState.getString("myString","default");
        }

        SwipeRefreshLayout swiper = (SwipeRefreshLayout) v.findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("refreshed");
                discoverServices();
                swiper.setRefreshing(false);
            }
        });

        // Create a JmDNS instance
        /*new Thread(new Runnable() {
            public void run() {
                try {
                    WifiManager wifi = (WifiManager) MainActivity.ContextMethod().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    final InetAddress deviceIpAddress = InetAddress.getByName(Formatter.formatIpAddress(wifi.getConnectionInfo().getIpAddress()));
                    WifiManager.MulticastLock multicastLock = wifi.createMulticastLock(getClass().getName());
                    multicastLock.setReferenceCounted(true);
                    multicastLock.acquire();

                    JmDNS jmDNS = JmDNS.create(deviceIpAddress, "Android Device Discovery");
                    jmDNS.addServiceListener("_http._tcp.local.", new ServiceListener() {//_services._dns-sd._udp _http._tcp.local. _workstation._tcp.local.
                        @Override
                        public void serviceAdded(ServiceEvent serviceEvent) {
                            jmDNS.requestServiceInfo("", "", 1000);
                            System.out.println("found some bitch");
                        }

                        @Override
                        public void serviceRemoved(ServiceEvent serviceEvent) {
                            System.out.println("someone left");
                        }

                        @Override
                        public void serviceResolved(ServiceEvent serviceEvent) {
                            System.out.println(serviceEvent.getInfo().getHostAddress());
                            System.out.println(serviceEvent.getInfo().getName());
                        }
                    });

                }catch(IOException e) {
                    Log.e("sjruf", e.getMessage(), e);
                }
            }
        }).start();*/

        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);
        viewModel.getFor_char().observe(requireActivity(),item->{
            //WebServer.broadcast(item, WebServer.getConnections());
            System.out.println("Broadcast has been called");
            //Gex.broadcast("pls send", Gex.getConnections());
            System.out.println(item);
            // Works for other Mutalables expect for this, change it or fix it
        });




        return v;

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onClick(View view) {
        viewModel.for_char("Say something i am giving up on u");
    }
    public void resolveService(NsdServiceInfo serviceInfo) {
        // Create a NsdManager instance
        NsdManager nsdManager = (NsdManager) MainActivity.ContextMethod().getApplicationContext().getSystemService(Context.NSD_SERVICE);

        // Create a NsdManager.ResolveListener instance
        NsdManager.ResolveListener resolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Resolution failed
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                // The service was resolved
                System.out.println(serviceInfo.getHost());
                System.out.println(serviceInfo.getServiceName());
                System.out.println(serviceInfo.getPort());
                System.out.println(serviceInfo.getServiceType());
                mDiscoveredServices.add(serviceInfo);
                add_discov_device(serviceInfo.getServiceName());

                /*
                WebsocketServer client = null;
                try {
                    client = new WebsocketServer(new URI("ws://192.168.1.19:8765"), new Draft_6455());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                if (client != null) {
                    client.connect();
                }*/
                // getView().findViewByID();
            }
        };

        // Start resolving the service
        nsdManager.resolveService(serviceInfo, resolveListener);
    }

    public void discoverServices() {
        // Create a NsdManager instance
        NsdManager nsdManager = (NsdManager) MainActivity.ContextMethod().getApplicationContext().getSystemService(Context.NSD_SERVICE);

        // Create a NsdManager.DiscoveryListener instance
        NsdManager.DiscoveryListener discoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                // Discovery failed
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                // Discovery failed
            }

            @Override
            public void onDiscoveryStarted(String serviceType) {
                // Discovery started
                System.out.println("Discovery Started");
                mDiscoveredServices.clear();
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                // Discovery stopped
                System.out.println("discovery stopped");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        disco = getView().findViewById(R.id.discovery_table);
                        disco.removeAllViews();
                    }
                });
                for (NsdServiceInfo serviceInfo : mDiscoveredServices) {
                    add_discov_device(serviceInfo.getServiceName());
                }
            }

            @Override
            public void onServiceFound(NsdServiceInfo serviceInfo) {
                // A service was found
                resolveService(serviceInfo);
            }

            @Override
            public void onServiceLost(NsdServiceInfo serviceInfo) {
                // A service was lost
                System.out.println("Service lost");
            }
        };

        // Start discovery
        nsdManager.discoverServices("_http._tcp.", NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }
    public void add_discov_device(String DeviceName){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                disco = getView().findViewById(R.id.discovery_table);

                disco.removeAllViews();
                TableRow Placement_TableRow = new TableRow(MainActivity.ContextMethod());
                Placement_TableRow.setBackgroundColor(Color.WHITE);
                Placement_TableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

                ImageView comp_image = new ImageView(MainActivity.ContextMethod());
                comp_image.setImageResource(R.drawable.computer_ico);

                TextView Device_Name = new TextView(MainActivity.ContextMethod());
                Device_Name.setText(DeviceName);
                Typeface typeface = ResourcesCompat.getFont(MainActivity.ContextMethod(), R.font.poppins_bold);
                Device_Name.setTypeface(typeface);

                Placement_TableRow.addView(comp_image);
                Placement_TableRow.addView(Device_Name);
                Placement_TableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebsocketServer client = null;
                        try {
                            client = new WebsocketServer(new URI("ws://192.168.1.19:8765"), new Draft_6455());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        if (client != null) {
                            client.connect();
                        }
                    }
                });

                disco.addView(Placement_TableRow);
            }
        });

    }


}