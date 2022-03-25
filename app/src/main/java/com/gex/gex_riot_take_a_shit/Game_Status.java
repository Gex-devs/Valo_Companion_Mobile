package com.gex.gex_riot_take_a_shit;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.MoveAnimation;

public class Game_Status extends Fragment {

    TextView t;
    Button G;
    Current_status_Data viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT,enter,200);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's state here
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game__status, container, false);

        t = (TextView) v.findViewById(R.id.nah_bruh);
        G = (Button) v.findViewById(R.id.button);
        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.setText("changed");
            }
        });
        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);
        viewModel.getAnotherItem().observe(requireActivity(),item ->{
            t.setText(item);
        });


        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }


}