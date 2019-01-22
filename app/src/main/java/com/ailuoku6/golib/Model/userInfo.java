package com.ailuoku6.golib.Model;

public class userInfo {
    public static String userName;
    private String prifileStart;
    private String PrifileEnd;
    private String fiveDay_out;
    private String out_dateBook;
    private String maxBookNum;
    private String userType;
    private String borrowTime;
    private String breakrulesTime;
    private String debt;

//    public userInfo(String userName, String prifileStart, String prifileEnd, String fiveDay_out, String out_dateBook, String maxBookNum, String userType, String borrowTime, String breakrulesTime, String debt) {
//        this.userName = userName;
//        this.prifileStart = prifileStart;
//        PrifileEnd = prifileEnd;
//        this.fiveDay_out = fiveDay_out;
//        this.out_dateBook = out_dateBook;
//        this.maxBookNum = maxBookNum;
//        this.userType = userType;
//        this.borrowTime = borrowTime;
//        this.breakrulesTime = breakrulesTime;
//        this.debt = debt;
//    }

    public userInfo(){}

    public String getUserName() {
        return userName;
    }

    public String getPrifileStart() {
        return prifileStart;
    }

    public String getPrifileEnd() {
        return PrifileEnd;
    }

    public String getFiveDay_out() {
        return fiveDay_out;
    }

    public String getOut_dateBook() {
        return out_dateBook;
    }

    public String getMaxBookNum() {
        return maxBookNum;
    }

    public String getUserType() {
        return userType;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public String getBreakrulesTime() {
        return breakrulesTime;
    }

    public String getDebt() {
        return debt;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPrifileStart(String prifileStart) {
        this.prifileStart = prifileStart;
    }

    public void setPrifileEnd(String prifileEnd) {
        PrifileEnd = prifileEnd;
    }

    public void setFiveDay_out(String fiveDay_out) {
        this.fiveDay_out = fiveDay_out;
    }

    public void setOut_dateBook(String out_dateBook) {
        this.out_dateBook = out_dateBook;
    }

    public void setMaxBookNum(String maxBookNum) {
        this.maxBookNum = maxBookNum;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public void setBreakrulesTime(String breakrulesTime) {
        this.breakrulesTime = breakrulesTime;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

}
