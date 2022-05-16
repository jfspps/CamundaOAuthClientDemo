package com.example.camundaoauthclientdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaEngineConfig {

    public static final String CAMUNDA_PROCESS_NAME = "Camunda_and_OAuth_demo";

    public static final int TIMEOUT_MS = 6000;

    @Value("${camunda.baseUrl}")
    private String baseUrl;

    public static String BASE_URL;

    @Value("${camunda.baseUrl}")
    public void setBaseUrlStatic(String url){
        CamundaEngineConfig.BASE_URL = url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
