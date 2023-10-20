package com.gex.gex_riot_take_a_shit.fragments;

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
import android.widget.Toast;

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
import com.gex.gex_riot_take_a_shit.Background.ConnectionService;
import com.gex.gex_riot_take_a_shit.Background.WebsocketServer;
import com.gex.gex_riot_take_a_shit.Current_status_Data;
import com.gex.gex_riot_take_a_shit.MainActivity;
import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.FragmentSwitcher;
import com.gex.gex_riot_take_a_shit.Utils.signInChecker;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.squareup.picasso.RequestCreator;

import org.json.JSONException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Game_Status extends Fragment {
    String myString;
    Button retryButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (OfficalValorantApi.getInstance().isGameRunning()){
                FragmentSwitcher.Qeue_Menu();
            }
        } catch (IOException | JSONException | NoSuchAlgorithmException | ExecutionException |
                 InterruptedException | KeyManagementException e) {
            throw new RuntimeException(e);
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game__status, container, false);

        if (savedInstanceState != null) {
            myString = savedInstanceState.getString("myString","default");
        }

        retryButton = v.findViewById(R.id.retryBtn);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (OfficalValorantApi.getInstance().isGameRunning())
                        FragmentSwitcher.Qeue_Menu();
                    else
                        Toast.makeText(requireActivity(),"Game not running",Toast.LENGTH_LONG).show();
                } catch (IOException | JSONException | NoSuchAlgorithmException |
                         KeyManagementException | ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        return v;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }





}