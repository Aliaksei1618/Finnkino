package com.example.finnkino.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finnkino.R;
import com.example.finnkino.adapter.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ShowDate {
    private LocalDateTime date;

    public ShowDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ShowDate))
            return false;
        ShowDate showDate = (ShowDate) obj;
        if (this.date == null)
            return showDate.date == null;
        return this.date.isEqual(showDate.date);
    }

    @Override
    public int hashCode() {
        int result = date.getYear();
        result = 31 * result + date.getMonthValue();
        result = 31 * result + date.getDayOfMonth();
        result = 31 * result + date.getHour();
        result = 31 * result + date.getMinute();
        result = 31 * result + date.getSecond();
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        Context context = Service.getAppContext();
        String pattern = context.getString(R.string.date_time_pattern);
        String language = context.getString(R.string.locale_language);
        String country = context.getString(R.string.locale_country);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, new Locale(language, country));
        return formatter.format(date);
    }
}
