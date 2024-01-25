package com.gex.gex_riot_take_a_shit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

public class Current_status_Data extends ViewModel {
    private final MutableLiveData<String> Selection_Menu_json = new MutableLiveData<>();
    private final MutableLiveData<String> ChatData = new MutableLiveData<>();

    private final MutableLiveData<JSONObject> SocialFriendsData = new MutableLiveData<>();

    public void SetSocialFriendsData(JSONObject jsonObject){
        SocialFriendsData.postValue(jsonObject);
    }
    public void Selection(String item) {
        Selection_Menu_json.postValue(item);
    }

    public void SetPartyChat(String Text){
        ChatData.postValue(Text);
    }


    public LiveData<String> getPartyChat(){
        return ChatData;
    }


    public LiveData<String> getSelectedItem() {

        return Selection_Menu_json;
    }

    public LiveData<JSONObject> getSocialFriendsData(){
        return SocialFriendsData;
    }

}

