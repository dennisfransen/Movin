package dennisfransen.iths.se.movin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

    private EditText mCompanyName, mCompanyAdress, mCompanyEmail, mCompanyPhoneNumber, mCompanyOrgNumber;
    private CheckBox mCleaning, mMove;
    private Button mFinish;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseFirestore = FirebaseFirestore.getInstance();

        mCompanyName = findViewById(R.id.company_name_et);
        mCompanyAdress = findViewById(R.id.company_adress_et);
        mCompanyEmail = findViewById(R.id.company_email_et);
        mCompanyPhoneNumber = findViewById(R.id.phone_number_et);
        mCompanyOrgNumber = findViewById(R.id.company_org_number_et);
        mCleaning = findViewById(R.id.clean_firm_cb);
        mMove = findViewById(R.id.move_firm_cb);

        // Get email that were used to create account and set in mCompanyEmail Edit Text field.
        // So that when company mUser wont be needed to fill the company email field.
        mCompanyEmail.setText(mUser.getEmail());

        // Pressing finish will add company profile into a unique database id that will be saved in the Firestore database.
        mFinish = findViewById(R.id.finish_btn);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: Check if username is already in use.
                String company_name = mCompanyName.getText().toString();
                String company_address = mCompanyAdress.getText().toString();
                String company_email = mCompanyEmail.getText().toString();
                String company_number = mCompanyPhoneNumber.getText().toString();
                String company_org_number = mCompanyOrgNumber.getText().toString();
                boolean company_cleaning_type = mCleaning.isChecked();
                boolean company_moving_type = mMove.isChecked();

                Map<String, Object> userMap = new HashMap<>();
                userMap.put("company_name", company_name );
                userMap.put("company_address", company_address);
                userMap.put("company_contact_email", company_email);
                userMap.put("company_contact_number", company_number);
                userMap.put("company_org_number", company_org_number);
                userMap.put("company_cleaning_type", company_cleaning_type);
                userMap.put("company_moving_type", company_moving_type);

                // Add Document to database under users collection. Set document id to current mUser id
                firebaseFirestore.collection("company").document(mUser.getUid()).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Welcome the end mUser. Registration complete.
                        Toast.makeText(RegisterActivity.this, "You successfully added your company account, welcome!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Get error message from application and display in toast for end mUser.
                        String error = e.getMessage();
                        Toast.makeText(RegisterActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Send end mUser to MainActivity.
                        Intent gotoMainActivity = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(gotoMainActivity);
                    }
                });
            }
        });

    }
}
