package com.ailuoku6.golib.Model;

public class Login_State {
    private boolean IsLog;
    private String ERRORINFO;

    public void setLog(boolean log) {
        IsLog = log;
    }

    public void setERRORINFO(String ERRORINFO) {
        this.ERRORINFO = ERRORINFO;
    }

    public boolean getIslog(){
        return IsLog;
    }

    public String getERRORINFO() {
        return ERRORINFO;
    }
}
