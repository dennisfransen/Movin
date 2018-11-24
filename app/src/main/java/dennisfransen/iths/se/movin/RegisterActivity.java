package dennisfransen.iths.se.movin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    private EditText mFirstName, mLastName, mPhoneNumber;
    private Button mFinish;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseFirestore = FirebaseFirestore.getInstance();

        mFirstName = findViewById(R.id.first_name_et);
        mLastName = findViewById(R.id.last_name_et);
        mPhoneNumber = findViewById(R.id.phone_number_et);

        // Pressing finish will add username, firstname, lastname, phonenumber to an uniqe database id that will be saved in the firestore database.
        mFinish = findViewById(R.id.finish_btn);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: Check if username is already in use.
                String firstName = mFirstName.getText().toString();
                String lastName = mLastName.getText().toString();
                String phoneNumber = mPhoneNumber.getText().toString();

                Map<String, String> userMap = new HashMap<>();
                userMap.put("firstname", firstName);
                userMap.put("lastname", lastName);
                userMap.put("phonenumber", phoneNumber);

                // Add Document to database under users collection. Set document id to current user id
                firebaseFirestore.collection("users").document(user.getUid()).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Welcome the end user. Registration complete.
                        Toast.makeText(RegisterActivity.this, "You successfully added your account, welcome!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Get error message from application and display in toast for end user.
                        String error = e.getMessage();
                        Toast.makeText(RegisterActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Send end user to MainActivity.
                        Intent gotoMainActivity = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(gotoMainActivity);
                    }
                });
            }
        });

    }
}
