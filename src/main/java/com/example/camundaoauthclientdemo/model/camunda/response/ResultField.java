package com.example.camundaoauthclientdemo.model.camunda.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * An object which mimics the JSON structure of Camunda response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultField {
    private String type;

    private String value;

    private Object valueInfo;

    public ResultField() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getValueInfo() {
        return valueInfo;
    }

    public void setValueInfo(Object valueInfo) {
        this.valueInfo = valueInfo;
    }
}
