package com.gex.gex_riot_take_a_shit.Background;

import com.gex.gex_riot_take_a_shit.Current_status_Data;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.signInChecker;

import org.json.JSONException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class BackgroundWatchers {



    String lastData = "";
    Current_status_Data _viewModel;

    public BackgroundWatchers(Current_status_Data viewModel){

        _viewModel = viewModel;

    }

    public void StartWatch() throws JSONException, IOException, NoSuchAlgorithmException, ExecutionException, InterruptedException, KeyManagementException {

        while (true){

            if (!signInChecker.isCookieAlive() || !OfficalValorantApi.getInstance().isGameRunning()){
                Thread.sleep(5000);
                return;
            }


            String partyData = OfficalValorantApi.getInstance().GetParty();

            if (partyData != lastData){

                lastData = partyData;

                _viewModel.Selection(partyData);

                Thread.sleep(5000);

            }

        }
    }
}
