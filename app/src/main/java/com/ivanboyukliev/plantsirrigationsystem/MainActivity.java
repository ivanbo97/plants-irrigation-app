package com.ivanboyukliev.plantsirrigationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputValidator;

import java.util.Objects;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.INCORRECT_EMAIL_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.WEAK_PASSWORD_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private EditText userEmail, userPassword;
    private Button signUpBtn;
    private TextView signInTv;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        populateWidgetObjects();

        signUpBtn.setOnClickListener(v -> {

            String email = userEmail.getText().toString();
            String password = userPassword.getText().toString();

            if (!UserInputValidator.isEmailValid(email)) {
                userEmail.setError(INCORRECT_EMAIL_MESSAGE);
                return;
            }
            if (!UserInputValidator.isPasswordValid(password)) {
                userPassword.setError(WEAK_PASSWORD_MESSAGE);
                return;
            }
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(MainActivity.this, task -> {
                        if (!task.isSuccessful()) {
                            String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    });
        });

        signInTv.setOnClickListener(v -> {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

    }

    private void populateWidgetObjects() {

        userEmail = findViewById(R.id.emailInputEditTextMainActivity);
        userPassword = findViewById(R.id.passwordInputEditTextMainActivity);
        signUpBtn = findViewById(R.id.signUpButton);
        signInTv = findViewById(R.id.signInTv);
    }
}