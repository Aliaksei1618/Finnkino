package com.example.finnkino;


import static com.example.finnkino.model.Finnkino.getInstance;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finnkino.model.Finnkino;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        Intent intent = new Intent(this, MainActivity.class);
        Finnkino finnkino = getInstance();

        ExecutorService executor = Executors.newCachedThreadPool();
        Runnable runnableTheatreAreaList = finnkino::setTheatreAreaList;
        executor.execute(runnableTheatreAreaList);
        executor.execute(finnkino::setEventList);
        //Runnable runnableShowList = finnkino::setShowList;
        //executor.execute(runnableShowList);
        executor.execute(() -> {
            finnkino.setShowList();
                      startActivity(intent);
        });
        //          startActivity(intent);

        executor.shutdown();
    }
}