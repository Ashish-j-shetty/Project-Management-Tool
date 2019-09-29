package com.fullstack.pmtool.exceptions;

public class UsernameAlreadyExistsResponse {
    private String username;

    public UsernameAlreadyExistsResponse(String username) {
        this.username = username;
    }

    public String getUserAlreadyExist() {
        return username;
    }

    public void setUserAlreadyExist(String username) {
        this.username = username;
    }
}
