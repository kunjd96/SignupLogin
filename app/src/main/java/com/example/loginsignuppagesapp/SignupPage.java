package com.example.loginsignuppagesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity implements View.OnClickListener {
    EditText firstname,lastname,email,password;
    Button signup;
    TextView gobacktologin;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        firstname = findViewById(R.id.firstnameEdittext);
        lastname = findViewById(R.id.lastnameEdittext);
        email = findViewById(R.id.emailEdittext);
        password = findViewById(R.id.paswordEdittext);
        signup = findViewById(R.id.signupButton);
        gobacktologin = findViewById(R.id.gobacktologin);
        signup.setOnClickListener(this);
        gobacktologin.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserData");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signupButton:
                doSignup();
                break;
            case R.id.gobacktologin:
                gobacktologinpage();
                break;
        }
    }

    private void gobacktologinpage() {
        Intent homepage = new Intent(SignupPage.this,MainActivity.class);
        homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homepage);
        finish();
    }

    private void doSignup() {
        if(fieldsValidator()){
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(),"Please check yor verify your Email",Toast.LENGTH_LONG).show();
                                            FirebaseUser user = firebaseAuth.getCurrentUser();
                                            String userid = user.getUid();
                                            AddUser(userid);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"Wrong Email",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    private void AddUser(String userid) {
        UserDataPojo newUser = new UserDataPojo(firstname.getText().toString(),lastname.getText().toString(),email.getText().toString(),userid);
        databaseReference.child(userid).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(getApplicationContext(),"Sindup Done",Toast.LENGTH_LONG).show();
                Intent i = new Intent(SignupPage.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }

    private boolean fieldsValidator() {
        if (firstname.getText().toString().trim().isEmpty()) {
            firstname.setError("Enter First Name");
        } else if (lastname.getText().toString().trim().isEmpty()) {
            lastname.setError("Enter Last Name");
        } else if (email.getText().toString().trim().isEmpty()) {
            email.setError("Enter Email");
        } else if (!emailvalidate(email.getText().toString().trim())) {
            email.setError("Enter Email in valid formate");
        } else if (password.getText().toString().trim().isEmpty()) {
            password.setError("Enter Password");
        }else {
            return true;
        }
        return false;
    }

    private boolean emailvalidate(String empty) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (empty.matches(emailPattern))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}