package com.gex.gex_riot_take_a_shit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gex.gex_riot_take_a_shit.MainActivity;
import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.FragmentSwitcher;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.github.muddz.styleabletoast.StyleableToast;


public class Riot_login_frag extends Fragment {

    private EditText _username;
    private EditText _password;
    private Button _loginButton;
    private ProgressBar _progressBar;

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
        _progressBar = v.findViewById(R.id.loading);
        _loginButton = v.findViewById(R.id.login);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("I have been clicked");
                _progressBar.setVisibility(View.VISIBLE);
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
        switch (MainActivity.BottomNavIndex){
            case 0:
                FragmentSwitcher.Game_Status_Fragment();
                break;
            case 1:
                FragmentSwitcher.Store_Fragment();
                break;
            case 2:
                Toast.makeText(getActivity(),"Feature is still under development",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void Login(){
                boolean status = false;
                try {
                    status = OfficalValorantApi.getInstance().AuthToken(_username.getText().toString(),_password.getText().toString());
                } catch (IOException | JSONException | ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (status){
                    OpenUpTheStore();
                }else{
                    OfficalValorantApi.getInstance().clearCookies();
                    _progressBar.setVisibility(View.GONE);
                    StyleableToast.makeText(getContext(), "Invalid Username or password", Toast.LENGTH_SHORT, R.style.invalidErrorToast).show();
                }

    }
}
