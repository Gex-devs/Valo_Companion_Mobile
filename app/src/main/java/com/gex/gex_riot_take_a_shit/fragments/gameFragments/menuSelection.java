package com.gex.gex_riot_take_a_shit.fragments.gameFragments;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.gex.gex_riot_take_a_shit.Background.BackgroundWatchers;
import com.gex.gex_riot_take_a_shit.Current_status_Data;
import com.gex.gex_riot_take_a_shit.MainActivity;
import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.ThirdParty.ValorantApi;
import com.gex.gex_riot_take_a_shit.enums.GameModes;
import com.gex.gex_riot_take_a_shit.enums.PartyStatus;
import com.gex.gex_riot_take_a_shit.enums.QeueType;
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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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
    JellyToggleButton.OnStateChangeListener onStateChangeListener;
    AdapterView.OnItemSelectedListener GameModeSelectorListener;
    TextView p1_title,p2_title,p3_title,p4_title,p5_title;
    TextView p1_name,p2_name,p3_name,p4_name,p5_name;
    ImageView p1_leader,p2_leader,p3_leader,p4_leader,p5_leader;

    SweetAlertDialog pDialog;
    private SmartMaterialSpinner<String> spProvince;
    private boolean AllowNotification = true;
    private long lastActionTime = 0;
    private List<String> provinceList;

    private Current_status_Data _viewModel;

    public menuSelection(Current_status_Data viewModel){
        _viewModel = viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                BackgroundWatchers backgroundWatchers = new BackgroundWatchers(_viewModel);
                try {
                    backgroundWatchers.StartWatch();
                } catch (JSONException | IOException | NoSuchAlgorithmException |
                         ExecutionException | InterruptedException | KeyManagementException e) {
                    throw new RuntimeException(e);
                }
            }
        });



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

         //scroll view
        textchat = (ScrollView)v.findViewById(R.id.chat_body);

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


         //player titles
        p1_title = (TextView) v.findViewById(R.id.player_1_title);
        p2_title = (TextView) v.findViewById(R.id.player_2_title);
        p3_title = (TextView) v.findViewById(R.id.player_3_title);
        p4_title = (TextView) v.findViewById(R.id.player_4_title);
        p5_title = (TextView) v.findViewById(R.id.player_5_title);

        // Party Leader images
        p1_leader = (ImageView) v.findViewById(R.id.P_1_leader);
        p2_leader = (ImageView) v.findViewById(R.id.P_2_leader);
        p3_leader = (ImageView) v.findViewById(R.id.P_3_leader);
        p4_leader = (ImageView) v.findViewById(R.id.P_4_leader);
        p5_leader = (ImageView) v.findViewById(R.id.P_5_leader);


        ListenersIntiSetup();

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

        server_Switch.setOnStateChangeListener(onStateChangeListener);

        // Text chat
        _viewModel.getPartyChat().observe(requireActivity(),item->{
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(item);
                String NewTextMessage = jsonObject.getString("message");
                String name = jsonObject.getString("name");

                LinearLayout InsiderLinearLayout = new LinearLayout(MainActivity.ContextMethod());
                InsiderLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                InsiderLinearLayout.setLayoutParams(Text_Container);
                TextView Name = new TextView(MainActivity.ContextMethod());
                Name.setLayoutParams(Name_Text);
                Name.setText(name);
                Name.setTextColor(Color.WHITE);

                TextView Body = new TextView(MainActivity.ContextMethod());
                Body.setLayoutParams(Text_Value);
                Body.setText(": "+NewTextMessage);
                Body.setTextColor(Color.WHITE);

                InsiderLinearLayout.addView(Name);
                InsiderLinearLayout.addView(Body);

                linearLayout.addView(InsiderLinearLayout);
                textchat.scrollTo(0, textchat.getBottom());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });

        // drop down menu https://github.com/Chivorns/SmartMaterialSpinner
        spProvince = v.findViewById(R.id.spinner1);
        provinceList = new ArrayList<>();
        for (GameModes gameModes: GameModes.values()){
            provinceList.add(gameModes.getDisplayName());
        }
        spProvince.setItem(provinceList);
        spProvince.setOnItemSelectedListener(GameModeSelectorListener);

        _viewModel.getSelectedItem().observe(requireActivity(), item ->{
            new JsonParseTask().execute(item);
        });

        try {
            new JsonParseTask().execute(OfficalValorantApi.getInstance().GetParty());
        } catch (IOException | ExecutionException | InterruptedException |
                 NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }catch (JSONException e){
            Log.d("Api Json Parse", "JsonParse: "+ e);
        }

        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        System.out.println("Clicked");
        switch (view.getId()){
           case R.id.start_btn:
               if(Start.getText().equals("In Queue")){
                   try {
                       OfficalValorantApi.getInstance().StartStopQ(QeueType.STOPQ);
                   } catch (ExecutionException | InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }else {
                   try {
                       boolean status = OfficalValorantApi.getInstance().StartStopQ(QeueType.STARTQ);
                        if (!status)
                            Toast.makeText(getContext(),"Unable to start Match",Toast.LENGTH_LONG).show();
                   } catch (ExecutionException | InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
                break;
               case R.id.exit_party:
                //_viewModel.for_char("LeaveParty");
                break;
        }
    }
    private class JsonParseTask extends AsyncTask<String, Void, Void> {
        String QueueID;
        String accessibility;
        String state;
        JSONArray players;
        @Override
        protected Void doInBackground(String... strings) {
            String item = strings[0];
            try {
                JSONObject json = new JSONObject(item);
                QueueID = json.getJSONObject("MatchmakingData").getString("QueueID");
                Log.d("UiUpdate", "UiUpdate: " + QueueID);
                Log.d("UiUpdate", "UiUpdate: " + GameModes.getByCodeName(QueueID).ordinal());
//                spProvince.setSelection(GameModes.getByCodeName(QueueID).ordinal());
                players = json.getJSONArray("Members");
                accessibility = json.getString("Accessibility");
                state = json.getString("State");
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println(e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // This method runs on the main thread after doInBackground completes
            // Perform UI updates here
            if (accessibility != null)
                updateAccessibility(accessibility);
            if (state != null)
                updateState(state);
            if(players != null){
                try {
                    updatePlayerUI(players);
                } catch (IOException  | InterruptedException e) {
                    throw new RuntimeException(e);
                }catch (ExecutionException | JSONException e){
                    Log.d("MenuSelection", e.toString());
                }
            }
            // Update the game mode UI
            if (QueueID != null)
                UpdateGameModeUi(QueueID);
        }
    }

    private void updateAccessibility(String accessibility) {
        switch (accessibility) {
            case "CLOSED":
                if (!server_Switch.isChecked()) {
                    Log.d("Toggle Button", "JellyToggle is already False");
                } else if (server_Switch.isChecked()) {
                    server_Switch.setChecked(false);
                }
                break;
            case "OPEN":
                if (server_Switch.isChecked()) {
                    Log.d("Toggle Button", "JellyToggle is already True");
                } else if (!server_Switch.isChecked()) {
                    server_Switch.setChecked(true);
                }
                break;
        }
    }

    private void updateState(String state) {
        switch (state) {
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
                MatchFoundNotification();
                break;
        }
    }

    private void updatePlayerUI(JSONArray players) throws IOException, ExecutionException, InterruptedException, JSONException {
        // Update player UI
        Log.d("MenuSelection", "Players size: "+players.length());
        boolean p1Called = false;
        boolean p2Called = false;
        boolean p3Called = false;
        boolean p4Called = false;
        boolean p5Called = false;

        for (int i = 0; i < players.length(); i++) {
            try {
                JSONObject player = players.getJSONObject(i);
                String player_puid = player.getJSONObject("PlayerIdentity").getString("Subject");
                String player_card = player.getJSONObject("PlayerIdentity").getString("PlayerCardID");
                String player_title = player.getJSONObject("PlayerIdentity").getString("PlayerTitleID");
                boolean isOwner = player.has("IsOwner") && player.getBoolean("IsOwner");

                switch (i) {
                    case 0:
                        Log.d("MenuSelection", "updatePlayerUI: first Player");
                        if (isOwner) {
                            p1_leader.setVisibility(View.VISIBLE);
                        }

                        p1_name.setText(OfficalValorantApi.getInstance().GetNameByPuuid(player_puid));
                        Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetPlayerCard(player_card, true)).into(p1);
                        p1_title.setText(ValorantApi.GetPlayerTitle(player_title));
                        p1Called = true;
                        break;
                    case 1:
                        Log.d("MenuSelection", "updatePlayerUI: Second Player");
                        if (isOwner) {
                            p2_leader.setVisibility(View.VISIBLE);
                        }
                        p2_name.setText(OfficalValorantApi.getInstance().GetNameByPuuid(player_puid));
                        Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetPlayerCard(player_card, false)).into(p2);
                        p2_title.setText(ValorantApi.GetPlayerTitle(player_title));
                        p2Called = true;
                        break;
                    case 2:
                        if (isOwner) {
                            p3_leader.setVisibility(View.VISIBLE);
                        }
                        p3_name.setText(OfficalValorantApi.getInstance().GetNameByPuuid(player_puid));
                        Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetPlayerCard(player_card, false)).into(p3);
                        p3_title.setText(ValorantApi.GetPlayerTitle(player_title));
                        p3Called = true;
                        break;
                    case 3:
                        if (isOwner) {
                            p4_leader.setVisibility(View.VISIBLE);
                        }
                        p4_name.setText(OfficalValorantApi.getInstance().GetNameByPuuid(player_puid));
                        Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetPlayerCard(player_card, false)).into(p4);
                        p4_title.setText(ValorantApi.GetPlayerTitle(player_title));
                        p4Called = true;
                        break;
                    case 4:
                        if (isOwner) {
                            p5_leader.setVisibility(View.VISIBLE);
                        }
                        p5_name.setText(OfficalValorantApi.getInstance().GetNameByPuuid(player_puid));
                        Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetPlayerCard(player_card, false)).into(p5);
                        p5_title.setText(ValorantApi.GetPlayerTitle(player_title));
                        p5Called = true;
                        break;
                    default:
                        Log.d("AgentSelect", "Unknown Amount of agents");
                        break;
                }
            } catch (JSONException e) {
                Log.d("MenuSelection", "JsonException: " + e.toString());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                throw new RuntimeException(e);
            }
        }

        // Check if a case wasn't called and handle default behavior
        if (!p1Called) {
            // Handle default behavior for player 1
            p1_name.setText("");
            p1.setImageResource(R.color.scoreboard);
            p1_title.setText("");
        }
        if (!p2Called) {
            // Handle default behavior for player 2
            p2_name.setText("");
            p2.setImageResource(R.color.scoreboard);
            p2_title.setText("");
        }
        if (!p3Called) {
            // Handle default behavior for player 3
            p3_name.setText("");
            p3.setImageResource(R.color.scoreboard);
            p3_title.setText("");
        }
        if (!p4Called) {
            // Handle default behavior for player 4
            p4_name.setText("");
            p4.setImageResource(R.color.scoreboard);
            p4_title.setText("");
        }
        if (!p5Called) {
            // Handle default behavior for player 5
            p5_name.setText("");
            p5.setImageResource(R.color.scoreboard);
            p5_title.setText("");
        }

    }
    private void UpdateGameModeUi(String QueueID){
        spProvince.setSelection(GameModes.getByCodeName(QueueID).ordinal());
    }



    private void ListenersIntiSetup(){
        onStateChangeListener = new JellyToggleButton.OnStateChangeListener() {
            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
                    try {
                        OfficalValorantApi.getInstance().SetPartyAcc(PartyStatus.CLOSE);
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(state.equals(State.RIGHT)){
                    try {
                        OfficalValorantApi.getInstance().SetPartyAcc(PartyStatus.OPEN);
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        GameModeSelectorListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                try {
                    OfficalValorantApi.getInstance().ChangeQueue(GameModes.values()[position]);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(),"No Item",Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void MatchFoundNotification(){
        if (AllowNotification) {
            // Perform the action here
            new SweetAlertDialog(getContext())
                    .setTitleText("MATCH FOUND")
                    .show();

            Log.d("ActionHandler", "Action performed");

            // Update the flag and record the last action time
            AllowNotification = false;
            lastActionTime = System.currentTimeMillis();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AllowNotification = true;
                    Log.d("ActionHandler", "Action allowed again");
                }
            }, 5000);
        } else {
            // Action not allowed yet
            Log.d("ActionHandler", "Action not allowed yet");
        }
    }
}