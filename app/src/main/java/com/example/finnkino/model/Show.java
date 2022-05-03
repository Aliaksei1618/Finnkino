package com.example.finnkino.model;

import java.time.LocalDateTime;

public class Show {
    private String showID,
            eventID,
            theatreID,
            title,
            theatreAndAuditorium,
            rating,
            spokenLanguage,
            spokenLanguageISO,
            presentationMethod,
            smallImagePortrait,
            subtitleLanguage1,
            subtitleLanguage2;
    private LocalDateTime dtAccounting,
            dttmShowStart;

    public String getShowID() {
        return showID;
    }

    public void setShowID(String showID) {
        this.showID = showID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getTheatreID() {
        return theatreID;
    }

    public void setTheatreID(String theatreID) {
        this.theatreID = theatreID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheatreAndAuditorium() {
        return theatreAndAuditorium;
    }

    public void setTheatreAndAuditorium(String theatreAndAuditorium) {
        this.theatreAndAuditorium = theatreAndAuditorium;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSpokenLanguageISO() {
        return spokenLanguageISO;
    }

    public void setSpokenLanguageISO(String spokenLanguageISO) {
        this.spokenLanguageISO = spokenLanguageISO;
    }

    public String getPresentationMethod() {
        return presentationMethod;
    }

    public void setPresentationMethod(String presentationMethod) {
        this.presentationMethod = presentationMethod;
    }

    public String getSmallImagePortrait() {
        return smallImagePortrait;
    }

    public void setSmallImagePortrait(String smallImagePortrait) {
        this.smallImagePortrait = smallImagePortrait;
    }

    public LocalDateTime getDtAccounting() {
        return dtAccounting;
    }

    public void setDtAccounting(LocalDateTime dtAccounting) {
        this.dtAccounting = dtAccounting;
    }

    public LocalDateTime getDttmShowStart() {
        return dttmShowStart;
    }

    public void setDttmShowStart(LocalDateTime dttmShowStart) {
        this.dttmShowStart = dttmShowStart;
    }

    public String getSpokenLanguage() {
        return spokenLanguage;
    }

    public void setSpokenLanguage(String spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    public String getSubtitleLanguage1() {
        return subtitleLanguage1;
    }

    public void setSubtitleLanguage1(String subtitleLanguage1) {
        this.subtitleLanguage1 = subtitleLanguage1;
    }

    public String getSubtitleLanguage2() {
        return subtitleLanguage2;
    }

    public void setSubtitleLanguage2(String subtitleLanguage2) {
        this.subtitleLanguage2 = subtitleLanguage2;
    }
}
