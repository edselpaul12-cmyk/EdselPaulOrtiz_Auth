package com.example.edselpaultortiz.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth; // import firebase auth
import com.google.firebase.auth.FirebaseUser; // import to get user info

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;
    private TextView tvWelcome;
    private FirebaseAuth mAuth; // declare firebase auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect to firebase
        mAuth = FirebaseAuth.getInstance();

        // link elements
        btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);

        // get current logged in user from firebase
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // show user email on screen
            tvWelcome.setText("Welcome: " + user.getEmail());
        }

        // when logout pressed
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut(); // end firebase sessiontest@gmail.com
                // go back to login screen
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // if no user logged in send back to login
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
}
