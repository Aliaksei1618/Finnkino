package com.example.finnkino;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finnkino.model.Finnkino;
import com.example.finnkino.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail;
    private EditText etPassword;
    private Button btSignIn;
    private Button btRegister;
    private Context context;
    private Intent intent;
    private Finnkino finnkino;
    private User currentUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btSignIn = findViewById(R.id.bt_sign_in);
        btRegister = findViewById(R.id.bt_register);
        context = this.getBaseContext();
        intent = new Intent(context, MainActivity.class);
        finnkino = Finnkino.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        btSignIn.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(finnkino::setTheatreAreaList);
        executor.execute(finnkino::setEventList);
        executor.execute(finnkino::setShowList);
        executor.shutdown();
    }

    @Override
    public void onClick(View view) {
        if (view == btSignIn) {
            signing(etEmail.getText().toString(), etPassword.getText().toString());
        } else if (view == btRegister) {
            registration(etEmail.getText().toString(), etPassword.getText().toString());
        }
    }

    public void signing(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, getString(R.string.successful_login), Toast.LENGTH_SHORT).show();
                    getUserFromFirebase(firebaseAuth.getCurrentUser().getUid());
                } else {
                    Toast.makeText(context, getString(R.string.unsuccessful_login), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registration(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, getString(R.string.successful_registration), Toast.LENGTH_SHORT).show();
                setUserToFirebase();
                startActivity(intent);
            } else {
                Toast.makeText(context, getString(R.string.unsuccessful_registration), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUserToFirebase() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        finnkino.setCurrentUser(new User(Objects.requireNonNull(firebaseUser.getEmail()), firebaseUser.getUid()));
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users").child(firebaseUser.getUid());
        currentUser = finnkino.getCurrentUser();
        databaseReference.setValue(currentUser);
    }

    public void getUserFromFirebase(String uID) {
        firebaseDatabase.getReference().child("users").child(uID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        currentUser = dataSnapshot.getValue(User.class);
                        finnkino.setCurrentUser(currentUser);
                        System.out.println(finnkino.getCurrentUser().getLanguage());
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}