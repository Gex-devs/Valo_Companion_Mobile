package com.gex.gex_riot_take_a_shit.enums;

public enum PartyState {
    MATCHMAKING("In Queue"),
    DEFAULT("Lobby"),
    MATCHMADE_GAME_STARTING("Match Found"),
    CUSTOM_GAME_SETUP("Custom Game");

    private String displayName;

    PartyState(String displayName) {
        this.displayName = displayName;
    }

    public String GetDisplayName() {
        return displayName;
    }

}
