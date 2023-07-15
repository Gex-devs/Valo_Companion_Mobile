package com.gex.gex_riot_take_a_shit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.FragmentSwitcher;

import org.json.JSONException;

import java.io.IOException;


public class Riot_login_frag extends Fragment {


    private EditText _username;
    private EditText _password;
    private Button _loginButton;
    public Riot_login_frag() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_riot_login, container, false);
        _username = v.findViewById(R.id.username);
        _password = v.findViewById(R.id.password);

        _loginButton = v.findViewById(R.id.login);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("I have been clicked");
                if (!_username.getText().toString().equals("") && !_password.getText().toString().equals("")){
                    Login();
                }else {
                    Toast.makeText(getContext(),"Username or password is empty",Toast.LENGTH_LONG).show();
                }
            }
        });


        return v;
    }

    private void OpenUpTheStore(){
        FragmentSwitcher.Store_Fragment();
    }

    private void Login(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean status = false;

                try {
                    status = OfficalValorantApi.getInstance().AuthToken(_username.getText().toString(),_password.getText().toString());
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }

                if (status){
                    OpenUpTheStore();
                }else{
//                    Toast.makeText(getContext(),"Invalid Username or password",Toast.LENGTH_SHORT).show();
                    System.out.println("Invalid Username or password");
                }
            }
        });

        thread.start();
    }
}