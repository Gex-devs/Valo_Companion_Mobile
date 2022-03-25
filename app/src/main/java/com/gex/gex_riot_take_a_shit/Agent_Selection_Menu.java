package com.gex.gex_riot_take_a_shit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.MoveAnimation;

import org.json.JSONObject;

public class Agent_Selection_Menu extends Fragment {


    TextView P_0,P_1,P_2,P_3,P_4;
    ImageView Photo_0,Photo_1,Photo_2,Photo_3,Photo_4;

    Current_status_Data viewModel;

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
        View v = inflater.inflate(R.layout.fragment_agent__selection__menu, container, false);
        P_0 = (TextView) v.findViewById(R.id.Player_0_Name);
        P_1 = (TextView) v.findViewById(R.id.Player_1_Name);
        P_2 = (TextView) v.findViewById(R.id.Player_2_Name);
        P_3 = (TextView) v.findViewById(R.id.Player_3_Name);
        P_4 = (TextView) v.findViewById(R.id.Player_4_Name);
        Photo_0 = (ImageView) v.findViewById(R.id.Player_0);
        Photo_1 = (ImageView) v.findViewById(R.id.Player_1);
        Photo_2 = (ImageView) v.findViewById(R.id.Player_2);
        Photo_3 = (ImageView) v.findViewById(R.id.Player_3);
        Photo_4 = (ImageView) v.findViewById(R.id.Player_4);

        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);
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
                                P_0.setText(Player_Json_Object.getString("name"));
                                Photo_0.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 1:
                                System.out.println("case 1 active");
                                P_1.setText(Player_Json_Object.getString("name"));
                                Photo_1.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 2:
                                System.out.println("case 2 active");
                                P_2.setText(Player_Json_Object.getString("name"));
                                Photo_2.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 3:
                                System.out.println("case 3 active");
                                P_3.setText(Player_Json_Object.getString("name"));
                                Photo_3.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 4:
                                System.out.println("case 4 active");
                                P_4.setText(Player_Json_Object.getString("name"));
                                Photo_4.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
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

         /*
        Agent Selection Menu

        {"match_info":{"roster_0":
        "{\"name\":\"besobetbet #7749\",
        \"player_id\":\"2978f23d-a61f-5c0d-a328-d870e062e2ff\",
        \"character\":\"Phoenix\",
        \"rank\":0,
        \"locked\":true,
        \"local\":true,
        \"teammate\":true}"}}
        */

        return v;
    }
    public int get_respective_image(String t){
        switch (t){
            case "Clay":
                return R.drawable.raze;
            case "Pandemic":
                return R.drawable.viper;
            case "Wraith":
                return R.drawable.omen;
            case "Hunter":
                System.out.println("sova");
                return R.drawable.sova;
            case "Thorne":
                return R.drawable.sage;
            case "Phoenix":
                return R.drawable.phoenix;
            case "Wushu":
                return R.drawable.jett;
            case "Gumshoe":
                return R.drawable.cypher;
            case "Sarge":
                return R.drawable.brimstone;
            case "Breach":
                return R.drawable.breach;
            case "Vampire":
                return R.drawable.reyna;
            case "Killjoy":
                return R.drawable.killjoy;
            case "Guide":
                return R.drawable.skye;
            case "Stealth":
                return R.drawable.yoru;
            case "Rift":
                return R.drawable.astra;
            case "Grenadier":
                return R.drawable.kayo;
            case "Deadeye":
                return R.drawable.chamber;
            case "Sprinter":
                return R.drawable.neon;

        }
        return 0;
    }
}