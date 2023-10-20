package com.gex.gex_riot_take_a_shit.enums;

public enum QeueType {
    STARTQ("join"),
    STOPQ("leave");

    private String qeuery;

    QeueType(String qeuery){
        this.qeuery = qeuery;
    }

    public String getQeuery() {
        return qeuery;
    }
}
