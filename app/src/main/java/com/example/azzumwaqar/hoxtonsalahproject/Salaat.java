package com.example.azzumwaqar.hoxtonsalahproject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Salaat {
    private String salaatName;
    private Date salaatStartTime;
    private Date salaatJamaatTime;
    private String salatBegin;
    private String salatJamat;

    public Salaat(String salaatName, String salaatStartTime, String salaatJamaatTime) {
        this.salaatName = salaatName;
        this.salatBegin = salaatStartTime;
        this.salatJamat = salaatJamaatTime;
    }

    public String getSalaatName() {
        return salaatName;
    }

    public String getJamaatTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return dateFormat.format(salaatJamaatTime);
    }

    public String getSalaatStartTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return dateFormat.format(salaatStartTime);
    }

    public String getSalatBegin() {
        return salatBegin;
    }

    public String getSalatJamat() {
        return salatJamat;
    }
}
