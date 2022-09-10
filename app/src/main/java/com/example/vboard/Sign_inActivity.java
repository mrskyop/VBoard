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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_inActivity extends AppCompatActivity {
    private EditText memail , mpassword;
    private TextView textView;
    private Button signinbtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        memail=findViewById(R.id.emailreg);
        mpassword=findViewById(R.id.passwordreg);
        signinbtn=findViewById(R.id.registration_btn);
        textView=findViewById(R.id.textView);

        mAuth = FirebaseAuth.getInstance();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sign_inActivity.this , MainActivity.class));
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }
    private void loginUser(){
        String email =memail.getText().toString();
        String pass = mpassword.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!pass.isEmpty()){
                mAuth.signInWithEmailAndPassword(email,pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(Sign_inActivity.this, "Login Successfully !! ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Sign_inActivity.this , HomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Sign_inActivity.this, "Login Failed !! ", Toast.LENGTH_SHORT).show();
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