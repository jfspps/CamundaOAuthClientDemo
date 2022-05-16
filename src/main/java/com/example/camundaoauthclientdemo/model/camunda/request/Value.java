package com.example.camundaoauthclientdemo.model.camunda.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Each process variable in a request is defined as:
 * {"variables":
 *     {
 *         "process_variable_name": {"value": "Xmas0000"}
 *     },
 *     "withVariablesInReturn": true
 * }
 *
 * so Value (here) represents "value" from the request
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {

    private String value;

    public Value() {
    }

    public Value(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
