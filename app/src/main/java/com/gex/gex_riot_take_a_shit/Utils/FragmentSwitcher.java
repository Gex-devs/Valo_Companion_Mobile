package com.gex.gex_riot_take_a_shit.Utils;

import static com.gex.gex_riot_take_a_shit.MainActivity.viewModel;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.gex.gex_riot_take_a_shit.Game_Status;
import com.gex.gex_riot_take_a_shit.fragments.Riot_login_frag;
import com.gex.gex_riot_take_a_shit.fragments.Store_Fragment;
import com.gex.gex_riot_take_a_shit.fragments.gameFragments.fragment_improved_ingame;
import com.gex.gex_riot_take_a_shit.fragments.gameFragments.improved_Agent_sel_fragment;
import com.gex.gex_riot_take_a_shit.fragments.gameFragments.menuSelection;
import com.gex.gex_riot_take_a_shit.party;


public class FragmentSwitcher {
    
    private static FragmentManager _fragmentManager;
    private static ViewGroup _container;

    private static FragmentSwitcher Instance;



    public FragmentSwitcher(FragmentManager fragmentManager,ViewGroup container){
        _fragmentManager = fragmentManager;
        _container = container;
    }


    public static FragmentSwitcher getInstance(){
        if (Instance != null)
            return Instance;
        return null;
    }


    public static FragmentSwitcher getInstance(FragmentManager fragmentSwitcher,ViewGroup _container){
        if (Instance == null){
            Instance = new FragmentSwitcher(fragmentSwitcher,_container);
            return Instance;
        }
        return Instance;
    }

    public static void Agent_Select_fragment(){
        Fragment fragment = new improved_Agent_sel_fragment();
        new FragmentTransactionTask(_fragmentManager, _container, fragment).execute();
    }
    public static void Qeue_Menu() {
        Fragment fragment = new menuSelection(viewModel);
        new FragmentTransactionTask(_fragmentManager, _container, fragment).execute();
    }
    public static void Game_Status_Fragment(){
        Fragment fragment;
        if (signInChecker.isCookieAlive())
            fragment = new Game_Status();
        else
            fragment = new Riot_login_frag();
        new FragmentTransactionTask(_fragmentManager, _container, fragment).execute();
    }
    public  static void Store_Fragment(){
        Fragment fragment;
        if (signInChecker.isCookieAlive())
            fragment = new Store_Fragment();
        else
            fragment = new Riot_login_frag();

        new FragmentTransactionTask(_fragmentManager, _container, fragment).execute();

    }
    public static void Game_Fragment(){
        Fragment fragment = new fragment_improved_ingame();
        new FragmentTransactionTask(_fragmentManager, _container, fragment).execute();
    }

    public static void party_fragment(){
        Fragment fragment = new party();
        new FragmentTransactionTask(_fragmentManager, _container, fragment).execute();
    }
}
