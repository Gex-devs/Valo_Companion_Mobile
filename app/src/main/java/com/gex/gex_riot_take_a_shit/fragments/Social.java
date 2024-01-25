package com.gex.gex_riot_take_a_shit.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gex.gex_riot_take_a_shit.Current_status_Data;
import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class Social extends Fragment {

    private Current_status_Data viewModel;

    public Social(Current_status_Data viewModel){
        this.viewModel = viewModel;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_social, container, false);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.lala);

        viewModel.getSocialFriendsData().observe(requireActivity(), item ->{

            View socialFriend = inflater.inflate(R.layout.socia_friend, linearLayout, false);
            LayoutInflater fla = LayoutInflater.from(getActivity());

            try {
                String name = OfficalValorantApi.getInstance().GetNameByPuuid(item.getString("puuid"));
                String map = util.get_respective_map_name(item.getString("provisioningFlow") == "ShootingRange"? "Range" : item.getString("matchMap").split("/")[item.getString("matchMap").split("/").length - 1]);
                String Score = String.valueOf(item.getInt("partyOwnerMatchScoreAllyTeam")) + "-" +String.valueOf(item.getInt("partyOwnerMatchScoreEnemyTeam"));
                String partyState = item.getString("partyAccessibility");
                String gameMode = item.getString("queueId");
//                textView.setText(name +":" + map +":"+ Score +":"+ partyState + ":" + gameMode);
                linearLayout.addView(socialFriend);

            } catch (IOException | JSONException | NoSuchAlgorithmException |
                     KeyManagementException | ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        return  v;
    }
}