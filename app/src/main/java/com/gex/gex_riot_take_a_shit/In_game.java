package com.gex.gex_riot_take_a_shit;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;


public class In_game extends Fragment {
    TextView P0_NAME,P1_NAME,P2_NAME,P3_NAME,P4_NAME,P5_NAME,P6_NAME,P7_NAME,P8_NAME,P9_NAME;
    TextView P_0_K_D,P_1_K_D,P_2_K_D,P_3_K_D,P_4_K_D,P_5_K_D,P_6_K_D,P_7_K_D,P_8_K_D,P_9_K_D;
    ImageView P0_imageholder,P1_imageholder,P2_imageholder,P3_imageholder,P4_imageholder,P5_imageholder,P6_imageholder,P7_imageholder,P8_imageholder,P9_imageholder;
    Current_status_Data viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_in_game, container, false);


        //Image holders
        P0_imageholder = (ImageView) v.findViewById(R.id.player_0_image_holder);
        P1_imageholder = (ImageView) v.findViewById(R.id.player_1_image_holder7);
        P2_imageholder = (ImageView) v.findViewById(R.id.player_2_image_holder2);
        P3_imageholder = (ImageView) v.findViewById(R.id.player_3_image_holder6);
        P4_imageholder = (ImageView) v.findViewById(R.id.player_4_image_holder4);
        P5_imageholder = (ImageView) v.findViewById(R.id.player_5_image_holder9);
        P6_imageholder = (ImageView) v.findViewById(R.id.player_6_image_holder3);
        P7_imageholder = (ImageView) v.findViewById(R.id.player_7_image_holder8);
        P8_imageholder = (ImageView) v.findViewById(R.id.player_8_image_holder5);
        P9_imageholder = (ImageView) v.findViewById(R.id.player_9_image_holder10);

        //player Name
        P0_NAME = (TextView) v.findViewById(R.id.player_0_Name_scoreboard);
        P1_NAME = (TextView) v.findViewById(R.id.player_1_Name_scoreboard6);
        P2_NAME = (TextView) v.findViewById(R.id.player_2_Name_scoreboard2);
        P3_NAME = (TextView) v.findViewById(R.id.player_3_Name_scoreboard7);
        P4_NAME = (TextView) v.findViewById(R.id.player_4_Name_scoreboard4);
        P5_NAME = (TextView) v.findViewById(R.id.player_5_Name_scoreboard8);
        P6_NAME = (TextView) v.findViewById(R.id.player_6_Name_scoreboard5);
        P7_NAME = (TextView) v.findViewById(R.id.player_7_Name_scoreboard9);
        P8_NAME = (TextView) v.findViewById(R.id.player_8_Name_scoreboard3);
        P9_NAME = (TextView) v.findViewById(R.id.player_9_Name_scoreboard10);

        // Player K/D
        P_0_K_D = (TextView) v.findViewById(R.id.Player_0_K_D);
        P_1_K_D = (TextView) v.findViewById(R.id.player_1_K_D7);
        P_2_K_D = (TextView) v.findViewById(R.id.player_2_K_D2);
        P_3_K_D = (TextView) v.findViewById(R.id.player_3_K_D9);
        P_4_K_D = (TextView) v.findViewById(R.id.player_4_K_D3);
        P_5_K_D = (TextView) v.findViewById(R.id.player_5_K_D8);
        P_6_K_D = (TextView) v.findViewById(R.id.player_6_K_D5);
        P_7_K_D = (TextView) v.findViewById(R.id.player_7_K_D10);
        P_8_K_D = (TextView) v.findViewById(R.id.player_8_K_D6);
        P_9_K_D = (TextView) v.findViewById(R.id.player_9_K_D11);

        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);
        viewModel.getSelectedItem().observe(requireActivity(),item ->{
            try{
                // org.json.JSONException: Unterminated array at character 9 of [object Object]
                for(int i = 0;i < 10;i++){
                    try{
                        JSONObject jsob = new JSONObject(item);
                        String Key_Element = "scoreboard_"+i;
                        String Match_info_object = jsob.getJSONObject("match_info").getString(Key_Element);
                        JSONObject Player_Json_Object = new JSONObject(Match_info_object);
                        switch (i){
                            case 0:
                                P0_NAME.setText(Player_Json_Object.getString("name"));
                                P_0_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P0_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 1:
                                P1_NAME.setText(Player_Json_Object.getString("name"));
                                P_1_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P1_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 2:
                                P2_NAME.setText(Player_Json_Object.getString("name"));
                                P_2_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P2_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 3:
                                P3_NAME.setText(Player_Json_Object.getString("name"));
                                P_3_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P3_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 4:
                                P4_NAME.setText(Player_Json_Object.getString("name"));
                                P_4_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P4_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 5:
                                P5_NAME.setText(Player_Json_Object.getString("name"));
                                P_5_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P5_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 6:
                                P6_NAME.setText(Player_Json_Object.getString("name"));
                                P_6_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P6_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 7:
                                P7_NAME.setText(Player_Json_Object.getString("name"));
                                P_7_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P7_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 8:
                                P8_NAME.setText(Player_Json_Object.getString("name"));
                                P_8_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P8_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                break;
                            case 9:
                                P9_NAME.setText(Player_Json_Object.getString("name"));
                                P_9_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P9_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
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
            case "BountyHunter":
                return R.drawable.fade;
        }
        return R.drawable.valo_place_holder;
    }
}