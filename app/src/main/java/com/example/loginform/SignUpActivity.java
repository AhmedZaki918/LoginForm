package com.example.loginform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressWarnings("FieldCanBeLocal")
public class SignUpActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etPhoneNumber;
    private Button btnSignUp;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        etFullName = findViewById(R.id.et_fullname);
        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPhoneNumber = findViewById(R.id.et_phone);
        btnSignUp = findViewById(R.id.btn_sign_up);
        progressBar = findViewById(R.id.progressBar);

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
            finish();
        }


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    etUserName.setError("Username is required");
                    return;

                } else if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password is required");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignUpActivity.this, "Login succeed", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                    progressBar.setVisibility(View.INVISIBLE);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
            }
        });
    }
}



