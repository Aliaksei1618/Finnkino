package com.example.finnkino.UI;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finnkino.R;
import com.example.finnkino.adapter.ScheduleAdapter;
import com.example.finnkino.adapter.onHelperListener;
import com.example.finnkino.model.Event;
import com.example.finnkino.model.Finnkino;
import com.example.finnkino.model.Show;
import com.example.finnkino.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class SettingsFragment extends Fragment {

    private Context context;
    private View rootView;
    private boolean flag;
    private boolean flag2;
    private Spinner spinnerLanguage;
    private Spinner spinnerMyMovies;
    private RecyclerView scheduleRecycler;
    private ScheduleAdapter scheduleAdapter;
    private FirebaseAuth mAuth;
    private EditText etUserName;
    private User currentUser;
    private List<Event> eventList;
    private List<Show> showList;
    onHelperListener helperListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        context = container.getContext();
        currentUser = Finnkino.getInstance().getCurrentUser();
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setExitTransition(transitionInflater.inflateTransition(R.transition.fade));

        spinnerLanguage = rootView.findViewById(R.id.sp_language);
        spinnerMyMovies = rootView.findViewById(R.id.sp_my_movies);
        eventList = Finnkino.getInstance().getEventList();
        showList = Finnkino.getInstance().getShowsList();
        helperListener = (onHelperListener) context;
        setSpinnerLanguage();
        setSpinnerMyMovies();
        etUserName = rootView.findViewById(R.id.et_user_name);
        etUserName.setText(currentUser.getName());

        etUserName.setOnKeyListener((view, i, keyEvent) -> {
            if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                String userName = etUserName.getText().toString();
                currentUser.setName(userName);
                return true;
            }
            return false;
        });
        return rootView;
    }

    private void setSpinnerMyMovies() {

        List<String> eventTitlteList = new ArrayList<>();
        for (String userEventId : currentUser.getUserEventsList()) {
            for (Show show : showList) {
                if (userEventId.equals(show.getEventID())) {
                    eventTitlteList.add(show.getTitle());
                }
            }
        }
        List<String> sortedEventTitlteList = eventTitlteList.stream().distinct().collect(Collectors.toList());


        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, sortedEventTitlteList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMyMovies.setAdapter(adapter);

        flag2 = false;
        spinnerMyMovies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (flag2) {

                for (Show show : showList) {
                    if (show.getTitle().equals(adapterView.getItemAtPosition(i))) {
                        helperListener.setEventFragment(show);
                        return;
                    }
                }
            }
                flag2 = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


       // spinnerMyMovies.setOnClickListener(view -> flag2 = true);
    }


    public void setSpinnerLanguage() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);

        flag = false;
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String language = (String) adapterView.getItemAtPosition(i);
                if (flag) {
                    if ("suomi".equals(language)) {
                        updateLanguage("fi");
                    }
                    if ("english".equals(language)) {
                        updateLanguage("en");
                    }
                }
                flag = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void updateLanguage(String language) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(language.toLowerCase()));
        res.updateConfiguration(conf, dm);
        onHelperListener helperListener = (onHelperListener) context;
        helperListener.setSettingsFragment();
        currentUser.setLanguage(language);
        FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUserId()).child("language").setValue(language);
    }


}