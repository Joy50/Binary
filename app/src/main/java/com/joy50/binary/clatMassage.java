package com.joy50.binary;

import java.util.Date;

/**
 * Created by Asus on 1/6/2018.
 */

public class clatMassage {
    private String massageText;
    private String massageUser;
    private long massageTime;

    public clatMassage(String massageText, String massageUser) {
        this.massageText = massageText;
        this.massageUser = massageUser;
        massageTime=new Date().getTime();
    }

    public clatMassage() {

    }

    public String getMassageText() {
        return massageText;
    }

    public void setMassageText(String massageText) {
        this.massageText = massageText;
    }

    public String getMassageUser() {
        return massageUser;
    }

    public void setMassageUser(String massageUser) {
        this.massageUser = massageUser;
    }

    public long getMassageTime() {
        return massageTime;
    }

    public void setMassageTime(long massageTime) {
        this.massageTime = massageTime;
    }
}
