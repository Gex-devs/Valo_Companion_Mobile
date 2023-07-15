package com.gex.gex_riot_take_a_shit.Utils.requestBodies;

import com.google.gson.annotations.SerializedName;

public class AuthCookiesBody {
    @SerializedName("client_id")
    private String clientId;
    private String nonce;
    @SerializedName("redirect_uri")
    private String redirectUri;
    @SerializedName("response_type")
    private String responseType;
    private String scope;

    public AuthCookiesBody() {
        this.clientId = "play-valorant-web-prod";
        this.nonce = "1";
        this.redirectUri = "https://playvalorant.com/opt_in";
        this.responseType = "token id_token";
        this.scope = "account openid";
    }

    // Getters and setters (or use Lombok annotations for convenience)
}
