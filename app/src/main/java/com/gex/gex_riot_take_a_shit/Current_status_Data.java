package com.gex.gex_riot_take_a_shit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class Current_status_Data extends ViewModel {
    private final MutableLiveData<String> Selection_Menu_json = new MutableLiveData<>();
    private final MutableLiveData<String> anotherItem = new MutableLiveData<>();
    private final MutableLiveData<String> char_select_item = new MutableLiveData<>();
    private final MutableLiveData<String> IP_Addr = new MutableLiveData<>();
    private final MutableLiveData<String> Port_Addr = new MutableLiveData<>();
    private final MutableLiveData<String> Map_addr = new MutableLiveData<>();
    public void Selection(String item) {
        Selection_Menu_json.postValue(item);
    }

    public void Game_state(String las){
        //anotherItem.setValue(las);
        anotherItem.postValue(las);
    }

    public void for_char(String som){
        char_select_item.postValue(som);
    }

    public void set_map(String map){
        Map_addr.postValue(map);
    }

    public void setIP_Addr(String ip){
        IP_Addr.postValue(ip);
    }

    public void setPort_Addr(String port){
        Port_Addr.postValue(port);
    }

    public LiveData<String> getIP_Addr(){
        return IP_Addr;
    }

    public LiveData<String> get_map(){
        return  Map_addr;
    }
    public LiveData<String> getPort_Addr(){
        return Port_Addr;
    }
    public LiveData<String> getSelectedItem() {

        return Selection_Menu_json;
    }

    public LiveData<String> getAnotherItem() {

        return anotherItem;
    }
    public LiveData<String> getFor_char() {

        return char_select_item;
    }
}

