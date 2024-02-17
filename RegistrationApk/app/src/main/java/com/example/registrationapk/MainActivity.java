package com.example.registrationapk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText inputStudentID, inputStudentName, inputStudentAge, inputStudentAddress, inputStudentEmail;

    private Button btnRegister;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize EditText fields
        inputStudentID = findViewById(R.id.inputStudentId);
        inputStudentName = findViewById(R.id.inputStudentName);
        inputStudentAge = findViewById(R.id.inputStudentAge);
        inputStudentAddress = findViewById(R.id.inputStudentAddress);
        inputStudentEmail = findViewById(R.id.inputStudentEmail);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Students");

        // Initialize register button
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStudent();
            }
        });
    }

    private void registerStudent() {
        // Get student data from EditText fields
        String id = inputStudentID.getText().toString().trim();
        String name = inputStudentName.getText().toString().trim();
        String age = inputStudentAge.getText().toString().trim();
        String address = inputStudentAddress.getText().toString().trim();
        String email = inputStudentEmail.getText().toString().trim();

        // Check if any field is empty
        if (id.isEmpty() || name.isEmpty() || age.isEmpty() || address.isEmpty() || email.isEmpty()) {
            Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Student object with the provided data
        Student student = new Student(name, age, address, email);

        // Store data in Firebase Realtime Database with student ID as the root node
        databaseReference.child(id).setValue(student)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity.this, "Student registered successfully", Toast.LENGTH_SHORT).show();
                    // Clear EditText fields after successful registration
                    inputStudentID.setText("");
                    inputStudentName.setText("");
                    inputStudentAge.setText("");
                    inputStudentAddress.setText("");
                    inputStudentEmail.setText("");
                })
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
