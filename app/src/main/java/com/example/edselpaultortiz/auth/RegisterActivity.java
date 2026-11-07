package com.example.edselpaultortiz.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth; // imports firebase auth for register

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmailReg, etPasswordReg;
    private Button btnRegister;
    private TextView tvGoToLogin;
    private FirebaseAuth mAuth; // declare firebase auth var

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // connect this screen to firebase
        mAuth = FirebaseAuth.getInstance();

        // link layout elements
        etEmailReg = findViewById(R.id.etEmailReg);
        etPasswordReg = findViewById(R.id.etPasswordReg);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);

        // when create account clicked
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailReg.getText().toString().trim();
                String password = etPasswordReg.getText().toString().trim();

                // no email entered
                if (TextUtils.isEmpty(email)) {
                    etEmailReg.setError("Email required");
                    return;
                }
                // password too short or empty
                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    etPasswordReg.setError("Min 6 characters");
                    return;
                }

                // send to firebase make new account
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this,
                                            "Account Created", Toast.LENGTH_SHORT).show();
                                    // account made go to main
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this,
                                            "Registration Failed: " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show(); // show error
                                }
                            }
                        });
            }
        });

        // go back to login
        tvGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
