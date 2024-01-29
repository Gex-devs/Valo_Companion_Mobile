package com.gex.gex_riot_take_a_shit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.FragmentSwitcher;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import org.json.JSONException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;


public class Game_Status extends Fragment {
    String myString;
    Button retryButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (OfficalValorantApi.getInstance().isGameRunning()){
                //FragmentSwitcher.Qeue_Menu();
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