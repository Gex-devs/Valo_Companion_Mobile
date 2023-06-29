package com.gex.gex_riot_take_a_shit;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.view.MaterialListView;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.squareup.picasso.RequestCreator;

import org.java_websocket.drafts.Draft_6455;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class Game_Status extends Fragment implements View.OnClickListener{
    Current_status_Data viewModel;
    String myString;
    public String ip_addrs;
    TableLayout disco;
    MaterialListView mListView;
    Button cam;
    public static WebsocketServer client;
    NsdManager nsdManager = (NsdManager) MainActivity.ContextMethod().getApplicationContext().getSystemService(Context.NSD_SERVICE);


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
        cam = (Button) v.findViewById(R.id.button);
        if (savedInstanceState != null) {
            myString = savedInstanceState.getString("myString","default");
        }

        SwipeRefreshLayout swiper = (SwipeRefreshLayout) v.findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                discoverServices();
                swiper.setRefreshing(false);
            }
        });
        //https://github.com/dexafree/MaterialList
        mListView = (MaterialListView) v.findViewById(R.id.material_listview);


        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);
        viewModel.getFor_char().observe(requireActivity(),item->{
            //WebServer.broadcast(item, WebServer.getConnections());
            System.out.println("Broadcast has been called");
            //Gex.broadcast("pls send", Gex.getConnections());
            System.out.println(item);
            // Works for other Mutalables expect for this, change it or fix it
        });


        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                intent.setAction("com.google.zxing.client.android.SCAN");
                intent.putExtra("SAVE_HISTORY", false);
                startActivityForResult(intent, 0);

            }
        });

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String qrCode = data.getStringExtra("SCAN_RESULT");
                System.out.println(qrCode);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            client = new WebsocketServer(new URI("ws://"+qrCode.split(":")[0]+ ":"+qrCode.split(":")[1]), new Draft_6455());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        if (client != null) {
                            client.connect();
                        }
                    }
                });

                // Use the QR code here
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancelled scan
            }
        }
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
                ip_addrs = serviceInfo.getHost().toString().substring(1);
                Log.d("WifiSet", "onServiceResolved: Assigned value Ip");
                mDiscoveredServices.add(serviceInfo); // do i really need this? Good Question Past me! fking retard talking to himself
                add_discov_device_improved(serviceInfo.getServiceName(),serviceInfo.getHost().toString().substring(1) +":"+ serviceInfo.getPort());



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
                for (NsdServiceInfo serviceInfo : mDiscoveredServices) {
                    System.out.println(serviceInfo.getServiceName());
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
    public void add_discov_device_improved(String DeviceName,String IP_PORT){
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Card card = new Card.Builder(MainActivity.ContextMethod())
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_basic_image_buttons_card_layout)
                        .setTitle(DeviceName)
                        .setTitleGravity(Gravity.LEFT)
                        .setDescription(IP_PORT)
                        .setDescriptionGravity(Gravity.LEFT)
                        .setDrawable(R.drawable.computer_ico)
                        .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                            @Override
                            public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                                requestCreator.resize(200,200);
                            }
                        })
                        .addAction(R.id.left_text_button, new TextViewAction(MainActivity.ContextMethod())
                                .setText("Connect")
                                .setTextResourceColor(R.color.Button_Color)
                                .setTextResourceColor(R.color.black_button)
                                .setListener(new OnActionClickListener() {
                                    @Override
                                    public void onActionClicked(View view, Card card) {
                                        //Toasty.info(MainActivity.ContextMethod(), card.getProvider().getDescription().split(":")[0]+card.getProvider().getDescription().split(":")[1], Toast.LENGTH_SHORT, true).show();

                                        try {
                                            client = new WebsocketServer(new URI("ws://"+card.getProvider().getDescription().split(":")[0]+ ":"+card.getProvider().getDescription().split(":")[1]), new Draft_6455());
                                        } catch (URISyntaxException e) {
                                            e.printStackTrace();
                                        }
                                        if (client != null) {
                                            client.connect();
                                            System.out.printf("I am not null "+ client);
                                        }
                                    }
                                }))
                        .endConfig()
                        .build();
                
                mListView.getAdapter().add(card);
            }
        });

    }


}