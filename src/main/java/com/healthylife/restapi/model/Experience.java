package com.healthylife.restapi.model;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class Experience {
    private int level, nutritionXP, activityXP, socialXP, ticket;
    private boolean  XPForCalories, XPForProtein, XPForCarbs, XPForFat;

    public Experience(int level, int nutritionXP, int activityXP, int socialXP, int ticket, boolean XPForCalories,
                      boolean XPForProtein, boolean XPForCarbs, boolean XPForFat) {
        this.level = level;
        this.nutritionXP = nutritionXP;
        this.activityXP = activityXP;
        this.socialXP = socialXP;
        this.ticket = ticket;
        this.XPForCalories = XPForCalories;
        this.XPForProtein = XPForProtein;
        this.XPForCarbs = XPForCarbs;
        this.XPForFat = XPForFat;
    }

    public Experience() {
        //må ikke slettes
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNutritionXP() {
        return nutritionXP;
    }

    public void setNutritionXP(int nutritionXP) {
        this.nutritionXP = nutritionXP;
        if (nutritionXP >= 0 && activityXP >= 0 && socialXP >= 0 && level > 0) {
            checkUpdatedX(0);
        }
    }

    public int getActivityXP() {
        return activityXP;
    }

    public void setActivityXP(int activityXP) {
        this.activityXP = activityXP;
        if (nutritionXP >= 0 && activityXP >= 0 && socialXP >= 0 && level > 0) {
            checkUpdatedX(1);
        }
    }

    public int getSocialXP() {
        return socialXP;
    }

    public void setSocialXP(int socialXP) {
        this.socialXP = socialXP;
        if (nutritionXP >= 0 && activityXP >= 0 && socialXP >= 0 && level > 0) {
            checkUpdatedX(2);
        }
    }

    public int getTotalXP() {
        int totalXP = nutritionXP + activityXP + socialXP;
        return totalXP;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public boolean isXPForCalories() {
        return XPForCalories;
    }

    public void setXPForCalories(boolean XPForCalories) {
        this.XPForCalories = XPForCalories;
    }

    public boolean isXPForProtein() {
        return XPForProtein;
    }

    public void setXPForProtein(boolean XPForProtein) {
        this.XPForProtein = XPForProtein;
    }

    public boolean isXPForCarbs() {
        return XPForCarbs;
    }

    public void setXPForCarbs(boolean XPForCarbs) {
        this.XPForCarbs = XPForCarbs;
    }

    public boolean isXPForFat() {
        return XPForFat;
    }

    public void setXPForFat(boolean XPForFat) {
        this.XPForFat = XPForFat;
    }

    public void checkUpdatedX(int xpType) {
//        if (getTotalXP() >= level * 5 + 10) {
//            if (xpType == 0) {
//                Settings.getCurrentPupil().getExperience().setNutritionXP(Settings.getCurrentPupil().getExperience().getTotalXP() % (Settings.getCurrentPupil().getExperience().getLevel() * 5 + 10));
//                Settings.getCurrentPupil().getExperience().setActivityXP(0);
//                Settings.getCurrentPupil().getExperience().setSocialXP(0);
//            }
//            if (xpType == 1) {
//                Settings.getCurrentPupil().getExperience().setActivityXP(Settings.getCurrentPupil().getExperience().getTotalXP() % (Settings.getCurrentPupil().getExperience().getLevel() * 5 + 10));
//                Settings.getCurrentPupil().getExperience().setNutritionXP(0);
//                Settings.getCurrentPupil().getExperience().setSocialXP(0);
//            }
//            if (xpType == 2) {
//                Settings.getCurrentPupil().getExperience().setSocialXP(Settings.getCurrentPupil().getExperience().getTotalXP() % (Settings.getCurrentPupil().getExperience().getLevel() * 5 + 10));
//                Settings.getCurrentPupil().getExperience().setNutritionXP(0);
//                Settings.getCurrentPupil().getExperience().setActivityXP(0);
//            }
//            Settings.getCurrentPupil().getExperience().setLevel(Settings.getCurrentPupil().getExperience().getLevel() + 1);
//            Settings.getCurrentPupil().getExperience().setTicket(Settings.getCurrentPupil().getExperience().getTicket() + 1);
//        }

    }

    public void ResetBooleans(){
    }
}


