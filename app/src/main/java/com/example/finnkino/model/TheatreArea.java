package com.example.finnkino.model;

import androidx.annotation.NonNull;

import com.example.finnkino.R;
import com.example.finnkino.adapter.Service;

public class TheatreArea {
    private static final String DEFAULT_THEATRE_AREA_ID = "1029";
    private static final String DEFAULT_THEATRE_AREA_NAME = Service.getAppContext().getString(R.string.default_theater_name);
    private String theatreAreaID;
    private String theatreAreaName;

    public TheatreArea() {
        this.theatreAreaID = DEFAULT_THEATRE_AREA_ID;
        this.theatreAreaName = DEFAULT_THEATRE_AREA_NAME;
    }

    public String getTheatreAreaID() {
        return theatreAreaID;
    }

    public void setTheatreAreaID(String theatreAreaID) {
        this.theatreAreaID = theatreAreaID;
    }

    public void setTheatreAreaName(String theatreAreaName) {
        if (theatreAreaName.equals("Valitse alue/teatteri")) {
            this.theatreAreaName = DEFAULT_THEATRE_AREA_NAME;
        } else {
            this.theatreAreaName = theatreAreaName;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return theatreAreaName;
    }
}


