package com.ailuoku6.golib.Model;

public class Notice {
    private String subTitle;
    private String Link;
    private String Date;

    public Notice(String Link,String subTitle,String Date){
        this.subTitle = subTitle;
        this.Link = Link;
        this.Date = Date;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getLink() {
        return Link;
    }

    public String getDate() {
        return Date;
    }
}
