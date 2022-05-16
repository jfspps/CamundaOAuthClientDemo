package com.example.camundaoauthclientdemo.model.camunda.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoResponse {

    // initialised at the start of the process and reproduced here
    private ResultField username;

    // initialised as part of the Camunda Process "Camunda_and_OAuth_demo" and only accessible at the end of the process
    private ResultField restricted_resource_variable;

    public DemoResponse() {
    }

    public DemoResponse(ResultField username, ResultField restricted_resource_variable) {
        this.username = username;
        this.restricted_resource_variable = restricted_resource_variable;
    }

    public ResultField getUsername() {
        return username;
    }

    public void setUsername(ResultField username) {
        this.username = username;
    }

    public ResultField getRestricted_resource_variable() {
        return restricted_resource_variable;
    }

    public void setRestricted_resource_variable(ResultField restricted_resource_variable) {
        this.restricted_resource_variable = restricted_resource_variable;
    }
}
