package com.gex.gex_riot_take_a_shit.Utils.requestBodies;

public class AuthRequestBody {
    private String type;
    private String username;
    private String password;
    private boolean remember;
    private String language;

    public AuthRequestBody(String username, String password, boolean remember) {
        this.type = "auth";
        this.username = username;
        this.password = password;
        this.remember = remember;
        this.language = "en_US";
    }

    // Getters and setters (or use Lombok annotations for convenience)
}
