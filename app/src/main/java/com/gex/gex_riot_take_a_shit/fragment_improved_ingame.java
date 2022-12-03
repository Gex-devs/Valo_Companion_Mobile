package com.gex.gex_riot_take_a_shit;

import static com.gex.gex_riot_take_a_shit.MainActivity.viewModel;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;


public class fragment_improved_ingame extends Fragment {
    Button debug;
    TableLayout YourTeam,Enemy;
    TextView yourScore,enemyScore,roundnumb;
    ArrayList<Integer> exclude_ints = new ArrayList<>();
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

        YourTeam = v.findViewById(R.id.teammates);
        Enemy = v.findViewById(R.id.enemy);

        yourScore = v.findViewById(R.id.attacker_score);
        enemyScore = v.findViewById(R.id.defender_score);
        roundnumb = v.findViewById(R.id.Round);

        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);
        viewModel.getSelectedItem().observe(requireActivity(),item ->{
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
                                (240, 100, 0.0f);


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
        });

        // for scoreboard
        viewModel.getSelectedItem().observe(requireActivity(),item ->{
            try{
                // org.json.JSONException: Unterminated array at character 9 of [object Object]

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