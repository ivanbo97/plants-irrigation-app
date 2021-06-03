package com.ivanboyukliev.plantsirrigationsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.EMPTY_FILED_MESSAGE;


public class LoginActivity extends AppCompatActivity {

    private EditText userEmail, userPassword;
    private Button signInBtn;
    private TextView signUpTv;
    private FirebaseAuth firebaseAuth; //object is singleton
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        populateWidgetObjects();

        authStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                Toast.makeText(LoginActivity.this, "You are logged in!", Toast.LENGTH_SHORT).show();
                Intent homeActivityIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(homeActivityIntent);
            } else {
                Toast.makeText(LoginActivity.this, "Please, login!", Toast.LENGTH_SHORT).show();
            }
        };

        signInBtn.setOnClickListener(v -> {
            String email = userEmail.getText().toString();
            String password = userPassword.getText().toString();
            userEmail.setError(null);
            userPassword.setError(null);
            if (email.isEmpty()) {
                userEmail.setError(EMPTY_FILED_MESSAGE);
                return;
            }
            if (password.isEmpty()) {
                userPassword.setError(EMPTY_FILED_MESSAGE);
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
                if (!task.isSuccessful()) {
                    String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            });
        });

        signUpTv.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void populateWidgetObjects() {
        userEmail = findViewById(R.id.emailInputEditTextLoginActivity);
        userPassword = findViewById(R.id.passwordInputEditTextLoginActivity);
        signInBtn = findViewById(R.id.signInButton);
        signUpTv = findViewById(R.id.signInTvLoginActivity);
    }
}