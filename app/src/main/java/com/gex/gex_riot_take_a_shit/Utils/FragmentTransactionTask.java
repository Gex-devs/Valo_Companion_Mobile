package com.gex.gex_riot_take_a_shit.Utils;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentTransactionTask {

    private FragmentManager fragmentManager;
    private ViewGroup container;
    private Fragment fragment;

    public FragmentTransactionTask(FragmentManager fragmentManager, ViewGroup container, Fragment fragment) {
        this.fragmentManager = fragmentManager;
        this.container = container;
        this.fragment = fragment;
    }

    public void execute() {
        // Start the fragment transaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Add, remove, or replace the fragment
        transaction.add(container.getId(), fragment);

        // Commit the transaction
        transaction.commitAllowingStateLoss();

        fragmentManager.executePendingTransactions();
    }
}

