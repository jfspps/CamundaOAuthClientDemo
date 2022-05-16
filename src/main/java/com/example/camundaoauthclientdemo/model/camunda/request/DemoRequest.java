package com.example.camundaoauthclientdemo.model.camunda.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoRequest {

    private Value username;

    public DemoRequest() {
    }

    public DemoRequest(Value username) {
        this.username = username;
    }

    public Value getUsername() {
        return username;
    }

    public void setUsername(Value username) {
        this.username = username;
    }
}
