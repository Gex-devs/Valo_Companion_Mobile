package com.gex.gex_riot_take_a_shit.fragments.gameFragments;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.gex.gex_riot_take_a_shit.Current_status_Data;
import com.gex.gex_riot_take_a_shit.LocalApiHandler;
import com.gex.gex_riot_take_a_shit.MainActivity;
import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.ThirdParty.ValorantApi;
import com.gex.gex_riot_take_a_shit.enums.GameModes;
import com.google.android.material.imageview.ShapeableImageView;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.JellyTypes.Jelly;
import com.nightonke.jellytogglebutton.State;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;



public class menuSelection extends Fragment implements View.OnClickListener {

    Button Start,Send;
    EditText body_value;
    ShapeableImageView p1,p2,p3,p4,p5;
    ScrollView textchat;
    JellyToggleButton server_Switch;
    TextView p1_title,p2_title,p3_title,p4_title,p5_title;
    TextView p1_name,p2_name,p3_name,p4_name,p5_name;
    SweetAlertDialog pDialog;
    private SmartMaterialSpinner<String> spProvince;
    private List<String> provinceList;

    private Current_status_Data _viewModel;

    public menuSelection(Current_status_Data viewModel){
        _viewModel = viewModel;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.RIGHT,enter,200);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_menu_selection, container, false);

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
         //buttons
         Start = (Button) v.findViewById(R.id.start_btn);
         Start.setOnClickListener(this);

         Send = (Button) v.findViewById(R.id.button_gchat_send);
         Send.setOnClickListener(this);

         //scroll view
        textchat = (ScrollView)v.findViewById(R.id.kill_feed);

         // Player image view
         p1 = (ShapeableImageView) v.findViewById(R.id.player_1);
         p2 = (ShapeableImageView) v.findViewById(R.id.player_2);
         p3 = (ShapeableImageView) v.findViewById(R.id.player_3);
         p4 = (ShapeableImageView) v.findViewById(R.id.player_4);
         p5 = (ShapeableImageView) v.findViewById(R.id.player_5);

         // Player Name

        p1_name = (TextView) v.findViewById(R.id.player_1_name);
        p2_name = (TextView) v.findViewById(R.id.player_2_name);
        p3_name = (TextView) v.findViewById(R.id.player_3_name);
        p4_name = (TextView) v.findViewById(R.id.player_4_name);
        p5_name = (TextView) v.findViewById(R.id.player_5_name);

         // Text chat value
         body_value = (EditText) v.findViewById(R.id.edit_gchat_message);

         //player titles
        p1_title = (TextView) v.findViewById(R.id.player_1_title);
        p2_title = (TextView) v.findViewById(R.id.player_2_title);
        p3_title = (TextView) v.findViewById(R.id.player_3_title);
        p4_title = (TextView) v.findViewById(R.id.player_4_title);
        p5_title = (TextView) v.findViewById(R.id.player_5_title);

         // layout params
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.inner_kill_feed);

        LinearLayout.LayoutParams Text_Container = new LinearLayout.LayoutParams
                (getResources().getDimensionPixelSize(R.dimen.text_chat_widtg), getResources().getDimensionPixelSize(R.dimen.text_View_padding), 0.0f);

        LinearLayout.LayoutParams Name_Text = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout.LayoutParams Text_Value = new LinearLayout.LayoutParams
                (R.dimen.inner_layout_text_view_width_small, LinearLayout.LayoutParams.WRAP_CONTENT, 0.0f);


        //Open party Swtich // https://github.com/Nightonke/JellyToggleButton#listener
        server_Switch = v.findViewById(R.id.party_Switch);
        server_Switch.setJelly(Jelly.LAZY_TREMBLE_TAIL_SLIM_JIM);

        server_Switch.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
                    try {
                        LocalApiHandler.set_party_status("closed");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(state.equals(State.RIGHT)){
                    try {
                        LocalApiHandler.set_party_status("open");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);
        // Text chat
        _viewModel.getSelectedItem().observe(requireActivity(), item ->{
            try {
                JSONObject jsob = new JSONObject(item);
                String Match_info_object = String.valueOf(jsob.getJSONObject("messages"));
                JSONObject ff = new JSONObject(Match_info_object);
                //System.out.println(ff);
                JSONArray gg = ff.getJSONArray("$insert");
                System.out.println(gg);
                //System.out.println(gg.getJSONArray(0).getJSONObject(1));
                JSONObject uu = new JSONObject(String.valueOf(gg.getJSONArray(0).getJSONObject(1)));
                System.out.println(uu.getString("body"));

                LinearLayout InsiderLinearLayout = new LinearLayout(MainActivity.ContextMethod());
                InsiderLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                InsiderLinearLayout.setLayoutParams(Text_Container);
                TextView Name = new TextView(MainActivity.ContextMethod());
                Name.setLayoutParams(Name_Text);
                Name.setText(uu.getString("game_name")+":");
                Name.setTextColor(Color.WHITE);

                TextView Body = new TextView(MainActivity.ContextMethod());
                Body.setLayoutParams(Text_Value);
                Body.setText(" "+uu.getString("body"));
                Body.setTextColor(Color.WHITE);

                InsiderLinearLayout.addView(Name);
                InsiderLinearLayout.addView(Body);


                linearLayout.addView(InsiderLinearLayout);
                textchat.scrollTo(0, textchat.getBottom());
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println(e);
            }
        });

        // Update on render
        _viewModel.getSelectedItem().observe(requireActivity(), item ->{
            UiUpdate(item);
        });

        // drop down menu https://github.com/Chivorns/SmartMaterialSpinner
        spProvince = v.findViewById(R.id.spinner1);
        provinceList = new ArrayList<>();
        for (GameModes gameModes: GameModes.values()){
            provinceList.add(gameModes.getDisplayName());
        }
        spProvince.setItem(provinceList);
        spProvince.setItemListBackground(R.color.Valo_Color);
        try {
            UiUpdate(LocalApiHandler.get_party());
            spProvince.setSelection(GameModes.getByCodeName(LocalApiHandler.GetQeueMode()).ordinal());
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // this how u select item spProvince.setSelection(0);

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //Toasty.info(MainActivity.ContextMethod(), provinceList.get(position), Toast.LENGTH_SHORT, true).show();
                try {
                    LocalApiHandler.Change_Q(GameModes.values()[position].getCodeName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(),"No Item",Toast.LENGTH_SHORT);
            }
        });

        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        System.out.println("Clicked");
        switch (view.getId()){
            case R.id.button_gchat_send:
                try {
                    LocalApiHandler.send_text(String.valueOf(body_value.getText()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Toasty.info(MainActivity.ContextMethod(), "Gex:Text Sent", Toast.LENGTH_SHORT, true).show();
                body_value.setText("");
                break;
           case R.id.start_btn:
               if(Start.getText().equals("In Queue")){
                   try {
                       LocalApiHandler.LeaveQ();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }else {
                   try {
                       LocalApiHandler.StartQ();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
                break;
            case R.id.exit_party:
                _viewModel.for_char("LeaveParty");
                break;
        }
    }
    public void UiUpdate(String item){
        try {
            JSONObject jsob = new JSONObject(item);
            String QueueID = jsob.getJSONObject("MatchmakingData").getString("QueueID");
            spProvince.setSelection(GameModes.getByCodeName(QueueID).ordinal());

            JSONArray party_members = jsob.getJSONArray("Members");
            switch (jsob.getString("Accessibility")){
                case "CLOSED":
                    if(!server_Switch.isChecked()){
                        Log.d("Toggle Button", "JellyToggle is already False");
                    }else if(server_Switch.isChecked()){
                        server_Switch.setChecked(false);
                    }
                    break;
                case "OPEN":
                    if(server_Switch.isChecked()){
                        Log.d("Toggle Button", "JellyToggle is already True");
                    }else if(!server_Switch.isChecked()){
                        server_Switch.setChecked(true);
                    }
                    break;
            }
            switch (jsob.getString("State")){
                case "DEFAULT":
                    Log.d("Start_Button", "Set the button to Start");
                    Start.setText("START");
                    Start.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal_700)));
                    break;
                case "MATCHMAKING":
                    Log.d("Start_Button", "Set the button to In Queue");
                    Start.setText("In Queue");
                    Start.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Button_Color)));
                    break;
                case "MATCHMADE_GAME_STARTING":
                    new SweetAlertDialog(getContext())
                            .setTitleText("MATCH FOUND")
                            .show();
                    break;
            }


            for(int i = 0;i < 5;i++){
                try {
                    String player_puid;
                    String player_card;
                    String player_title;
                    switch (i){
                        case 0:
                            player_puid = party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject");
                            player_card =  party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID");
                            player_title = party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID");
                            p1_name.setText(LocalApiHandler.getUsername(player_puid));
                            Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetPlayerCard(player_card,true)).into(p1);
                            p1_title.setText(ValorantApi.GetPlayerTitle(player_title));
                            break;
                        case 1:
                            player_puid = party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject");
                            player_card =  party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID");
                            player_title = party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID");
                            p2_name.setText(LocalApiHandler.getUsername(player_puid));
                            Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetPlayerCard(player_card,false)).into(p2);
                            p2_title.setText(ValorantApi.GetPlayerTitle(player_title));
                            break;
                        case 2:
                            player_puid = party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject");
                            player_card =  party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID");
                            player_title = party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID");
                            p3_name.setText(LocalApiHandler.getUsername(player_puid));
                            Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetPlayerCard(player_card,false)).into(p3);
                            p3_title.setText(ValorantApi.GetPlayerTitle(player_title));
                            break;
                        case 3:
                            player_puid = party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject");
                            player_card =  party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID");
                            player_title = party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID");
                            p4_name.setText(LocalApiHandler.getUsername(player_puid));
                            Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetPlayerCard(player_card,false)).into(p4);
                            p4_title.setText(ValorantApi.GetPlayerTitle(player_title));
                            break;
                        case 4:
                            player_puid = party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject");
                            player_card =  party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID");
                            player_title = party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID");
                            p5_name.setText(LocalApiHandler.getUsername(player_puid));
                            Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetPlayerCard(player_card,false)).into(p5);
                            p5_title.setText(ValorantApi.GetPlayerTitle(player_title));
                            break;
                    }
                } catch (IOException | ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

//            StringRequest request = null;
//            RequestQueue rQueue = Volley.newRequestQueue(MainActivity.ContextMethod());
//            for(int i = 0;i < 5;i++){
//                try {
//                    switch (i){
//                        case 0:
//                            System.out.println("player 1");
//                            System.out.println("player_puid: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject"));
//                            System.out.println("player_card: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID"));
//                            System.out.println("player_title: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID"));
//                            p1_name.setText(LocalApiHandler.getUsername(party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject")));
//                            request = new StringRequest("https://valorant-api.com/v1/playercards/"+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String string) {
//                                    System.out.println(string);
//                                    try {
//                                        JSONObject json = new JSONObject(string);
//                                        Picasso.with(MainActivity.ContextMethod()).load(json.getJSONObject("data").getString("wideArt")).into(p1);
//                                        // Downgraded to 2.0.0 to make cardlist work
//                                        //Picasso.get().load(json.getJSONObject("data").getString("wideArt")).into(p1);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    System.out.println("error");
//                                }
//                            });
//                            rQueue.add(request);
//                            request = new StringRequest("https://valorant-api.com/v1/playertitles/"+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String string) {
//                                    System.out.println(string);
//                                    try {
//                                        JSONObject json = new JSONObject(string);
//                                        JSONObject ff = json.getJSONObject("data");
//                                        p1_title.setText(ff.getString("titleText"));
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    System.out.println("error");
//                                }
//                            });
//                            rQueue.add(request);
//                            break;
//                        case 1:
//                            System.out.println("player 2");
//                            System.out.println("player_puid: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject"));
//                            System.out.println("player_card: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID"));
//                            System.out.println("player_title: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID"));
//                            p2_name.setText(LocalApiHandler.getUsername(party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject")));
//                            request = new StringRequest("https://valorant-api.com/v1/playercards/"+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String string) {
//                                    System.out.println(string);
//                                    try {
//                                        JSONObject json = new JSONObject(string);
//                                        Picasso.with(MainActivity.ContextMethod()).load(json.getJSONObject("data").getString("smallArt")).into(p2);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    System.out.println("error");
//                                }
//                            });
//                            rQueue.add(request);
//                            request = new StringRequest("https://valorant-api.com/v1/playertitles/"+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String string) {
//                                    System.out.println(string);
//                                    try {
//                                        JSONObject json = new JSONObject(string);
//                                        JSONObject ff = json.getJSONObject("data");
//                                        p2_title.setText(ff.getString("titleText"));
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    System.out.println("error");
//                                }
//                            });
//                            rQueue.add(request);
//                            break;
//                        case 2:
//                            System.out.println("player 3");
//                            System.out.println("player_puid: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject"));
//                            System.out.println("player_card: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID"));
//                            System.out.println("player_title: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID"));
//                            p3_name.setText(LocalApiHandler.getUsername(party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject")));
//                            request = new StringRequest("https://valorant-api.com/v1/playercards/"+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String string) {
//                                    System.out.println(string);
//                                    try {
//                                        JSONObject json = new JSONObject(string);
//                                        Picasso.with(MainActivity.ContextMethod()).load(json.getJSONObject("data").getString("smallArt")).into(p3);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    System.out.println("error");
//                                }
//                            });
//                            rQueue.add(request);
//                            request = new StringRequest("https://valorant-api.com/v1/playertitles/"+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String string) {
//                                    System.out.println(string);
//                                    try {
//                                        JSONObject json = new JSONObject(string);
//                                        JSONObject ff = json.getJSONObject("data");
//                                        p3_title.setText(ff.getString("titleText"));
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    System.out.println("error");
//                                }
//                            });
//                            rQueue.add(request);
//                            break;
//                        case 3:
//                            System.out.println("player 4");
//                            System.out.println("player_puid: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject"));
//                            System.out.println("player_card: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID"));
//                            System.out.println("player_title: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID"));
//                            p4_name.setText(LocalApiHandler.getUsername(party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject")));
//                            request = new StringRequest("https://valorant-api.com/v1/playercards/"+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String string) {
//                                    System.out.println(string);
//                                    try {
//                                        JSONObject json = new JSONObject(string);
//                                        Picasso.with(MainActivity.ContextMethod()).load(json.getJSONObject("data").getString("smallArt")).into(p4);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    System.out.println("error");
//                                }
//                            });
//                            rQueue.add(request);
//                            request = new StringRequest("https://valorant-api.com/v1/playertitles/"+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String string) {
//                                    System.out.println(string);
//                                    try {
//                                        JSONObject json = new JSONObject(string);
//                                        JSONObject ff = json.getJSONObject("data");
//                                        p4_title.setText(ff.getString("titleText"));
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    System.out.println("error");
//                                }
//                            });
//                            rQueue.add(request);
//                            break;
//                        case 4:
//                            System.out.println("player 5");
//                            System.out.println("player_puid: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject"));
//                            System.out.println("player_card: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID"));
//                            System.out.println("player_title: "+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID"));
//                            p5_name.setText(LocalApiHandler.getUsername(party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("Subject")));
//                            request = new StringRequest("https://valorant-api.com/v1/playercards/"+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerCardID"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String string) {
//                                    System.out.println(string);
//                                    try {
//                                        JSONObject json = new JSONObject(string);
//                                        Picasso.with(MainActivity.ContextMethod()).load(json.getJSONObject("data").getString("smallArt")).into(p5);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    System.out.println("error");
//                                }
//                            });
//                            rQueue.add(request);
//                            request = new StringRequest("https://valorant-api.com/v1/playertitles/"+party_members.getJSONObject(i).getJSONObject("PlayerIdentity").getString("PlayerTitleID"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String string) {
//                                    System.out.println(string);
//                                    try {
//                                        JSONObject json = new JSONObject(string);
//                                        JSONObject ff = json.getJSONObject("data");
//                                        p5_title.setText(ff.getString("titleText"));
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    System.out.println("error");
//                                }
//                            });
//                            rQueue.add(request);
//                            break;
//                    }
//
//                }catch (JSONException | IOException | ExecutionException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }


        }catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}