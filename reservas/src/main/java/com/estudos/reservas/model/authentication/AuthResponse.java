package com.estudos.reservas.model.authentication;

public class AuthResponse {
    private final String token;
    private final String type;
    private final long expires_in;

    public AuthResponse(String token, String type, long expires_in) {
        this.token = token;
        this.type = type;
        this.expires_in = expires_in;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public long getExpires_in() {
        return expires_in;
    }
}
