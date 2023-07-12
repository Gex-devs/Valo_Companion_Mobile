package com.gex.gex_riot_take_a_shit.Utils;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gex.gex_riot_take_a_shit.MainActivity;

public class CustomViewConstructor {

    public static LinearLayout ChatTextConstructor(String nameText, String message){

        LinearLayout.LayoutParams Text_Container = new LinearLayout.LayoutParams
                (406, 18, 0.0f);
        LinearLayout.LayoutParams Name_Text = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout.LayoutParams Text_Value = new LinearLayout.LayoutParams
                (152, LinearLayout.LayoutParams.WRAP_CONTENT, 0.0f);

        LinearLayout InsiderLinearLayout = new LinearLayout(MainActivity.ContextMethod());
        InsiderLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        InsiderLinearLayout.setLayoutParams(Text_Container);
        TextView Name = new TextView(MainActivity.ContextMethod());
        Name.setLayoutParams(Name_Text);
        Name.setText(nameText);
        Name.setTextColor(Color.WHITE);

        TextView Body = new TextView(MainActivity.ContextMethod());
        Body.setLayoutParams(Text_Value);
        Body.setText(": "+message);
        Body.setTextColor(Color.WHITE);

        InsiderLinearLayout.addView(Name);
        InsiderLinearLayout.addView(Body);

        return InsiderLinearLayout;

    }
}
