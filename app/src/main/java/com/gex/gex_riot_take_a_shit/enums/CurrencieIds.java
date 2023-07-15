package com.gex.gex_riot_take_a_shit.enums;

public enum CurrencieIds {
    ValorantPoint("85ad13f7-3d1b-5128-9eb2-7cd8ee0b5741"),
    RadianitePoints("e59aa87c-4cbf-517a-5983-6e81511be9b7"),
    KingdomCredits("85ca954a-41f2-ce94-9b45-8ca3dd39a00d");



    private String Id;
    CurrencieIds(String PUUID){
        this.Id = PUUID;
    }


    public String getID(){
        return Id;
    }

}
