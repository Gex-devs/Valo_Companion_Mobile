package com.gex.gex_riot_take_a_shit;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import com.labo.kaji.fragmentanimations.MoveAnimation;

public class Game_Status extends Fragment implements View.OnClickListener{
    TextView ip_input,port_input;
    String FF = "nigga";
    Current_status_Data viewModel;
    Button host;
    WebsocketServer WebServer = new WebsocketServer();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            ip_input.setText(savedInstanceState.getString(FF));
        }
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT,enter,200);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FF,ip_input.getText().toString());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game__status, container, false);

        ip_input = (TextView) v.findViewById(R.id.IP_Input);
        port_input = (TextView) v.findViewById(R.id.PORT_input);
        host = (Button) v.findViewById(R.id.Host_btn);
        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);

        viewModel.getIP_Addr().observe(requireActivity(),item ->{
            ip_input.setText(item);
        });
        viewModel.getPort_Addr().observe(requireActivity(),item ->{
            port_input.setText(item);
        });


        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WebServer.start();
                System.out.println("you called senior?");
            }
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

}