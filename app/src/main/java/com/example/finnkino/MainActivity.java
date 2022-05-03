package com.example.finnkino;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.finnkino.UI.EventFragment;
import com.example.finnkino.UI.ScheduleFragment;
import com.example.finnkino.UI.SettingsFragment;
import com.example.finnkino.adapter.onHelperListener;
import com.example.finnkino.model.Finnkino;
import com.example.finnkino.model.Show;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements onHelperListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        Resources res = this.getBaseContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(Finnkino.getInstance().getCurrentUser().getLanguage()));
        res.updateConfiguration(conf, dm);

        ImageView iv_toolbar_menu = findViewById(R.id.iv_toolbar_menu);
        iv_toolbar_menu.setOnClickListener(view -> setSettingsFragment());
        ImageView toolbar_icon = findViewById(R.id.toolbar_icon);
        toolbar_icon.setOnClickListener(view -> setSheduleFragment());
        setSheduleFragment();
    }

    @Override
    public void setSheduleFragment() {
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
        transaction.replace(R.id.fl_main, scheduleFragment);
        transaction.addToBackStack("scheduleFragment");
        transaction.commit();
    }

    @Override
    public void setEventFragment(Show currentShow) {
        EventFragment eventFragment = new EventFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
        transaction.replace(R.id.fl_main, eventFragment);
        transaction.addToBackStack("eventFragment");
        transaction.commit();
        eventFragment.setCurrentShow(currentShow);
    }


    @Override
    public void setSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
        transaction.replace(R.id.fl_main, settingsFragment);
        transaction.addToBackStack("settingsFragment");
        transaction.commit();
    }
}