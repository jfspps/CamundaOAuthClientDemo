package com.example.camundaoauthclientdemo.model.camunda.request;

/**
 * Encompasses a given set of "variables" for a request, along with an indication if process variables should be
 * returned in the response (only applies in some cases and is ignored if not applicable)
 */
public class DemoRequests {

    private DemoRequest variables;

    private Boolean withVariablesInReturn;

    public DemoRequests() {
    }

    public DemoRequests(DemoRequest variables, Boolean withVariablesInReturn) {
        this.variables = variables;
        this.withVariablesInReturn = withVariablesInReturn;
    }

    public DemoRequest getVariables() {
        return variables;
    }

    public void setVariables(DemoRequest variables) {
        this.variables = variables;
    }

    public Boolean getWithVariablesInReturn() {
        return withVariablesInReturn;
    }

    public void setWithVariablesInReturn(Boolean withVariablesInReturn) {
        this.withVariablesInReturn = withVariablesInReturn;
    }
}
