package com.gex.gex_riot_take_a_shit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Current_status_Data extends ViewModel {
    private final MutableLiveData<String> Selection_Menu_json = new MutableLiveData<>();
    private final MutableLiveData<String> ChatData = new MutableLiveData<>();
    private final MutableLiveData<String> char_select_item = new MutableLiveData<>();


    public void Selection(String item) {
        Selection_Menu_json.postValue(item);
    }

    public void SetPartyChat(String Text){
        ChatData.postValue(Text);
    }

    public void for_char(String som){
        char_select_item.postValue(som);
    }

    public LiveData<String> getPartyChat(){
        return ChatData;
    }



    public LiveData<String> getSelectedItem() {

        return Selection_Menu_json;
    }

    public LiveData<String> getFor_char() {

        return char_select_item;
    }
}

