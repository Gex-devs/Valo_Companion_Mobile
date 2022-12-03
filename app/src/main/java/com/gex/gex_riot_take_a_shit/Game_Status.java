package com.gex.gex_riot_take_a_shit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.imageview.ShapeableImageView;
import com.hanks.htextview.base.HTextView;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.unstoppable.submitbuttonview.SubmitButton;

import java.io.IOException;


public class Game_Status extends Fragment implements View.OnClickListener{
    HTextView ip_output,ip_text,port_output;
    Current_status_Data viewModel;
    ShapeableImageView host;
    String myString;
    public WebsocketServer WebServer = new WebsocketServer();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            myString = savedInstanceState.getString("myString","default");
            ip_output.setText(myString);
        }
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT,enter,200);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("myString",ip_output.getText().toString());

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
        ip_output.setText(myString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game__status, container, false);

        if (savedInstanceState != null) {
            myString = savedInstanceState.getString("myString","default");
            ip_output.setText(myString);
        }

        ip_output = (HTextView) v.findViewById(R.id.IP_output);
        port_output = (HTextView) v.findViewById(R.id.PORT_output);
        ip_text = (HTextView) v.findViewById(R.id.IP_TEXT);
        host = (ShapeableImageView) v.findViewById(R.id.Host_btn);
        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);
        viewModel.getIP_Addr().observe(requireActivity(),item ->{
            ip_output.animateText(item);
        });
        viewModel.getPort_Addr().observe(requireActivity(),item ->{
            port_output.animateText(item);
        });
        viewModel.getFor_char().observe(requireActivity(),item->{
            WebServer.broadcast(item, WebServer.getConnections());
            System.out.println("Broadcast has been called");
            //Gex.broadcast("pls send", Gex.getConnections());
            System.out.println(item);
            // Works for other Mutalables expect for this, change it or fix it
        });




        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebServer.start();
                //Intent mis = new Intent(MainActivity.ContextMethod(), Svc.class);
                //MainActivity.ContextMethod().startService(mis);
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