package com.example.camundaoauthclientdemo.model.camunda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StartProcess {

    private String id;

    private String definitionId;

    private String businessKey;

    private String caseInstanceId;

    private Boolean ended;

    private Boolean suspended;

    private String tenantId;

    public StartProcess() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public void setCaseInstanceId(String caseInstanceId) {
        this.caseInstanceId = caseInstanceId;
    }

    public Boolean getEnded() {
        return ended;
    }

    public void setEnded(Boolean ended) {
        this.ended = ended;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
