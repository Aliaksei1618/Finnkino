package com.example.finnkino.UI;

import android.content.Context;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finnkino.R;
import com.example.finnkino.adapter.ScheduleAdapter;
import com.example.finnkino.model.Finnkino;
import com.example.finnkino.model.Show;
import com.example.finnkino.model.ShowDate;
import com.example.finnkino.model.TheatreArea;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleFragment extends Fragment {

    private Context context;
    private View rootView;
    private TheatreArea currentTheatreArea;
    private ShowDate currentShowDate;
    private List<TheatreArea> theatreAreaList;
    private List<Show> AllShowsList;

    private Spinner spinnerTheatreAreas;
    private Spinner spinnerShowDates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        context = container.getContext();

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setExitTransition(transitionInflater.inflateTransition(R.transition.fade));

        currentTheatreArea = new TheatreArea();
        currentShowDate = new ShowDate(LocalDateTime.now());
        spinnerTheatreAreas = rootView.findViewById(R.id.spinner_theatre_areas);
        spinnerShowDates = rootView.findViewById(R.id.spinner_dates);
        theatreAreaList = Finnkino.getInstance().getTheatreAreaList();
        AllShowsList = Finnkino.getInstance().getShowsList();
        setSpinnerTheatreAreas();
        return rootView;
    }

    public void setSpinnerTheatreAreas() {
        ArrayAdapter<TheatreArea> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, theatreAreaList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheatreAreas.setAdapter(adapter);
        spinnerTheatreAreas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentTheatreArea = (TheatreArea) adapterView.getItemAtPosition(i);
                setSpinnerShowDates();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setSpinnerShowDates() {
        ArrayAdapter<ShowDate> dateAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getShowDateList());
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowDates.setAdapter(dateAdapter);
        spinnerShowDates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentShowDate = (ShowDate) adapterView.getItemAtPosition(i);
                setScheduleRecycler();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public List<ShowDate> getShowDateList() {
        List<ShowDate> showDateList = new ArrayList<>();
        String[] theaterId = Finnkino.getInstance().getTheatresIdMap().get(currentTheatreArea.getTheatreAreaID());
        assert theaterId != null;
        for (String s : theaterId) {
            for (Show show : AllShowsList) {
                if (show.getTheatreID().equals(s)) {
                    ShowDate showDate = new ShowDate(show.getDtAccounting());
                    showDateList.add(showDate);
                }
            }
        }
        return showDateList.stream().distinct().collect(Collectors.toList());
    }

    public void setScheduleRecycler() {
        List<Show> showList = getShowList();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        RecyclerView scheduleRecycler = rootView.findViewById(R.id.rv_schedule);
        scheduleRecycler.setLayoutManager(layoutManager);
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(context, showList);
        scheduleRecycler.setAdapter(scheduleAdapter);
    }

    public List<Show> getShowList() {
        List<Show> showList = new ArrayList<>();
        String[] theaterId = Finnkino.getInstance().getTheatresIdMap().get(currentTheatreArea.getTheatreAreaID());
        assert theaterId != null;
        for (String s : theaterId) {
            for (Show show : AllShowsList) {
                if (show.getTheatreID().equals(s) && show.getDtAccounting().equals(currentShowDate.getDate())) {
                    showList.add(show);
                }
            }
        }
        return showList.stream().sorted(Comparator.comparing(Show::getDttmShowStart)).collect(Collectors.toList());
    }
}