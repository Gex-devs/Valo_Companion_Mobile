package com.gex.gex_riot_take_a_shit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class Current_status_Data extends ViewModel {
    private final MutableLiveData<String> Selection_Menu_json = new MutableLiveData<>();
    private final MutableLiveData<String> anotherItem = new MutableLiveData<>();

    public void Selection(String item) {
        Selection_Menu_json.postValue(item);
    }

    public void Game_state(String las){
        //anotherItem.setValue(las);
        anotherItem.postValue(las);
    }

    public LiveData<String> getSelectedItem() {

        return Selection_Menu_json;
    }

    public LiveData<String> getAnotherItem() {

        return anotherItem;
    }
}
