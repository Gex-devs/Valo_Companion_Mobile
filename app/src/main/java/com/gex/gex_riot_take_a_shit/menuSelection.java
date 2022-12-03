package com.gex.gex_riot_take_a_shit;

import static com.gex.gex_riot_take_a_shit.MainActivity.viewModel;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class menuSelection extends Fragment implements View.OnClickListener {

    Button Comp,DM,Unrated,Start,Send;
    LinearLayout inner_layout;
    EditText body_value;
    private SmartMaterialSpinner<String> spProvince;
    private SmartMaterialSpinner<String> spEmptyItem;
    private List<String> provinceList;

    public menuSelection() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_menu_selection, container, false);

         Comp = (Button) v.findViewById(R.id.comp_btn);
         Comp.setOnClickListener(this);
         DM = (Button) v.findViewById(R.id.dm_btn);
         DM.setOnClickListener(this);
         Unrated = (Button) v.findViewById(R.id.unrated_btn);
         Unrated.setOnClickListener(this);
         Start = (Button) v.findViewById(R.id.start_btn);
         Start.setOnClickListener(this);
         Send = (Button) v.findViewById(R.id.button_gchat_send);
         Send.setOnClickListener(this);

         body_value = (EditText) v.findViewById(R.id.edit_gchat_message);

        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.inner_kill_feed);

        LinearLayout.LayoutParams Text_Container = new LinearLayout.LayoutParams
                (getResources().getDimensionPixelSize(R.dimen.text_chat_widtg), getResources().getDimensionPixelSize(R.dimen.text_View_padding), 0.0f);

        LinearLayout.LayoutParams Name_Text = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout.LayoutParams Text_Value = new LinearLayout.LayoutParams
                (R.dimen.inner_layout_text_view_width_small, LinearLayout.LayoutParams.WRAP_CONTENT, 0.0f);

        viewModel = new ViewModelProvider(requireActivity()).get(Current_status_Data.class);
        viewModel.getSelectedItem().observe(requireActivity(),item ->{
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




            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println(e);
            }
        });


        // drop down menu https://github.com/Chivorns/SmartMaterialSpinner
        spProvince = v.findViewById(R.id.spinner1);
        //spEmptyItem = v.findViewById(R.id.sp_empty_item);
        provinceList = new ArrayList<>();

        provinceList.add("COMPETITIVE");
        provinceList.add("UNRATED");
        provinceList.add("DEATH MATCH");
        provinceList.add("SPIKE RUSH");
        provinceList.add("ESCALATION");

        spProvince.setItem(provinceList);
        spProvince.setItemListBackground(R.color.Valo_Color);
        // this how u select item spProvince.setSelection(0);

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toasty.info(MainActivity.ContextMethod(), provinceList.get(position), Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toasty.info(MainActivity.ContextMethod(), "nothing selected", Toast.LENGTH_SHORT, true).show();
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
                viewModel.for_char("chat:"+body_value.getText());
                Toasty.info(MainActivity.ContextMethod(), "Gex:Text Sent", Toast.LENGTH_SHORT, true).show();
                body_value.setText("");
                break;
            case R.id.dm_btn:
                viewModel.for_char("changeQ:deathmatch");
                break;
            case R.id.unrated_btn:
                viewModel.for_char("changeQ:unrated");
                break;
            case R.id.comp_btn:
                viewModel.for_char("changeQ:competitive");
                break;
            case R.id.start_btn:
                viewModel.for_char("startQ");
                break;
        }


    }
}