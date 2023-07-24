package com.gex.gex_riot_take_a_shit.Utils;

import android.widget.ImageView;
import android.widget.TextView;

import com.gex.gex_riot_take_a_shit.LocalApiHandler;
import com.gex.gex_riot_take_a_shit.ThirdParty.ValorantApi;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AgentSelViewOrganizer {

    private ImageView _PlayerAgentCard;
    private  TextView _PlayerName;
    private  TextView _AgentName;

    public AgentSelViewOrganizer(ImageView PlayerAgentCard, TextView PlayerName,TextView AgentName){
        _PlayerAgentCard = PlayerAgentCard;
        _PlayerName = PlayerName;
        _AgentName = AgentName;
    }
    public void SetName(String Name) throws IOException, ExecutionException, InterruptedException {
        _PlayerName.setText(LocalApiHandler.getUsername(Name));
    }

    public void SetAgent(String characterID) throws IOException, ExecutionException, InterruptedException {
        String Name = ValorantApi.getCharacterNameByID(characterID);
        if (Name.equals("null")){
            _AgentName.setText("Selecting....");
        }else {
            _AgentName.setText(Name);
        }

        SetPlayerCard(Name);
    }


    private void SetPlayerCard(String characterName){
        _PlayerAgentCard.setImageResource(util.get_respective_image(characterName));
    }
}
