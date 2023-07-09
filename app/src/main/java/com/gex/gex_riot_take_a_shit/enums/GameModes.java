package com.gex.gex_riot_take_a_shit.enums;

public enum GameModes {
    COMPETITIVE("COMPETITIVE", "competitive"),
    UNRATED("UNRATED", "unrated"),
    DEATH_MATCH("DEATH MATCH", "deathmatch"),
    SPIKE_RUSH("SPIKE RUSH", "spikerush"),
    SWIFT_PLAY("SWIFT PLAY", "swiftplay"),
    ESCALATION("ESCALATION", "ggteam"),
    TEAM_DEATHMATCH("TEAM DEATHMATCH", "hurm");

    private String displayName;
    private String codeName;

    GameModes(String displayName, String codeName) {
        this.displayName = displayName;
        this.codeName = codeName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCodeName() {
        return codeName;
    }

    public static GameModes getByCodeName(String codeName) {
        for (GameModes mode : GameModes.values()) {
            if (mode.getCodeName().equalsIgnoreCase(codeName)) {
                return mode;
            }
        }
        // Default to Comp
        return GameModes.COMPETITIVE;
    }
}
