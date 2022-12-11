package com.gex.gex_riot_take_a_shit;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.imageview.ShapeableImageView;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class improved_Agent_sel_fragment extends Fragment implements View.OnClickListener {

    ImageView Photo_1,Photo_2,Photo_3,Photo_4,Photo_5;
    TextView P_1,P_2,P_3,P_4,P_5,A_1,A_2,A_3,A_4,A_5,MapName,server_name,game_mode;
    Current_status_Data viewModel;
    ShapeableImageView astra,breach,brimstone,chamber,cypher,jett,kayo,killjoy,neon,omen,phoniex,raze,reyna,sage,skye,sova,viper,fade,Map;
    String selected_agent;

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
                        switch (i){
                            case 0:
                                System.out.println("case 0 active");
                                P_1.setText(Player_Json_Object.getString("name").split("#")[0]);
                                Photo_1.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                A_1.setText(get_respective_name(Player_Json_Object.getString("character")));
                                break;
                            case 1:
                                System.out.println("case 1 active");
                                Photo_2.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P_2.setText(Player_Json_Object.getString("name").split("#")[0]);
                                A_2.setText(get_respective_name(Player_Json_Object.getString("character")));
                                break;
                            case 2:
                                System.out.println("case 2 active");
                                P_3.setText(Player_Json_Object.getString("name").split("#")[0]);
                                Photo_3.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                A_3.setText(get_respective_name(Player_Json_Object.getString("character")));
                                break;
                            case 3:
                                System.out.println("case 3 active");
                                P_4.setText(Player_Json_Object.getString("name").split("#")[0]);
                                Photo_4.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                A_4.setText(get_respective_name(Player_Json_Object.getString("character")));
                                break;
                            case 4:
                                System.out.println("case 4 active");
                                P_5.setText(Player_Json_Object.getString("name").split("#")[0]);
                                Photo_5.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                A_5.setText(get_respective_name(Player_Json_Object.getString("character")));
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
            String map_now = pythonRestApi.get_map_name();
            MapName.setText(get_respective_map_name(map_now));
            server_name.setText(pythonRestApi.get_server());
            game_mode.setText(pythonRestApi.get_gamemode());
            Map.setImageResource(get_respective_map_image(map_now));
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

    // Gets image based on characters developer name
    public int get_respective_image(String t){
        switch (t){
            case "Clay":
                return R.drawable.raze_background;
            case "Pandemic":
                return R.drawable.viper_background;
            case "Wraith":
                return R.drawable.omen_background;
            case "Hunter":
                return R.drawable.sova_background;
            case "Thorne":
                return R.drawable.sage_background;
            case "Phoenix":
                return R.drawable.phx_background;
            case "Wushu":
                return R.drawable.jett_backgroun;
            case "Gumshoe":
                return R.drawable.cypher_background;
            case "Sarge":
                return R.drawable.brimstone_background;
            case "Breach":
                return R.drawable.breach_background;
            case "Vampire":
                return R.drawable.reyna_background;
            case "Killjoy":
                return R.drawable.killjoy_background;
            case "Guide":
                return R.drawable.skye_background;
            case "Stealth":
                return R.drawable.yoru_background;
            case "Rift":
                return R.drawable.astra_background;
            case "Grenadier":
                return R.drawable.kayo_background;
            case "Deadeye":
                return R.drawable.chamber_background;
            case "Sprinter":
                return R.drawable.neon_background;
            case "BountyHunter":
                return R.drawable.fade_background;
        }
        return R.drawable.valo_place_holder;
    }

    // Gets characters Name based on developers Name
    public String get_respective_name(String char_name){
        switch (char_name){
            case "Clay":
                return "Raze";
            case "Pandemic":
                return "Viper";
            case "Wraith":
                return "Omen";
            case "Hunter":
                return "Sova";
            case "Thorne":
                return "Sage";
            case "Phoenix":
                return "Phoenix";
            case "Wushu":
                return "Jett";
            case "Gumshoe":
                return "Cypher";
            case "Sarge":
                return "Brimstone";
            case "Breach":
                return "Breach";
            case "Vampire":
                return "Reyna";
            case "Killjoy":
                return "Kill Joy";
            case "Guide":
                return "Skye";
            case "Stealth":
                return "Yoru";
            case "Rift":
                return "Astra";
            case "Grenadier":
                return "Kayo";
            case "Deadeye":
                return "Chamber";
            case "Sprinter":
                return "Neon";
            case "BountyHunter":
                return "Fade";
        }
        return "Agent";
    }
    public String get_respective_map_name(String map){
        switch (map){
            case "/Game/Maps/Triad/Triad":
                return "Haven";
            case "/Game/Maps/Duality/Duality":
                return ("Bind");
            case "/Game/Maps/Bonsai/Bonsai":
                return ("Split");
            case "/Game/Maps/Ascent/Ascent":
                return("Ascent");
            case "/Game/Maps/Port/Port":
                return("Icebox");
            case "/Game/Maps/Foxtrot/Foxtrot":
                return("Breeze");
            case "/Game/Maps/Canyon/Canyon":
                return("Fracture");
            case "/Game/Maps/Pitt/Pitt":
                return("Pearl");
        }
        return null;
    }
    public int get_respective_map_image(String map){
        switch (map){
            case "/Game/Maps/Triad/Triad":
                return R.drawable.heaven;
            case "/Game/Maps/Duality/Duality":
                return R.drawable.bind;
            case "/Game/Maps/Bonsai/Bonsai":
                return R.drawable.split;
            case "/Game/Maps/Ascent/Ascent":
                return R.drawable.ascent;
            case "/Game/Maps/Port/Port":
                return R.drawable.icebox_1;
            case "/Game/Maps/Foxtrot/Foxtrot":
                return R.drawable.breeze;
            case "/Game/Maps/Canyon/Canyon":
                return R.drawable.fracture;
            case "/Game/Maps/Pitt/Pitt":
                return R.drawable.pearl;
        }
        return 0;
    }
    public void add_stroke(int ID){
        ShapeableImageView Temp = (ShapeableImageView) getView().findViewById(ID);
        Temp.setStrokeColorResource(R.color.Agent_selected_color);
    }
    public void remove_stroke_from_all(){
        astra = (ShapeableImageView) getView().findViewById(R.id.Astra_button);
        astra.setStrokeColorResource(android.R.color.transparent);
        breach = (ShapeableImageView) getView().findViewById(R.id.Breach_button);
        breach.setStrokeColorResource(android.R.color.transparent);
        brimstone = (ShapeableImageView) getView().findViewById(R.id.brimstone_button);
        brimstone.setStrokeColorResource(android.R.color.transparent);
        chamber = (ShapeableImageView) getView().findViewById(R.id.chamber_button);
        chamber.setStrokeColorResource(android.R.color.transparent);
        cypher = (ShapeableImageView) getView().findViewById(R.id.cypher_button);
        cypher.setStrokeColorResource(android.R.color.transparent);
        sage = (ShapeableImageView) getView().findViewById(R.id.Sage_button);
        sage.setStrokeColorResource(android.R.color.transparent);
        jett = (ShapeableImageView) getView().findViewById(R.id.jett_button);
        jett.setStrokeColorResource(android.R.color.transparent);
        killjoy = (ShapeableImageView) getView().findViewById(R.id.Killjoy_button);
        killjoy.setStrokeColorResource(android.R.color.transparent);
        kayo = (ShapeableImageView) getView().findViewById(R.id.kayo_button);
        kayo.setStrokeColorResource(android.R.color.transparent);
        skye = (ShapeableImageView) getView().findViewById(R.id.Skye_button);
        skye.setStrokeColorResource(android.R.color.transparent);
        neon = (ShapeableImageView) getView().findViewById(R.id.neon_button);
        neon.setStrokeColorResource(android.R.color.transparent);
        omen = (ShapeableImageView) getView().findViewById(R.id.omen_button);
        omen.setStrokeColorResource(android.R.color.transparent);
        phoniex = (ShapeableImageView) getView().findViewById(R.id.Phoenix_button);
        phoniex.setStrokeColorResource(android.R.color.transparent);
        raze = (ShapeableImageView) getView().findViewById(R.id.Raze_button);
        raze.setStrokeColorResource(android.R.color.transparent);
        sova = (ShapeableImageView) getView().findViewById(R.id.Sova_button);
        sova.setStrokeColorResource(android.R.color.transparent);
        reyna.setStrokeColorResource(android.R.color.transparent);
        viper = (ShapeableImageView) getView().findViewById(R.id.Viper_button);
        viper.setStrokeColorResource(android.R.color.transparent);
        fade = (ShapeableImageView) getView().findViewById(R.id.Fade_button);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.SelectAgent(agent);
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
                    pythonRestApi.LockAgent(selected_agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}


