package com.gex.gex_riot_take_a_shit.fragments.gameFragments;

import static com.gex.gex_riot_take_a_shit.MainActivity.viewModel;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gex.gex_riot_take_a_shit.Current_status_Data;
import com.gex.gex_riot_take_a_shit.LocalApiHandler;
import com.gex.gex_riot_take_a_shit.MainActivity;
import com.gex.gex_riot_take_a_shit.R;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class fragment_improved_ingame extends Fragment {
    Button debug;
    TableLayout YourTeam,Enemy;
    TextView yourScore,enemyScore,roundnumb;
    ArrayList<Integer> exclude_ints = new ArrayList<>();
    LinearLayout killfeed;
    ShapeableImageView map;
    ScrollView killfeed_container;
    public fragment_improved_ingame() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_improved_ingame, container, false);

        killfeed_container = (ScrollView) v.findViewById(R.id.chat_body);
        killfeed = (LinearLayout) v.findViewById(R.id.inner_kill_feed);

        map = (ShapeableImageView) v.findViewById(R.id.map) ;

        YourTeam = v.findViewById(R.id.teammates);
        Enemy = v.findViewById(R.id.enemy);

        yourScore = v.findViewById(R.id.attacker_score);
        enemyScore = v.findViewById(R.id.defender_score);
        roundnumb = v.findViewById(R.id.Round);

        // On Create of fragment del notification
        try {
            NotificationManagerCompat managerCompact = NotificationManagerCompat.from(MainActivity.ContextMethod());
            managerCompact.cancel(1);
        }catch (Exception e){
            System.out.println(e);
        }


        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);
        /*viewModel.getSelectedItem().observe(requireActivity(),item ->{
            try{
                // org.json.JSONException: Unterminated array at character 9 of [object Object]
                for(int i = 0;i < 10 && !exclude_ints.contains(i);i++)
                    try {
                        JSONObject jsob = new JSONObject(item);
                        String Key_Element = "scoreboard_" + i;
                        String Match_info_object = jsob.getJSONObject("match_info").getString(Key_Element);
                        JSONObject Player_Json_Object = new JSONObject(Match_info_object);
                        LinearLayout.LayoutParams Imageparam = new LinearLayout.LayoutParams
                                (207, 186, 0.0f);

                        LinearLayout.LayoutParams playernameparam = new LinearLayout.LayoutParams
                                (2w, 100, 0.0f);


                        TableRow Placement_TableRow = new TableRow(MainActivity.ContextMethod());
                        LinearLayout InsiderLinearLayout = new LinearLayout(MainActivity.ContextMethod());
                        TextView player_name = new TextView(MainActivity.ContextMethod());
                        ImageView charimage = new ImageView(MainActivity.ContextMethod());


                        charimage.setImageResource(get_respective_image(Player_Json_Object.getString("character")));
                        charimage.setLayoutParams(Imageparam);

                        player_name.setText(Player_Json_Object.getString("name").split("#")[0]);
                        //player_name.setPadding(R.dimen.text_margin,R.dimen.inner_layout_text_view_width,0,0);
                        Typeface typeface = ResourcesCompat.getFont(MainActivity.ContextMethod(), R.font.poppins_bold);
                        player_name.setTypeface(typeface);
                        player_name.setTextColor(Color.WHITE);
                        player_name.setLayoutParams(playernameparam);

                        Placement_TableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

                        InsiderLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        InsiderLinearLayout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

                        InsiderLinearLayout.addView(charimage);
                        InsiderLinearLayout.addView(player_name);

                        Placement_TableRow.addView(InsiderLinearLayout);

                        if (!Player_Json_Object.getBoolean("teammate") && Enemy.getChildCount() <= 5) {
                            Enemy.addView(Placement_TableRow);
                        }
                        if (YourTeam.getChildCount() <= 5) {
                            YourTeam.addView(Placement_TableRow);
                        }
                        exclude_ints.add(i);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
            }catch (Exception e){
                System.out.println(e);
            }
        });*/



        // Get the "Players" array from the JSON object
        try {
            JSONObject jsonObject = new JSONObject(LocalApiHandler.get_players_Current_game());
            JSONArray players = jsonObject.getJSONArray("Players");
            for (int i = 0; i < players.length(); i++) {
                // Get the player object as a JSONObject
                JSONObject player = players.getJSONObject(i);

                // Get the Subject, TeamID, and Rank from the player object
                String subject = player.getString("Subject");
                String teamID = player.getString("TeamID");
                String characterID = player.getString("CharacterID");

                // The Rank is nested under the "SeasonalBadgeInfo" object
                JSONObject badgeInfo = player.getJSONObject("SeasonalBadgeInfo");
                int rank = badgeInfo.getInt("Rank");

                LinearLayout.LayoutParams Imageparam = new LinearLayout.LayoutParams
                        (207, 186, 0.0f);

                LinearLayout.LayoutParams playernameparam = new LinearLayout.LayoutParams
                        (240, 100, 0.0f);


                TableRow Placement_TableRow = new TableRow(MainActivity.ContextMethod());
                LinearLayout InsiderLinearLayout = new LinearLayout(MainActivity.ContextMethod());
                TextView player_name = new TextView(MainActivity.ContextMethod());
                ImageView charimage = new ImageView(MainActivity.ContextMethod());

                charimage.setImageResource(get_respective_image_ID(characterID));
                charimage.setLayoutParams(Imageparam);

                player_name.setText(LocalApiHandler.getUsername(subject));
                //player_name.setPadding(R.dimen.text_margin,R.dimen.inner_layout_text_view_width,0,0);
                Typeface typeface = ResourcesCompat.getFont(MainActivity.ContextMethod(), R.font.poppins_bold);
                player_name.setTypeface(typeface);
                player_name.setTextColor(Color.WHITE);
                player_name.setLayoutParams(playernameparam);

                Placement_TableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

                InsiderLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                InsiderLinearLayout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

                InsiderLinearLayout.addView(charimage);
                InsiderLinearLayout.addView(player_name);

                Placement_TableRow.addView(InsiderLinearLayout);

                if (teamID.equals("Red")) {
                    Enemy.addView(Placement_TableRow);
                }
                if (teamID.equals("Blue")) {
                    YourTeam.addView(Placement_TableRow);
                }
            }
        } catch (JSONException | IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

// Loop through the array


        // for kill feed
        viewModel.getSelectedItem().observe(requireActivity(),item ->{
            try{
                // org.json.JSONException: Unterminated array at character 9 of [object Object]
                JSONObject jsob = new JSONObject(item);
                String Match_info_object = jsob.getJSONObject("match_info").getString("kill_feed");
                JSONObject kill_Json_Object = new JSONObject(Match_info_object);
                int Text_layout_width = 0;
                int kill_effect = 0;
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
                LinearLayout YOUR_LinearLayout = man;
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

                AT.setCompoundDrawablesWithIntrinsicBounds(0,0,gun,0);
                AT.setTextColor(Color.WHITE);
                AT.setTextSize(12.0f);

                TextView VT = new TextView(MainActivity.ContextMethod());
                LinearLayout.LayoutParams VTtextParam = new LinearLayout.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                VT.setText(kill_Json_Object.getString("victim"));
                VT.setCompoundDrawablesWithIntrinsicBounds(kill_effect,0,0,0);
                VT.setLayoutParams(VTtextParam);
                VT.setGravity(Gravity.CENTER);
                VT.setTextSize(12.0f);
                VT.setPadding(getResources().getDimensionPixelSize(R.dimen.text2_View_padding),0,0,0);
                VT.setTextColor(Color.WHITE);

                man.addView(AT);
                man.addView(VT);
                killfeed.addView(YOUR_LinearLayout);
                killfeed_container.fullScroll(ScrollView.FOCUS_DOWN);

            }catch (Exception e) {
                StackTraceElement[] stackTrace = e.getStackTrace();
                if (stackTrace.length > 0) {
                    StackTraceElement element = stackTrace[0];
                    System.out.println("Exception occurred in " + element.getClassName() + "." + element.getMethodName() + " at line " + element.getLineNumber());
                }
                Log.d("killFeed", e.toString());
            }
        });

        // for scoreboard
        viewModel.getSelectedItem().observe(requireActivity(),item ->{
            try{
                // org.json.JSONException: Unterminated array at character 9 of [object Object]
                    try {
                        JSONObject jsob1 = new JSONObject(item);
                        roundnumb.setText(jsob1.getJSONObject("match_info").getString("round_number"));
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    try{
                        JSONObject jsob = new JSONObject(item);
                        String Match_info_object = jsob.getJSONObject("match_info").getString("score");
                        JSONObject score = new JSONObject(Match_info_object);
                        yourScore.setText(String.valueOf(score.getInt("won")));
                        enemyScore.setText(String.valueOf(score.getInt("lost")));
                    }catch (Exception e){
                        System.out.println(e);
                    }


            }catch (Exception e){
                System.out.println(e);
            }
        });
        return v;
    }
    public int get_respective_image_ID(String Agent_name){
        switch (Agent_name){
            case "f94c3b30-42be-e959-889c-5aa313dba261":
                return R.drawable.raze;
            case "707eab51-4836-f488-046a-cda6bf494859":
                return R.drawable.viper;
            case "8e253930-4c05-31dd-1b6c-968525494517":
                return R.drawable.omen;
            case "320b2a48-4d9b-a075-30f1-1f93a9b638fa":
                return R.drawable.sova;
            case "569fdd95-4d10-43ab-ca70-79becc718b46":
                return R.drawable.sage;
            case "eb93336a-449b-9c1b-0a54-a891f7921d69":
                return R.drawable.phoenix;
            case "add6443a-41bd-e414-f6ad-e58d267f4e95":
                return R.drawable.jett;
            case "117ed9e3-49f3-6512-3ccf-0cada7e3823b":
                return R.drawable.cypher;
            case "9f0d8ba9-4140-b941-57d3-a7ad57c6b417":
                return R.drawable.brimstone;
            case "5f8d3a7f-467b-97f3-062c-13acf203c006":
                return R.drawable.breach;
            case "a3bfb853-43b2-7238-a4f1-ad90e9e46bcc":
                return R.drawable.reyna;
            case "1e58de9c-4950-5125-93e9-a0aee9f98746":
                return R.drawable.killjoy;
            case "6f2a04ca-43e0-be17-7f36-b3908627744d":
                return R.drawable.skye;
            case "7f94d92c-4234-0a36-9646-3a87eb8b5c89":
                // fix this nigga
                return R.drawable.yoru;
            case "41fb69c1-4189-7b37-f117-bcaf1e96f1bf":
                return R.drawable.astra;
            case "601dbbe7-43ce-be57-2a40-4abd24953621":
                return R.drawable.kayo;
            case "22697a3d-45bf-8dd7-4fec-84a9e28c69d7":
                return R.drawable.chamber;
            case "bb2a4828-46eb-8cd1-e765-15848195d751":
                return R.drawable.neon;
            case "dade69b4-4f5a-8528-247b-219e5a1facd6":
                return R.drawable.fade;
        }
        return R.drawable.valo_place_holder;
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

}