package com.gex.gex_riot_take_a_shit.Utils;

import android.os.AsyncTask;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentTransactionTask extends AsyncTask<Void, Void, Void> {

    private FragmentManager fragmentManager;
    private ViewGroup container;
    private Fragment fragment;

    public FragmentTransactionTask(FragmentManager fragmentManager, ViewGroup container, Fragment fragment) {
        this.fragmentManager = fragmentManager;
        this.container = container;
        this.fragment = fragment;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Start the fragment transaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Add, remove, or replace the fragment
        transaction.add(container.getId(), fragment);

        // Commit the transaction
        transaction.commitAllowingStateLoss();

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // Update the user interface to reflect the changes
    }
}
