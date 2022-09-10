package com.example.vboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText memail , mpassword;
    private TextView textView;
    private Button signupbtn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memail = findViewById(R.id.emailreg);
        mpassword=findViewById(R.id.passwordreg);
        textView=findViewById(R.id.textView);
        signupbtn=findViewById(R.id.registration_btn);

        mAuth = FirebaseAuth.getInstance();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , Sign_inActivity.class));
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

    }
    private void createUser(){
        String email =memail.getText().toString();
        String pass = mpassword.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!pass.isEmpty()){
                mAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this, "Register Successfully !!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this ,  Sign_inActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                mpassword.setError("Empty Fields Are Not Allowed" );
            }
        }else if (email.isEmpty()){
            memail.setError("Empty Fields Are Not Allowed");
        }else{
            memail.setError("Please Enter Correct Email");
        }

    }
}