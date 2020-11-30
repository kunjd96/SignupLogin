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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signuppage;
    EditText email,password;
    Button login;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signuppage = findViewById(R.id.signupgobtn);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        signuppage.setOnClickListener(this);
        login.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signupgobtn:
                Intent signupPage = new Intent(MainActivity.this,SignupPage.class);
                startActivity(signupPage);
                break;
            case R.id.loginbtn:
                dologin();
                break;
        }
    }

    private void dologin() {
        if(filedsValidator()){
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                    Intent homepage = new Intent(MainActivity.this,HomePage.class);
                                    homepage.putExtra("Emailid",email.getText().toString());
                                    homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(homepage);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Email is not verified",Toast.LENGTH_LONG).show();
                                }


                            } else {
                                Toast.makeText(getApplicationContext(),"Wrong passsword",Toast.LENGTH_LONG).show();
                            }

                            // ...
                        }
                    });
        }
    }

    private boolean filedsValidator() {
        if (email.getText().toString().trim().isEmpty()) {
            email.setError("Enter Email");
        } else if (password.getText().toString().trim().isEmpty()) {
            password.setError("Enter Password");
        } else {
            return true;
        }
        return false;
    }
}