package com.gex.gex_riot_take_a_shit.fragments.gameFragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gex.gex_riot_take_a_shit.Current_status_Data;
import com.gex.gex_riot_take_a_shit.LocalApiHandler;
import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.Utils.AgentSelViewOrganizer;
import com.gex.gex_riot_take_a_shit.Utils.PlayerStaticInfos;
import com.gex.gex_riot_take_a_shit.Utils.util;
import com.google.android.material.imageview.ShapeableImageView;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class improved_Agent_sel_fragment extends Fragment implements View.OnClickListener {

    private ImageView Photo_1,Photo_2,Photo_3,Photo_4,Photo_5;
    private ImageView P_1_King,P_2_King,P_3_King,P_4_King,P_5_King;
    private TextView P_1,P_2,P_3,P_4,P_5,A_1,A_2,A_3,A_4,A_5,MapName,server_name,game_mode;
    private Current_status_Data viewModel;
    private ShapeableImageView astra,breach,brimstone,chamber,cypher,jett,kayo,killjoy,neon,omen,phoniex,raze,reyna,sage,skye,sova,viper,fade,Map;
    private String selected_agent;

    private String gameMode,gameMap,gameServer;

    private AgentSelViewOrganizer PlayerOne,PlayerTwo,PlayerThree,PlayerFour,PlayerFive;
    private AgentSelViewOrganizer[] otherPlayers;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.RIGHT,enter,200);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.improved_agent_select_menu, container, false);



        // Lock in button
        Button Lock_In = (Button) v.findViewById(R.id.Lock_in_button);
        Lock_In.setOnClickListener(this);

        //Agent Image buttons
        astra = (ShapeableImageView) v.findViewById(R.id.Astra_button);
        astra.setOnClickListener(this);
        breach = (ShapeableImageView) v.findViewById(R.id.Breach_button);
        breach.setOnClickListener(this);
        brimstone = (ShapeableImageView) v.findViewById(R.id.brimstone_button);
        brimstone.setOnClickListener(this);
        chamber = (ShapeableImageView) v.findViewById(R.id.chamber_button);
        chamber.setOnClickListener(this);
        cypher = (ShapeableImageView) v.findViewById(R.id.cypher_button);
        cypher.setOnClickListener(this);
        sage = (ShapeableImageView) v.findViewById(R.id.Sage_button);
        sage.setOnClickListener(this);
        jett = (ShapeableImageView) v.findViewById(R.id.jett_button);
        jett.setOnClickListener(this);
        killjoy = (ShapeableImageView) v.findViewById(R.id.Killjoy_button);
        killjoy.setOnClickListener(this);
        kayo = (ShapeableImageView) v.findViewById(R.id.kayo_button);
        kayo.setOnClickListener(this);
        skye = (ShapeableImageView) v.findViewById(R.id.Skye_button);
        skye.setOnClickListener(this);
        neon = (ShapeableImageView) v.findViewById(R.id.neon_button);
        neon.setOnClickListener(this);
        omen = (ShapeableImageView) v.findViewById(R.id.omen_button);
        omen.setOnClickListener(this);
        phoniex = (ShapeableImageView) v.findViewById(R.id.Phoenix_button);
        phoniex.setOnClickListener(this);
        raze = (ShapeableImageView) v.findViewById(R.id.Raze_button);
        raze.setOnClickListener(this);
        sova = (ShapeableImageView) v.findViewById(R.id.Sova_button);
        sova.setOnClickListener(this);
        reyna = (ShapeableImageView) v.findViewById(R.id.Reyna_button);
        reyna.setOnClickListener(this);
        viper = (ShapeableImageView) v.findViewById(R.id.Viper_button);
        viper.setOnClickListener(this);
        fade = (ShapeableImageView) v.findViewById(R.id.Fade_button);
        fade.setOnClickListener(this);

        //player photo and agent name
        P_1 = (TextView) v.findViewById(R.id.Player_1_Name_Improved);
        P_2 = (TextView) v.findViewById(R.id.Player_2_Name_improved);
        P_3 = (TextView) v.findViewById(R.id.Player_3_Name_Improved);
        P_4 = (TextView) v.findViewById(R.id.Player_4_Name_Improved);
        P_5 = (TextView) v.findViewById(R.id.Player_5_Name_Improved);
        A_1 = (TextView) v.findViewById(R.id.Player_1_Agent_Improved);
        A_2 = (TextView) v.findViewById(R.id.Player_2_Agent_Improved);
        A_3 = (TextView) v.findViewById(R.id.Player_3_Agent_Improved);
        A_4 = (TextView) v.findViewById(R.id.Player_4_Agent_Improved);
        A_5 = (TextView) v.findViewById(R.id.Player_5_Agent_Improved);

        Photo_1 = (ImageView) v.findViewById(R.id.Player_1_improved);
        Photo_2 = (ImageView) v.findViewById(R.id.player_2_improved);
        Photo_3 = (ImageView) v.findViewById(R.id.player_3_improved2);
        Photo_4 = (ImageView) v.findViewById(R.id.player_4_improved3);
        Photo_5 = (ImageView) v.findViewById(R.id.player_5_improved4);


        //Map
        Map = (ShapeableImageView) v.findViewById(R.id.imageView19);
        MapName = (TextView) v.findViewById(R.id.textView7);
        // Server,game mode and
        server_name = (TextView) v.findViewById(R.id.server_name);
        game_mode = (TextView) v.findViewById(R.id.gamemode);



        // View model
        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);

        PlayerOne = new AgentSelViewOrganizer(Photo_1,P_1,A_1);
        PlayerTwo = new AgentSelViewOrganizer(Photo_2,P_2,A_2);
        PlayerThree = new AgentSelViewOrganizer(Photo_3,P_3,A_3);
        PlayerFour= new AgentSelViewOrganizer(Photo_4,P_4,A_4);
        PlayerFive = new AgentSelViewOrganizer(Photo_5,P_5,A_5);

        otherPlayers = new AgentSelViewOrganizer[]{PlayerTwo, PlayerThree, PlayerFour, PlayerFive};

        viewModel.getSelectedItem().observe(requireActivity(),item ->{
//            UpdateUi(item);
            new JsonParseTask().execute(item);
        });

        try {
            String map_now = LocalApiHandler.get_map_name();
            MapName.setText(util.get_respective_map_name(map_now));
            server_name.setText(LocalApiHandler.get_server());
            game_mode.setText(LocalApiHandler.get_gamemode());
            Map.setImageResource(util.get_respective_map_image(map_now));
//            new JsonParseTask().execute(LocalApiHandler.getPreGame());
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return v;
    }
    @SuppressLint("StaticFieldLeak")
    private class JsonParseTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String item = strings[0];
            try {
                Log.d("AgentSelect", "Json: "+item);
                JSONObject jsonObject = new JSONObject(item);
                JSONObject allyTeam = jsonObject.getJSONObject("AllyTeam");

                String teamId = allyTeam.getString("TeamID");
                Log.d("AgentSelect", "Team ID: " + teamId);

                JSONArray players = allyTeam.getJSONArray("Players");
                Log.d("ddme", "Value of array: "+String.valueOf(players.length()));
                int foragentSel = 0;
                for (int i = 0; i < players.length(); i++) {
                    Log.d("ddme", String.valueOf(i));
                    try {
                        JSONObject player = players.getJSONObject(i);
                        String subject = player.getString("Subject");
                        String characterId = player.getString("CharacterID");
                        String characterSelectionState = player.getString("CharacterSelectionState");
                        int competitiveTier = player.getInt("CompetitiveTier");
                        boolean isCaptain = player.getBoolean("IsCaptain");

                        JSONObject playerIdentity = player.getJSONObject("PlayerIdentity");
                        String playerCardId = playerIdentity.getString("PlayerCardID");
                        String playerTitleId = playerIdentity.getString("PlayerTitleID");

                        Log.d("AgentSelect", "Player " + (i + 1));
                        Log.d("AgentSelect", "Subject: " + subject);
                        Log.d("AgentSelect", "Character ID: " + characterId);
                        Log.d("AgentSelect", "Character Selection State: " + characterSelectionState);
                        Log.d("AgentSelect", "Competitive Tier: " + competitiveTier);
                        Log.d("AgentSelect", "Is Captain: " + isCaptain);
                        Log.d("AgentSelect", "Player Card ID: " + playerCardId);
                        Log.d("AgentSelect", "Player Title ID: " + playerTitleId);

                        AgentSelViewOrganizer playerViewOrganizer = null;

                        if (subject.equals(PlayerStaticInfos.getMyID())){
                            // Apply PlayerOne to the owner only
                            playerViewOrganizer = PlayerOne;
                            Log.d("AgentSelect", "isOwner: true");
                        }else if (foragentSel >= 0 && foragentSel < otherPlayers.length) {
                            // Apply the other players to PlayerTwo, PlayerThree, PlayerFour, or PlayerFive
                            playerViewOrganizer = otherPlayers[foragentSel];
                            foragentSel++; // Increment the index for the next iteration
                        } else {
                            Log.d("AgentSelect", "Unknown Amount of agents");
                        }



                        if(playerViewOrganizer != null)
                            updateUiOnUiThread(playerViewOrganizer,subject,characterId);
                    }catch (Exception ex){
                        Log.e("AgentSel", ex.toString());
                    }

                }
            }catch (Exception ex){
                Log.d("ShowMeTheWay", ex.toString());
            }
            return null;
        }
    }

    private void updateUiOnUiThread(AgentSelViewOrganizer agentSelViewOrganizer,String... item) throws IOException, ExecutionException, InterruptedException {
        agentSelViewOrganizer.SetName(item[0]);
        agentSelViewOrganizer.SetAgent(item[1]);
    }

    private void add_stroke(int ID){
        ShapeableImageView Temp = (ShapeableImageView) getView().findViewById(ID);
        Temp.setStrokeColorResource(R.color.Agent_selected_color);
    }
    private void remove_stroke_from_all(){
        getActivity().runOnUiThread(() -> {
            astra.setStrokeColorResource(android.R.color.transparent);
            breach.setStrokeColorResource(android.R.color.transparent);
            brimstone.setStrokeColorResource(android.R.color.transparent);
            chamber.setStrokeColorResource(android.R.color.transparent);
            cypher.setStrokeColorResource(android.R.color.transparent);
            sage.setStrokeColorResource(android.R.color.transparent);
            jett.setStrokeColorResource(android.R.color.transparent);
            killjoy.setStrokeColorResource(android.R.color.transparent);
            kayo.setStrokeColorResource(android.R.color.transparent);
            skye.setStrokeColorResource(android.R.color.transparent);
            neon.setStrokeColorResource(android.R.color.transparent);
            omen.setStrokeColorResource(android.R.color.transparent);
            phoniex.setStrokeColorResource(android.R.color.transparent);
            raze.setStrokeColorResource(android.R.color.transparent);
            sova.setStrokeColorResource(android.R.color.transparent);
            reyna.setStrokeColorResource(android.R.color.transparent);
            viper.setStrokeColorResource(android.R.color.transparent);
            fade.setStrokeColorResource(android.R.color.transparent);
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        String agent = null;
        switch (view.getId()){
            case R.id.Astra_button:
                agent = "41fb69c1-4189-7b37-f117-bcaf1e96f1bf";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("astra was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Breach_button:
                agent = "5f8d3a7f-467b-97f3-062c-13acf203c006";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.brimstone_button:
                agent = "9f0d8ba9-4140-b941-57d3-a7ad57c6b417";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.chamber_button:
                agent = "22697a3d-45bf-8dd7-4fec-84a9e28c69d7";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.cypher_button:
                agent = "117ed9e3-49f3-6512-3ccf-0cada7e3823b";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Fade_button:
                agent = "dade69b4-4f5a-8528-247b-219e5a1facd6";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Killjoy_button:
                agent = "1e58de9c-4950-5125-93e9-a0aee9f98746";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.neon_button:
                agent = "bb2a4828-46eb-8cd1-e765-15848195d751";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.jett_button:
                agent = "add6443a-41bd-e414-f6ad-e58d267f4e95";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Phoenix_button:
                agent = "eb93336a-449b-9c1b-0a54-a891f7921d69";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Raze_button:
                agent = "f94c3b30-42be-e959-889c-5aa313dba261";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.omen_button:
                agent = "8e253930-4c05-31dd-1b6c-968525494517";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Reyna_button:
                agent = "a3bfb853-43b2-7238-a4f1-ad90e9e46bcc";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Sage_button:
                agent = "569fdd95-4d10-43ab-ca70-79becc718b46";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.kayo_button:
                agent = "601dbbe7-43ce-be57-2a40-4abd24953621";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Skye_button:
                agent = "6f2a04ca-43e0-be17-7f36-b3908627744d";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Sova_button:
                agent = "320b2a48-4d9b-a075-30f1-1f93a9b638fa";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Viper_button:
                agent = "707eab51-4836-f488-046a-cda6bf494859";
                try {
                    if (LocalApiHandler.SelectAgent(agent)){
                        selected_agent = agent;
                        System.out.println("Breach was clicked");
                        remove_stroke_from_all();
                        add_stroke(view.getId());
                    }else
                        AgentAlreadySelectedError();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.Lock_in_button:
                System.out.println("Lock In button was clicked");
                try {
                    if (LocalApiHandler.LockAgent(selected_agent).equals("409")){
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("AGENT")
                                .setContentText("LOCKED AGENT")
                                .show();
                    }

                } catch (IOException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
        }

    }
    private void AgentAlreadySelectedError(){
        Toast.makeText(getContext(),"Agent Locked",Toast.LENGTH_SHORT).show();
    }
    private void SetViews(){
        @SuppressLint("CutPasteId") ShapeableImageView[] agentButtons = new ShapeableImageView[] {
                (ShapeableImageView) getView().findViewById(R.id.Astra_button),
                (ShapeableImageView) getView().findViewById(R.id.Breach_button),
                (ShapeableImageView) getView().findViewById(R.id.brimstone_button),
                (ShapeableImageView) getView().findViewById(R.id.chamber_button),
                (ShapeableImageView) getView().findViewById(R.id.cypher_button),
                (ShapeableImageView) getView().findViewById(R.id.Sage_button),
                (ShapeableImageView) getView().findViewById(R.id.jett_button),
                (ShapeableImageView) getView().findViewById(R.id.Killjoy_button),
                (ShapeableImageView) getView().findViewById(R.id.kayo_button),
                (ShapeableImageView) getView().findViewById(R.id.Skye_button),
                (ShapeableImageView) getView().findViewById(R.id.neon_button),
                (ShapeableImageView) getView().findViewById(R.id.omen_button),
                (ShapeableImageView) getView().findViewById(R.id.Phoenix_button),
                (ShapeableImageView) getView().findViewById(R.id.Raze_button),
                (ShapeableImageView) getView().findViewById(R.id.Sova_button),
                (ShapeableImageView) getView().findViewById(R.id.Reyna_button),
                (ShapeableImageView) getView().findViewById(R.id.Viper_button),
                (ShapeableImageView) getView().findViewById(R.id.Fade_button)
        };

        for (ShapeableImageView button : agentButtons) {
            button.setOnClickListener(this);
        }

        TextView[] playerNames = new TextView[] {
                (TextView) getView().findViewById(R.id.Player_1_Name_Improved),
                (TextView) getView().findViewById(R.id.Player_2_Name_improved),
                (TextView) getView().findViewById(R.id.Player_3_Name_Improved),

        };

        TextView[] agentNames = new TextView[] {
                (TextView) getView().findViewById(R.id.Player_1_Agent_Improved),
                (TextView) getView().findViewById(R.id.Player_2_Agent_Improved),
                (TextView) getView().findViewById(R.id.Player_3_Agent_Improved),

        };

        ImageView[] playerPhotos = new ImageView[] {
                (ImageView) getView().findViewById(R.id.Player_1_improved),
                (ImageView) getView().findViewById(R.id.player_2_improved),
                (ImageView) getView().findViewById(R.id.player_3_improved2),
        };
    }

}


