package com.fullstack.pmtool.exceptions;

public class ProjectIdExceptionResponse {

    private  String projectIndentifier;

    public ProjectIdExceptionResponse(String projectIndentifier) {
        this.projectIndentifier = projectIndentifier;
    }

    public String getProjectIndentifier() {
        return projectIndentifier;
    }

    public void setProjectIndentifier(String projectIndentifier) {
        this.projectIndentifier = projectIndentifier;
    }
}
