package com.gex.gex_riot_take_a_shit;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Game_Status extends Fragment {

    TextView t;

    Current_status_Data viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game__status, container, false);
        t = (TextView) v.findViewById(R.id.nah_bruh);
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