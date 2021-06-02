package com.ivanboyukliev.plantsirrigationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputValidator;

import java.util.regex.Pattern;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.EMPTY_FILED_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.INCORRECT_EMAIL_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.INCORRECT_INPUT_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PASSWORD_PATTERN;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.REGISTRATION_ERROR_MESSAGE;
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
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    });
        });

    }

    private void populateWidgetObjects() {
        firebaseAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.emailInputEditTextMainActivity);
        userPassword = findViewById(R.id.passwordInputEditTextMainActivity);
        signUpBtn = findViewById(R.id.signUpButton);
        signInTv = findViewById(R.id.signInTv);
    }


}