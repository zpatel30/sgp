package com.example.sgp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        student = (Button) findViewById(R.id.student);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentlogin();
            }
        });
    }

    public void openStudentlogin(){
        Intent int1 = new Intent(this,Student_activity.class);
        startActivity(int1);
    }
}
