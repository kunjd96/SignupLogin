package com.example.loginsignuppagesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity implements View.OnClickListener {
    TextView email;
    Button logout;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        email = findViewById(R.id.hometext);
        logout = findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String id = intent.getStringExtra("Emailid");
        if(id != null){
            email.setText(id);
        }
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:
                doLogout();
                break;
        }
    }

    private void doLogout() {
        firebaseAuth.signOut();
        Intent i = new Intent(HomePage.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}