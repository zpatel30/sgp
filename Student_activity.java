package com.example.sgp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Student_activity extends AppCompatActivity {

    private EditText name;
    private EditText pass;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_activity);
        name = findViewById(R.id.etName);
        pass = findViewById(R.id.etPass);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    public void btn_Login(View i) {
        if (!pass.getText().toString().equalsIgnoreCase("")) {
            final ProgressDialog progressDialog = ProgressDialog.show(Student_activity.this, "Please wait...", "Proccessing...", true);

            (firebaseAuth.signInWithEmailAndPassword(name.getText().toString(), pass.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                // Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                checkEmailVerification();
                            } else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(Student_activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

    }


    public void btn_Register(View v) {
        finish();
        startActivity(new Intent(Student_activity.this, student_register.class));
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(Student_activity.this, Menu.class));
        if(emailflag) {
            finish();
            startActivity(new Intent(Student_activity.this, Menu.class));
        }else{
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }


}
