package com.gex.gex_riot_take_a_shit.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.gex.gex_riot_take_a_shit.R;
import com.labo.kaji.fragmentanimations.MoveAnimation;


public class Store_Fragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        // Fragment swap animation
        return MoveAnimation.create(MoveAnimation.RIGHT,enter,200);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_store_, container, false);


        return v;
    }
}