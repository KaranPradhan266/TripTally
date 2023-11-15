package com.example.final_group_expenser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    EditText email,pass;
    Button register;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email =findViewById(R.id.remail);
        pass  = findViewById(R.id.rpass);
        register = findViewById(R.id.registerbtn);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_pass = pass.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass)){
                    Toast.makeText(register.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                }else if(txt_pass.length() < 6){
                    Toast.makeText(register.this, "Password to short", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(txt_email,txt_pass);
                }
            }
        });
    }

    private void registerUser(String email, String pass) {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(register.this, "User Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(register.this,dashboard.class));
                    finish();
                }
                else{
                    Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}