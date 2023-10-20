package com.gex.gex_riot_take_a_shit.enums;

public enum PartyStatus {
    OPEN("OPEN"),
    CLOSE("CLOSED");

    private String qeuery;

    PartyStatus(String qeuery){
        this.qeuery = qeuery;
    }

    public String getQeuery() {
        return qeuery;
    }

}
