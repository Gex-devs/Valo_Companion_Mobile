package com.gex.gex_riot_take_a_shit.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gex.gex_riot_take_a_shit.Background.XMPPServer;
import com.gex.gex_riot_take_a_shit.Current_status_Data;
import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.ThirdParty.ValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.util;
import com.gex.gex_riot_take_a_shit.enums.GameModes;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Social extends Fragment {

    private Current_status_Data viewModel;
    private Map<String, View> viewMap = new HashMap<>();


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

            try {
                String puuid = item.getString("puuid");
                String name = OfficalValorantApi.getInstance().GetNameByPuuid(puuid);
                String sessionLoopState = item.getString("sessionLoopState");
                String gameMode = GameModes.getByCodeName(item.getString("queueId")).getDisplayName();
                String playerStatus = "can't be null";
                String playerCard = item.getString("playerCardId");

                boolean isIdle = item.getBoolean("isIdle");

                if (isIdle){
                    playerStatus = "Away";
                }
                else if (sessionLoopState.equals("MENUS")){
                    String partyState = item.getString("partyState");
                    // Game mode could be appended at the end
                    switch (partyState){
                        case "DEFAULT":
                            playerStatus = "Lobby - " + gameMode;
                            break;
                        case "MATCHMAKING":
                            playerStatus = "In Queue - " + gameMode;
                            break;
                        case "MATCHMADE_GAME_STARTING":
                            playerStatus = "Game Found - " + gameMode;
                            break;
                    }
                }else if(sessionLoopState.equals("PREGAME")){
                    String provisioningFlow = item.getString("provisioningFlow");

                    switch (provisioningFlow){
                        case "Invalid":
                            playerStatus = gameMode + "- Match found";
                            break;
                        case "CustomGame":
                            playerStatus = "Custom Game - Agent Select";
                            break;
                        case "Matchmaking":
                            playerStatus = gameMode + "- Agent Select";
                            break;
                    }
                }else if(sessionLoopState.equals("INGAME")){
                    // TODO: Handle range and Custom in game states
                    String score = String.valueOf(item.getInt("partyOwnerMatchScoreAllyTeam")) + "-" +String.valueOf(item.getInt("partyOwnerMatchScoreEnemyTeam"));
                    String map = util.get_respective_map_name(item.getString("matchMap"));

                    if (map.equals("Range")){
                        score = "";
                        gameMode = "";
                    }
                    playerStatus = map + " : "+ gameMode + score;
                }

                // use it somewhere
//                String partyAccessibility = item.getString("partyAccessibility");

                if (viewMap.containsKey(puuid)){
                    View shit = viewMap.get(puuid);
                    TextView status = (TextView) shit.findViewById(R.id.playerStatus);
                    ImageView playerCardImage = (ImageView) shit.findViewById(R.id.playerCard);
                    String finalPlayerStatus = playerStatus;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            status.setText(finalPlayerStatus);
                            try {
                                Picasso.with(getContext()).load(ValorantApi.GetPlayerCard(playerCard, false)).into(playerCardImage);
                            } catch (IOException | ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    return;
                }

                // If doesn't exist create a new View
                View socialFriend = inflater.inflate(R.layout.socia_friend, linearLayout, false);
                TextView playerNamelabel = socialFriend.findViewById(R.id.playerName);
                TextView playerStatuslabel = socialFriend.findViewById(R.id.playerStatus);
                ImageView playerCardImage = socialFriend.findViewById(R.id.playerCard);
                String finalPlayerStatus1 = playerStatus;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playerStatuslabel.setText(finalPlayerStatus1);
                        playerNamelabel.setText(name);
                        try {
                            Picasso.with(getContext()).load(ValorantApi.GetPlayerCard(playerCard, false)).into(playerCardImage);
                        } catch (IOException | ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                linearLayout.addView(socialFriend);
                viewMap.put(puuid, socialFriend); // Add it to the Map
            } catch (IOException | JSONException | NoSuchAlgorithmException |
                     KeyManagementException | ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        return  v;
    }



}