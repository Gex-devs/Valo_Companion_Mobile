package com.gex.gex_riot_take_a_shit;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONObject;

import java.util.ArrayList;


public class In_game extends Fragment {
    TextView P0_NAME,P1_NAME,P2_NAME,P3_NAME,P4_NAME,P5_NAME,P6_NAME,P7_NAME,P8_NAME,P9_NAME;
    TextView P_0_K_D,P_1_K_D,P_2_K_D,P_3_K_D,P_4_K_D,P_5_K_D,P_6_K_D,P_7_K_D,P_8_K_D,P_9_K_D;
    ImageView P0_imageholder,P1_imageholder,P2_imageholder,P3_imageholder,P4_imageholder,P5_imageholder,P6_imageholder,P7_imageholder,P8_imageholder,P9_imageholder;
    ShapeableImageView Map;
    MaterialButton surrbtn;
    LinearLayout killfeed;
    ScrollView kill_Feed_container;
    Current_status_Data viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_in_game, container, false);


        // Map shapeable view
        Map = (ShapeableImageView) v.findViewById(R.id.imageView19) ;
        //scroll view
        killfeed = (LinearLayout) v.findViewById(R.id.inner_kill_feed);
        kill_Feed_container = (ScrollView) v.findViewById(R.id.kill_feed);
        kill_Feed_container.fullScroll(View.FOCUS_DOWN);

        //surrender Button
        surrbtn = (MaterialButton) v.findViewById(R.id.surrBtn);


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

        // Data Array
        ArrayList<String> P0 = new ArrayList<>();
        ArrayList<String> P1 = new ArrayList<>();
        ArrayList<String> P2 = new ArrayList<>();
        ArrayList<String> P3 = new ArrayList<>();
        ArrayList<String> P4 = new ArrayList<>();
        ArrayList<String> P5 = new ArrayList<>();
        ArrayList<String> P6 = new ArrayList<>();
        ArrayList<String> P7 = new ArrayList<>();
        ArrayList<String> P8 = new ArrayList<>();
        ArrayList<String> P9 = new ArrayList<>();



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
                                P0_NAME.setText(Player_Json_Object.getString("name").split("#")[0]);
                                P_0_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P0_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P0.add(Player_Json_Object.getString("name").split("#")[0]);
                                P0.add(Player_Json_Object.getString("character"));
                                break;
                            case 1:
                                P1_NAME.setText(Player_Json_Object.getString("name").split("#")[0]);
                                P_1_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P1_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P1.add(Player_Json_Object.getString("name").split("#")[0]);
                                P1.add(Player_Json_Object.getString("character"));
                                break;
                            case 2:
                                P2_NAME.setText(Player_Json_Object.getString("name").split("#")[0]);
                                P_2_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P2_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P2.add(Player_Json_Object.getString("name").split("#")[0]);
                                P2.add(Player_Json_Object.getString("character"));
                                break;
                            case 3:
                                P3_NAME.setText(Player_Json_Object.getString("name").split("#")[0]);
                                P_3_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P3_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P3.add(Player_Json_Object.getString("name").split("#")[0]);
                                P3.add(Player_Json_Object.getString("character"));
                                break;
                            case 4:
                                P4_NAME.setText(Player_Json_Object.getString("name").split("#")[0]);
                                P_4_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P4_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P4.add(Player_Json_Object.getString("name").split("#")[0]);
                                P4.add(Player_Json_Object.getString("character"));
                                break;
                            case 5:
                                P5_NAME.setText(Player_Json_Object.getString("name").split("#")[0]);
                                P_5_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P5_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P5.add(Player_Json_Object.getString("name").split("#")[0]);
                                P5.add(Player_Json_Object.getString("character"));
                                break;
                            case 6:
                                P6_NAME.setText(Player_Json_Object.getString("name").split("#")[0]);
                                P_6_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P6_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P6.add(Player_Json_Object.getString("name").split("#")[0]);
                                P6.add(Player_Json_Object.getString("character"));
                                break;
                            case 7:
                                P7_NAME.setText(Player_Json_Object.getString("name").split("#")[0]);
                                P_7_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P7_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P7.add(Player_Json_Object.getString("name").split("#")[0]);
                                P7.add(Player_Json_Object.getString("character"));
                                break;
                            case 8:
                                P8_NAME.setText(Player_Json_Object.getString("name").split("#")[0]);
                                P_8_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P8_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P8.add(Player_Json_Object.getString("name").split("#")[0]);
                                P8.add(Player_Json_Object.getString("character"));
                                break;
                            case 9:
                                P9_NAME.setText(Player_Json_Object.getString("name").split("#")[0]);
                                P_9_K_D.setText(Player_Json_Object.getString("kills") +"/"+ Player_Json_Object.getString("deaths") +"/"+ Player_Json_Object.getString("assists"));
                                P9_imageholder.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                                P9.add(Player_Json_Object.getString("name").split("#")[0]);
                                P9.add(Player_Json_Object.getString("character"));
                                break;
                        }
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            }catch (Exception e){
                System.out.println(e);
            }
                /*try{
                    // org.json.JSONException: Unterminated array at character 9 of [object Object]
                    JSONObject jsob = new JSONObject(item);
                    String Match_info_object = jsob.getJSONObject("match_info").getString("kill_feed");
                    JSONObject kill_Json_Object = new JSONObject(Match_info_object);
                    int Text_layout_width = 0;
                    int Text_layout_height = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_height);
                    if(kill_Json_Object.getString("attacker").length() < 5){
                        // Small size
                        Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_small);
                    }else if(5 <= kill_Json_Object.getString("attacker").length() && kill_Json_Object.getString("attacker").length() <= 10 ){
                        // Medium Size
                        Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_medium);;
                    }else if(kill_Json_Object.getString("attacker").length() > 10){
                        // Large size
                        Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_larg);;
                    }


                    if(kill_Json_Object.getString("victim").length() < 5){
                        // Small size
                        Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_small);
                    }else if(5 <= kill_Json_Object.getString("victim").length() && kill_Json_Object.getString("attacker").length() <= 10 ){
                        // Medium Size
                        Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_medium);
                    }else if(kill_Json_Object.getString("victim").length() > 10){
                        // Large size
                        Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_larg);
                    }

                    LinearLayout man = new LinearLayout(MainActivity.ContextMethod());
                    man.setOrientation(LinearLayout.HORIZONTAL);
                    man.setBackgroundResource(R.drawable.redd);
                    LinearLayout YOUR_LinearLayout =man;
                    YOUR_LinearLayout.setLayoutParams(param);
                    TextView AT = new TextView(MainActivity.ContextMethod());
                    AT.setText(kill_Json_Object.getString("attacker"));
                    LinearLayout.LayoutParams ATtextParam = new LinearLayout.LayoutParams
                            (Text_layout_width, Text_layout_height, 1.0f);
                    AT.setLayoutParams(ATtextParam);
                    AT.setBackgroundResource(R.drawable.greenk);
                    AT.setGravity(Gravity.CENTER_VERTICAL);
                    AT.setPadding(0,0,getResources().getDimensionPixelSize(R.dimen.text_View_padding),0);
                    AT.setCompoundDrawablesWithIntrinsicBounds(R.drawable.breach_kill_feed,0,R.drawable.vandal,0);
                    AT.setTextColor(R.color.Agent_selected_color);
                    AT.setTextSize(12.0f);

                    TextView VT = new TextView(MainActivity.ContextMethod());
                    LinearLayout.LayoutParams VTtextParam = new LinearLayout.LayoutParams
                            (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                    VT.setText(kill_Json_Object.getString("victim"));
                    VT.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.fbreach_kill_feed,0);
                    VT.setText("Gex");
                    VT.setLayoutParams(VTtextParam);
                    VT.setGravity(Gravity.CENTER);
                    VT.setTextSize(12.0f);
                    VT.setPadding(getResources().getDimensionPixelSize(R.dimen.text2_View_padding),0,0,0);
                    VT.setTextColor(R.color.white);

                    man.addView(AT);
                    man.addView(VT);
                    killfeed.addView(YOUR_LinearLayout);

                }catch (Exception f) {
                    System.out.println(f);
                }
            }*/
        });

        // Map Image
        viewModel.get_map().observe(requireActivity(),item ->{
            switch (item){
                case "Haven":
                    Map.setImageResource(R.drawable.heaven);
                    break;
                case "Bind":
                    Map.setImageResource(R.drawable.bind);
                    break;
                case "Split":
                    Map.setImageResource(R.drawable.split);
                    break;
                case "Ascent":
                    Map.setImageResource(R.drawable.ascent);
                    break;
                case "Icebox":
                    Map.setImageResource(R.drawable.icebox_1);
                    break;
                case "Breeze":
                    Map.setImageResource(R.drawable.breeze);
                    break;
                case "Fracture":
                    Map.setImageResource(R.drawable.fracture);
                    break;
                case "Pearl":
                    Map.setImageResource(R.drawable.pearl);
                    break;
            }
        });

        viewModel.getSelectedItem().observe(requireActivity(),item ->{
            try{
                // org.json.JSONException: Unterminated array at character 9 of [object Object]
                JSONObject jsob = new JSONObject(item);
                String Match_info_object = jsob.getJSONObject("match_info").getString("kill_feed");
                JSONObject kill_Json_Object = new JSONObject(Match_info_object);
                int Text_layout_width = 0;
                int Text_layout_height = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_height);
                int kill_effect = 0;
                String ATcharacter = null;
                String VTcharacter = null;
                int gun = get_respective_weapon(kill_Json_Object.getString("weapon"));


                if(kill_Json_Object.getString("headshot").equals("true")){
                    kill_effect = R.drawable.headshot;
                }
                if(kill_Json_Object.getString("weapon").equals("")){
                    gun = get_respective_weapon(kill_Json_Object.getString("ult"));
                }


                if(kill_Json_Object.getString("attacker").length() < 5){
                    // Small size
                    Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_small);
                }else if(5 <= kill_Json_Object.getString("attacker").length() && kill_Json_Object.getString("attacker").length() <= 10 ){
                    // Medium Size
                    Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_medium);;
                }else if(kill_Json_Object.getString("attacker").length() > 10){
                    // Large size
                    Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_larg);;
                }


                LinearLayout man = new LinearLayout(MainActivity.ContextMethod());
                man.setOrientation(LinearLayout.HORIZONTAL);
                man.setBackgroundResource(R.drawable.redd);
                LinearLayout YOUR_LinearLayout =man;
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                        /*height*/ getResources().getDimensionPixelSize(R.dimen.inner_layout_height),
                        /*weight*/ 10.0f
                );
                YOUR_LinearLayout.setLayoutParams(param);
                TextView AT = new TextView(MainActivity.ContextMethod());
                AT.setText(kill_Json_Object.getString("attacker"));
                LinearLayout.LayoutParams ATtextParam = new LinearLayout.LayoutParams
                        (Text_layout_width, ViewGroup.LayoutParams.MATCH_PARENT, 0.0f);
                AT.setLayoutParams(ATtextParam);
                AT.setBackgroundResource(R.drawable.greenk);
                AT.setGravity(Gravity.CENTER_VERTICAL);
                AT.setPadding(0,0,getResources().getDimensionPixelSize(R.dimen.text_View_padding),0);

                // Setting up character
                if(P1.get(0).equals(kill_Json_Object.getString("attacker"))){
                    ATcharacter = P1.get(1);
                    System.out.println("Character is "+ATcharacter);
                }else if(P2.get(0).equals(kill_Json_Object.getString("attacker"))){
                    ATcharacter = P2.get(1);
                    System.out.println("Character is "+ATcharacter);
                }else if(P3.get(0).equals(kill_Json_Object.getString("attacker"))){
                    ATcharacter = P3.get(1);
                    System.out.println("Character is "+ATcharacter);
                }else if(P4.get(0).equals(kill_Json_Object.getString("attacker"))){
                    ATcharacter = P4.get(1);
                    System.out.println("Character is "+ATcharacter);
                }else if(P5.get(0).equals(kill_Json_Object.getString("attacker"))){
                    ATcharacter = P5.get(1);
                    System.out.println("Character is "+ATcharacter);
                }else if(P6.get(0).equals(kill_Json_Object.getString("attacker"))){
                    ATcharacter = P6.get(1);
                    System.out.println("Character is "+ATcharacter);
                }else if(P7.get(0).equals(kill_Json_Object.getString("attacker"))){
                    ATcharacter = P7.get(1);
                    System.out.println("Character is "+ATcharacter);
                }else if(P8.get(0).equals(kill_Json_Object.getString("attacker"))){
                    ATcharacter = P8.get(1);
                    System.out.println("Character is "+ATcharacter);
                }else if(P9.get(0).equals(kill_Json_Object.getString("attacker"))){
                    ATcharacter = P9.get(1);
                    System.out.println("Character is "+ATcharacter);
                }else if(P0.get(0).equals(kill_Json_Object.getString("attacker"))){
                    ATcharacter = P0.get(1);
                    System.out.println("Character is "+ATcharacter);
                }


                AT.setCompoundDrawablesWithIntrinsicBounds(get_respective_killFeed_AT(ATcharacter),0,gun,0);
                AT.setTextColor(Color.WHITE);
                AT.setTextSize(12.0f);



                if(P1.get(0).equals(kill_Json_Object.getString("victim"))){
                    VTcharacter = P1.get(1);
                    System.out.println("Character is "+VTcharacter);
                }else if(P2.get(0).equals(kill_Json_Object.getString("victim"))){
                    VTcharacter = P2.get(1);
                    System.out.println("Character is "+VTcharacter);
                }else if(P3.get(0).equals(kill_Json_Object.getString("victim"))){
                    VTcharacter = P3.get(1);
                    System.out.println("Character is "+VTcharacter);
                }else if(P4.get(0).equals(kill_Json_Object.getString("victim"))){
                    VTcharacter = P4.get(1);
                    System.out.println("Character is "+VTcharacter);
                }else if(P5.get(0).equals(kill_Json_Object.getString("victim"))){
                    VTcharacter = P5.get(1);
                    System.out.println("Character is "+VTcharacter);
                }else if(P6.get(0).equals(kill_Json_Object.getString("victim"))){
                    VTcharacter = P6.get(1);
                    System.out.println("Character is "+VTcharacter);
                }else if(P7.get(0).equals(kill_Json_Object.getString("victim"))){
                    VTcharacter = P7.get(1);
                    System.out.println("Character is "+VTcharacter);
                }else if(P8.get(0).equals(kill_Json_Object.getString("victim"))){
                    VTcharacter = P8.get(1);
                    System.out.println("Character is "+VTcharacter);
                }else if(P9.get(0).equals(kill_Json_Object.getString("victim"))){
                    VTcharacter = P9.get(1);
                    System.out.println("Character is "+VTcharacter);
                }else if(P0.get(0).equals(kill_Json_Object.getString("victim"))){
                    VTcharacter = P0.get(1);
                    System.out.println("Character is "+VTcharacter);
                }



                TextView VT = new TextView(MainActivity.ContextMethod());
                LinearLayout.LayoutParams VTtextParam = new LinearLayout.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                VT.setText(kill_Json_Object.getString("victim"));
                VT.setCompoundDrawablesWithIntrinsicBounds(kill_effect,0,get_respective_killFeed_VT(VTcharacter),0);
                VT.setLayoutParams(VTtextParam);
                VT.setGravity(Gravity.CENTER);
                VT.setTextSize(12.0f);
                VT.setPadding(getResources().getDimensionPixelSize(R.dimen.text2_View_padding),0,0,0);
                VT.setTextColor(Color.WHITE);

                man.addView(AT);
                man.addView(VT);
                killfeed.addView(YOUR_LinearLayout);

            }catch (Exception f) {
                System.out.println(f);
            }
        });

        viewModel.getSelectedItem().observe(requireActivity(),item ->{
            try{
                // org.json.JSONException: Unterminated array at character 9 of [object Object]
                JSONObject jsob = new JSONObject(item);
                String Match_info_object = jsob.getJSONObject("match_info").getString("score");
                JSONObject match_score = new JSONObject(Match_info_object);
                // {"info":{"match_info":{"score":"{"won":9,"lost":2}"}},"feature":"match_info"}

            }catch (Exception f) {
                System.out.println(f);
            }
        });

        surrbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                String attacker = "Helicoptor";
                String victm = "hanzo";
                int Text_layout_width = 0;
                int Text_layout_height = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_height);
                if(attacker.length() < 5){
                    // Small size
                    Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_small);
                    Log.d("LayoutSize","At small");
                }else if(5 <= attacker.length() && attacker.length() <= 10 ){
                    // Medium Size
                    Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_medium);
                    Log.d("LayoutSize","At Medium");
                }else if(attacker.length() > 10){
                    // Large size
                    Text_layout_width = getResources().getDimensionPixelSize(R.dimen.inner_layout_text_view_width_larg);
                    Log.d("LayoutSize","At Large");
                }

                LinearLayout man = new LinearLayout(MainActivity.ContextMethod());
                man.setOrientation(LinearLayout.HORIZONTAL);
                man.setBackgroundResource(R.drawable.redd);
                LinearLayout YOUR_LinearLayout =man;
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                        /*height*/ getResources().getDimensionPixelSize(R.dimen.inner_layout_height),
                        /*weight*/ 10.0f
                );
                YOUR_LinearLayout.setLayoutParams(param);
                TextView AT = new TextView(MainActivity.ContextMethod());
                AT.setText(attacker);
                LinearLayout.LayoutParams ATtextParam = new LinearLayout.LayoutParams
                        (Text_layout_width, ViewGroup.LayoutParams.MATCH_PARENT, 0.0f);
                AT.setLayoutParams(ATtextParam);
                AT.setBackgroundResource(R.drawable.greenk);
                AT.setGravity(Gravity.CENTER_VERTICAL);
                AT.setPadding(0,0,getResources().getDimensionPixelSize(R.dimen.text_View_padding),0);
                AT.setCompoundDrawablesWithIntrinsicBounds(R.drawable.k_breach,0,R.drawable.vandal,0);
                AT.setTextColor(Color.WHITE);
                AT.setTextSize(12.0f);

                TextView VT = new TextView(MainActivity.ContextMethod());
                LinearLayout.LayoutParams VTtextParam = new LinearLayout.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.0f);
                VT.setText(victm);
                VT.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.fbreachs,0);
                VT.setLayoutParams(VTtextParam);
                VT.setGravity(Gravity.CENTER);
                VT.setTextSize(12.0f);
                VT.setPadding(getResources().getDimensionPixelSize(R.dimen.text2_View_padding),0,0,0);
                VT.setTextColor(Color.WHITE);

                man.addView(AT);
                man.addView(VT);
                killfeed.addView(YOUR_LinearLayout);

                // Empty Linearlayout
                LinearLayout padding_layout = new LinearLayout(MainActivity.ContextMethod());
                padding_layout.setOrientation(LinearLayout.HORIZONTAL);
                padding_layout.setBackgroundResource(R.drawable.empty);
                LinearLayout PADDING_LinearLayout =padding_layout;
                LinearLayout.LayoutParams pad_params = new LinearLayout.LayoutParams(
                        /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                        /*height*/ getResources().getDimensionPixelSize(R.dimen.padding_dimen),
                        /*weight*/ 10.0f
                );
                PADDING_LinearLayout.setLayoutParams(pad_params);
                killfeed.addView(PADDING_LinearLayout);
            }
        });

        return v;
    }

    public int get_respective_killFeed_AT(String Agent_name){
        switch (Agent_name){
            case "Clay":
                return R.drawable.k_raze;
            case "Pandemic":
                return R.drawable.k_viper;
            case "Wraith":
                return R.drawable.k_omen;
            case "Hunter":
                return R.drawable.k_sova;
            case "Thorne":
                return R.drawable.k_sage;
            case "Phoenix":
                return R.drawable.k_phx;
            case "Wushu":
                return R.drawable.k_jett;
            case "Gumshoe":
                return R.drawable.k_cypher;
            case "Sarge":
                return R.drawable.k_brim;
            case "Breach":
                return R.drawable.k_breach;
            case "Vampire":
                return R.drawable.k_reyna;
            case "Killjoy":
                return R.drawable.k_killjoy;
            case "Guide":
                return R.drawable.k_skyel;
            case "Stealth":
                return R.drawable.k_yoru;
            case "Rift":
                return R.drawable.k_astra;
            case "Grenadier":
                return R.drawable.k_kayo;
            case "Deadeye":
                return R.drawable.k_chamber;
            case "Sprinter":
                return R.drawable.k_neon;
            case "BountyHunter":
                return R.drawable.k_fade;
        }
        return R.drawable.valo_place_holder;
    }

    public int get_respective_killFeed_VT(String Agent_name){
        switch (Agent_name){
            case "Clay":
                return R.drawable.f_raze;
            case "Pandemic":
                return R.drawable.f_viper;
            case "Wraith":
                return R.drawable.f_omen;
            case "Hunter":
                return R.drawable.f_sova;
            case "Thorne":
                return R.drawable.f_sage;
            case "Phoenix":
                return R.drawable.f_phx;
            case "Wushu":
                return R.drawable.f_jett;
            case "Gumshoe":
                return R.drawable.f_cypher;
            case "Sarge":
                return R.drawable.f_brim;
            case "Breach":
                return R.drawable.fbreachs;
            case "Vampire":
                return R.drawable.f_reyna;
            case "Killjoy":
                return R.drawable.f_killjoy;
            case "Guide":
                return R.drawable.f_skye;
            case "Stealth":
                return R.drawable.f_yoru;
            case "Rift":
                return R.drawable.f_astra;
            case "Grenadier":
                return R.drawable.f_kayo;
            case "Deadeye":
                return R.drawable.f_chamber;
            case "Sprinter":
                return R.drawable.f_neon;
            case "BountyHunter":
                return R.drawable.f_fade;
        }
        return R.drawable.valo_place_holder;
    }

    public int get_respective_weapon(String weapon_name){
        switch (weapon_name){
            case "TX_Hud_Assault_AR10A2_S":
                System.out.println("Phantom");
                return R.drawable.f_phantom;
            case "TX_Hud_AutoPistol":
                System.out.println("Frenzy");
                return R.drawable.f_frenzy;
            case "TX_Hud_Burst":
                System.out.println("Burst");
                return R.drawable.f_bulldog;
            case "TX_Hud_Deadeye_Q_Pistol":
                System.out.println("Chamber_deag");
                return R.drawable.f_dead;
            case "TX_Hud_Deadeye_X_GiantSlayer":
                System.out.println("Chamber_awp");
                return R.drawable.f_chamber_awp;
            case "tx_hud_dmr":
                System.out.println("Guradian");
                return R.drawable.f_ghost;
            case "TX_Hud_HMG":
                System.out.println("Odin");
                return R.drawable.f_odin;
            case "TX_Hud_Knife_Standard_S":
                System.out.println("Knife");
                return R.drawable.f_knife;
            case "TX_Hud_LMG":
                System.out.println("Ares");
                return R.drawable.f_aries;
            case "TX_Hud_Operator":
                System.out.println("awp");
                return R.drawable.f_awp;
            case "TX_Hud_Pistol_Glock_S":
                System.out.println("Classic");
                return R.drawable.f_classic;
            case "TX_Hud_Pistol_Luger_S":
                System.out.println("Ghost");
                return R.drawable.f_ghost;
            case "TX_Hud_Pistol_Revolver_S":
                System.out.println("Deag");
                return R.drawable.f_sherif;
            case "TX_Hud_Pistol_SawedOff_S":
                System.out.println("Shorty");
                return R.drawable.f_shorty;
            case "TX_Hud_Pump":
                System.out.println("Bucky");
                return R.drawable.f_bucky;
            case "TX_Hud_Shotguns_Spas12_S":
                System.out.println("Judge");
                return R.drawable.f_judge;
            case "TX_Hud_SMG_KrissVector_S":
            case "TX_Hud_Vector":
                System.out.println("Stinger");
                return R.drawable.f_stinger;
            case "TX_Hud_SMG_MP5_S":
                System.out.println("Specter");
                return R.drawable.f_specter;
            case "TX_Hud_Sniper_BoltAction_S":
                System.out.println("Marshall");
                return R.drawable.f_marshal;
            case "TX_Hud_Volcano":
                System.out.println("Vandal");
                return R.drawable.vandal;
            case "TX_SnowballLauncher":
                System.out.println("Snowball");
                return R.drawable.f_snow;
            case "TX_Thorne_Heal":
                System.out.println("sage revive");
                return R.drawable.f_revive;

        }
        return 0;
    }
    public int get_respective_image(String Agent_name){
        switch (Agent_name){
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