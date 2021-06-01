package com.ivanboyukliev.plantsirrigationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.INCORRECT_INPUT_MESSAGE;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.REGISTRATION_ERROR_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private EditText userEmail, userPassword;
    private Button signUpBtn;

    private TextView signInTv;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateWidgetObjects();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this,INCORRECT_INPUT_MESSAGE,Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    String errorMesssage = task.getException().getMessage();
                                    Toast.makeText(MainActivity.this,errorMesssage,Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }
                        });
            }
        });

    }


    private void populateWidgetObjects(){
        firebaseAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.emailInputEditTextMainActivity);
        userPassword = findViewById(R.id.passwordInputEditTextMainActivity);
        signUpBtn = findViewById(R.id.signUpButton);
        signInTv = findViewById(R.id.signInTv);
    }


}