package dennisfransen.iths.se.movin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mSignIn, mRegister;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email_et);
        mPassword = findViewById(R.id.password_et);
        mSignIn = findViewById(R.id.signIn_btn);
        mRegister = findViewById(R.id.register_btn);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };

        // Login to user account button.
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });

        // Register user button.
        mRegister = findViewById(R.id.register_btn);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn() {

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(LoginActivity.this, "Leave no fields empty.", Toast.LENGTH_LONG).show();

        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Wrong email or password. Please try again.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }



    private void registerUser() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            // Email is empty.
            Toast.makeText(LoginActivity.this, "Email field is blank.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            // Password is empty.
            Toast.makeText(LoginActivity.this, "Password field is blank", Toast.LENGTH_LONG).show();
            return;
        }

        // TODO: Check what google have instead of progressDialog.
        // Validation OK. Progress bar is showing.
        // progressDialog.setMessage("Registration in progress ...");
        // progressDialog.show();

        // Adding user to database.
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // User is successfully registered and logged in.
                    Toast.makeText(LoginActivity.this, "Nice, let's complete the registration.", Toast.LENGTH_LONG).show();
                    // Go to Registration Activity to complete registration with user information.
                    Intent gotoRegActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(gotoRegActivity);
                } else {
                    Toast.makeText(LoginActivity.this, "Sorry, registration failed. Please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public EditText getmEmail() {
        return mEmail;
    }

}
