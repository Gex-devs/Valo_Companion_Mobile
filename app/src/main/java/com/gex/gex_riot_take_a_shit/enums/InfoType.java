package com.gex.gex_riot_take_a_shit.enums;

public enum InfoType {
    Chat("chat"),
    Interface("Interfacee");

    private String displayName;

    InfoType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
