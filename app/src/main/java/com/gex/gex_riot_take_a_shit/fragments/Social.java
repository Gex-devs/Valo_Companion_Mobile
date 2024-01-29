package com.gex.gex_riot_take_a_shit.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import java.util.zip.Inflater;

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
                String name = XMPPServer.friendsList.get(puuid) != null? XMPPServer.friendsList.get(puuid) : "Not Friend";
                String sessionLoopState = item.getString("sessionLoopState");
                String gameMode = GameModes.getByCodeName(item.getString("queueId")).getDisplayName();
                String playerStatus = "can't be null";
                String playerCard = item.getString("playerCardId");
                String partyID = item.getString("partyId");

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

//                IsPartyStillAlive(linearLayout);
                if (viewMap.containsKey(partyID))
                {
                    boolean playerInParty = false;
                    View shit = viewMap.get(partyID);
                    LinearLayout playerHolder = (LinearLayout) shit.findViewById(R.id.partyMembers);
                    for (int i = 0; i < playerHolder.getChildCount(); i++) {
                        View childView = playerHolder.getChildAt(i);
                        if (childView.getTag().equals(puuid)){
                            playerInParty = true;
                            TextView status = (TextView) childView.findViewById(R.id.playerStatus);
                            ImageView playerCardImage = (ImageView) childView.findViewById(R.id.playerCard);
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
                        }
                    }


                    if (!playerInParty){
                        LinearLayout partyHolder = (LinearLayout) shit.findViewById(R.id.partyMembers);
                        AddPlayerToParty(partyHolder, name, playerStatus, inflater, playerCard, puuid);
                    }

                    return;
                }

                // If Party doesn't exist
                View partyHolder = inflater.inflate(R.layout.party_holder, linearLayout, false);
                LinearLayout partyLayoutHolder = partyHolder.findViewById(R.id.partyMembers);
                linearLayout.addView(partyHolder);
                AddPlayerToParty(partyLayoutHolder, name, playerStatus, inflater, playerCard, puuid);
                viewMap.put(partyID, partyHolder);

            } catch ( JSONException e) {
                Log.e("XMPP", "onCreateView: " + e);
            }
        });

        return  v;
    }

    private void AddPlayerToParty(LinearLayout party, String name, String status, LayoutInflater inflater, String playerCard, String puuid){
        View socialFriend = inflater.inflate(R.layout.socia_friend, party, false);
        socialFriend.setTag(puuid); // Set tag
        TextView playerNamelabel = socialFriend.findViewById(R.id.playerName);
        TextView playerStatuslabel = socialFriend.findViewById(R.id.playerStatus);
        ImageView playerCardImage = socialFriend.findViewById(R.id.playerCard);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playerStatuslabel.setText(status);
                playerNamelabel.setText(name);
                try {
                    Picasso.with(getContext()).load(ValorantApi.GetPlayerCard(playerCard, false)).into(playerCardImage);
                } catch (IOException | ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        party.addView(socialFriend);
    }

    private void IsPartyStillAlive(LinearLayout partyHolderHolder)
    {
        for (Map.Entry<String , View> entry : viewMap.entrySet()) {
            try {
                String partyId = entry.getKey();
                String responseBody = OfficalValorantApi.getInstance().GetPartyById(partyId);
                JSONObject jsonObject = new JSONObject(responseBody);
                if (jsonObject.getInt("httpStatus") == 404){
                    // Remove party
                    View party_holder = entry.getValue();
                    partyHolderHolder.removeView(party_holder);
                }
            } catch (JSONException e) {
                Log.e("Social", "IsPartyStillAlive: " + e );
            } catch (IOException | NoSuchAlgorithmException | ExecutionException |
                     InterruptedException | KeyManagementException e) {
                Log.e("OfficialApi", "IsPartyStillAlive: " + e );
            }
        }

    }


}