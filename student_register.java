package com.example.sgp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class student_register extends AppCompatActivity {
    private EditText userName, userPassword, userEmail;
    private Button regButton, userLogin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        //hideActionBar();
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()){
                    //Upload data to the database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();
                    final ProgressDialog progressDialog = ProgressDialog.show(student_register.this, "Please wait...", "Proccessing...", true);
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                sendEmailVerification();
                            }else{
                                Toast.makeText(student_register.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(student_register.this, Student_activity.class));
            }
        });

    }

    private void setupUIViews(){
        userName = findViewById(R.id.etUserName);
        userPassword = findViewById(R.id.etUserPassword);
        userEmail = findViewById(R.id.etUserEmail);
        regButton = findViewById(R.id.btnRegister);
        userLogin = findViewById(R.id.btnLogin);
    }

    private Boolean validate(){
        Boolean result = false;

        String name = userName.getText().toString();
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }


    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(student_register.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(student_register.this, Student_activity.class));
                    }else{
                        Toast.makeText(student_register.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
