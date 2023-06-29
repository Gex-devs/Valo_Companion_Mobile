package com.gex.gex_riot_take_a_shit.fragments.gameFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gex.gex_riot_take_a_shit.Current_status_Data;
import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.LocalApiHandler;
import com.gex.gex_riot_take_a_shit.Utils.util;
import com.google.android.material.imageview.ShapeableImageView;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class improved_Agent_sel_fragment extends Fragment implements View.OnClickListener {

    private ImageView Photo_1,Photo_2,Photo_3,Photo_4,Photo_5;
    private TextView P_1,P_2,P_3,P_4,P_5,A_1,A_2,A_3,A_4,A_5,MapName,server_name,game_mode;
    private Current_status_Data viewModel;
    private ShapeableImageView astra,breach,brimstone,chamber,cypher,jett,kayo,killjoy,neon,omen,phoniex,raze,reyna,sage,skye,sova,viper,fade,Map;
    private String selected_agent;

    private String gameMode,gameMap,gameServer;

    public  improved_Agent_sel_fragment(){

    }
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
        game_mode = (TextView) v.findViewById(R.id.textView5);


        // View model
        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);

        // Agent select listener view model
        viewModel.getSelectedItem().observe(requireActivity(),item ->{
            try{
                // org.json.JSONException: Unterminated array at character 9 of [object Object]
                for(int i = 0;i < 5;i++){
                    try{
                        JSONObject jsob = new JSONObject(item);
                        String Key_Element = "roster_"+i;
                        String Match_info_object = jsob.getJSONObject("match_info").getString(Key_Element);
                        JSONObject Player_Json_Object = new JSONObject(Match_info_object);
                        switch (i) {
                            case 0:
                                System.out.println("case 0 active");
                                P_1.setText(Player_Json_Object.getString("name").split("#")[0]);
                                Photo_1.setImageResource(util.get_respective_image(Player_Json_Object.getString("character")));
                                A_1.setText(util.get_respective_name(Player_Json_Object.getString("character")));
                                break;
                            case 1:
                                System.out.println("case 1 active");
                                Photo_2.setImageResource(util.get_respective_image(Player_Json_Object.getString("character")));
                                P_2.setText(Player_Json_Object.getString("name").split("#")[0]);
                                A_2.setText(util.get_respective_name(Player_Json_Object.getString("character")));
                                break;
                            case 2:
                                System.out.println("case 2 active");
                                P_3.setText(Player_Json_Object.getString("name").split("#")[0]);
                                Photo_3.setImageResource(util.get_respective_image(Player_Json_Object.getString("character")));
                                A_3.setText(util.get_respective_name(Player_Json_Object.getString("character")));
                                break;
                            case 3:
                                System.out.println("case 3 active");
                                P_4.setText(Player_Json_Object.getString("name").split("#")[0]);
                                Photo_4.setImageResource(util.get_respective_image(Player_Json_Object.getString("character")));
                                A_4.setText(util.get_respective_name(Player_Json_Object.getString("character")));
                                break;
                            case 4:
                                System.out.println("case 4 active");
                                P_5.setText(Player_Json_Object.getString("name").split("#")[0]);
                                Photo_5.setImageResource(util.get_respective_image(Player_Json_Object.getString("character")));
                                A_5.setText(util.get_respective_name(Player_Json_Object.getString("character")));
                                break;
                        }
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            }catch (Exception e){
                System.out.println(e);
            }
        });


        try {
            String map_now = LocalApiHandler.get_map_name();
            MapName.setText(util.get_respective_map_name(map_now));
            server_name.setText(LocalApiHandler.get_server());
            game_mode.setText(LocalApiHandler.get_gamemode());
            Map.setImageResource(util.get_respective_map_image(map_now));
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        // Map select view model listener
        viewModel.get_map().observe(requireActivity(),item ->{
            switch (item){
                case "Haven":
                    Map.setImageResource(R.drawable.heaven);
                    MapName.setText(item);
                    break;
                case "Bind":
                    Map.setImageResource(R.drawable.bind);
                    MapName.setText("BIND");
                    break;
                case "Split":
                    Map.setImageResource(R.drawable.split);
                    MapName.setText("SPLIT");
                    break;
                case "Ascent":
                    Map.setImageResource(R.drawable.ascent);
                    MapName.setText("ASCENT");
                    break;
                case "Icebox":
                    Map.setImageResource(R.drawable.heaven);
                    MapName.setText("ICEBOX");
                    break;
                case "Breeze":
                    Map.setImageResource(R.drawable.ascent);
                    MapName.setText("BREEZE");
                    break;
                case "Fracture":
                    Map.setImageResource(R.drawable.fracture);
                    MapName.setText("FRACTURE");
                    break;
                case "Pearl":
                    Map.setImageResource(R.drawable.pearl);
                    MapName.setText("PEARL");
                    break;
            }
        });
        return v;
    }

    private void add_stroke(int ID){
        ShapeableImageView Temp = (ShapeableImageView) getView().findViewById(ID);
        Temp.setStrokeColorResource(R.color.Agent_selected_color);
    }
    private void remove_stroke_from_all(){
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
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        String agent = null;
        switch (view.getId()){
            case R.id.Astra_button:
                agent = "41fb69c1-4189-7b37-f117-bcaf1e96f1bf";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("astra was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.Breach_button:
                agent = "5f8d3a7f-467b-97f3-062c-13acf203c006";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("Breach was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                viewModel.for_char("Breach");
                break;
            case R.id.brimstone_button:
                agent = "9f0d8ba9-4140-b941-57d3-a7ad57c6b417";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;

                System.out.println("Brimstone was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                viewModel.for_char("Brimstone");
                break;
            case R.id.chamber_button:
                agent = "22697a3d-45bf-8dd7-4fec-84a9e28c69d7";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("Chamber was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                viewModel.for_char("Chamber");
                break;
            case R.id.cypher_button:
                agent = "117ed9e3-49f3-6512-3ccf-0cada7e3823b";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("Cypher was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                viewModel.for_char("Cypher");
                break;
            case R.id.Fade_button:
                agent = "dade69b4-4f5a-8528-247b-219e5a1facd6";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("Fade was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                viewModel.for_char("Fade");
                break;
            case R.id.Killjoy_button:
                agent = "1e58de9c-4950-5125-93e9-a0aee9f98746";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("Kj was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.neon_button:
                agent = "bb2a4828-46eb-8cd1-e765-15848195d751";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("neon was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.jett_button:
                agent = "add6443a-41bd-e414-f6ad-e58d267f4e95";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("jett was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.Phoenix_button:
                agent = "eb93336a-449b-9c1b-0a54-a891f7921d69";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("phx was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.Raze_button:
                agent = "f94c3b30-42be-e959-889c-5aa313dba261";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("Raze was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.omen_button:
                agent = "8e253930-4c05-31dd-1b6c-968525494517";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("omen was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.Reyna_button:
                agent = "a3bfb853-43b2-7238-a4f1-ad90e9e46bcc";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("Reyna was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.Sage_button:
                agent = "569fdd95-4d10-43ab-ca70-79becc718b46";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("sage was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.kayo_button:
                agent = "601dbbe7-43ce-be57-2a40-4abd24953621";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("kayo was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.Skye_button:
                agent = "6f2a04ca-43e0-be17-7f36-b3908627744d";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("Skye was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.Sova_button:
                agent = "320b2a48-4d9b-a075-30f1-1f93a9b638fa";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("Sova was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
                break;
            case R.id.Viper_button:
                agent = "707eab51-4836-f488-046a-cda6bf494859";
                try {
                    LocalApiHandler.SelectAgent(agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selected_agent = agent;
                System.out.println("Viper was clicked");
                remove_stroke_from_all();
                add_stroke(view.getId());
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

}


