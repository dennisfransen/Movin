package dennisfransen.iths.se.movin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    private TextView mCompanyName, mCompanyAddress, mCompanyOrgNumber, mCompanyPhoneNumber, mCompanyEmail;
    private CheckBox mMove, mClean;
    private ImageButton mChangeCompanyLogo;

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mCompanyName = view.findViewById(R.id.company_name_et);
        mCompanyAddress = view.findViewById(R.id.company_adress_et);
        mCompanyOrgNumber = view.findViewById(R.id.company_org_number_et);
        mCompanyPhoneNumber = view.findViewById(R.id.phone_number_et);
        mCompanyEmail = view.findViewById(R.id.company_email_et);
        mMove = view.findViewById(R.id.move_cb);
        mClean = view.findViewById(R.id.clean_cb);



        mFirestore.collection("company").document(mUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                // OnSuccess, get credentials from database in document field.
                String companyName = documentSnapshot.getString("company_name");
                String companyAddress = documentSnapshot.getString("company_address");
                String companyOrgNumber = documentSnapshot.getString("company_org_number");
                String companyContactNumber = documentSnapshot.getString("company_contact_number");
                String companyContactEmail = documentSnapshot.getString("company_contact_email");

                // Print out company user profile from database
                mCompanyName.append("Company: " + companyName);
                mCompanyAddress.append("Address: " + companyAddress);
                mCompanyOrgNumber.append("Organisation number: " + companyOrgNumber);
                mCompanyPhoneNumber.append("Phone Number: " + companyContactNumber);
                mCompanyEmail.append("E-mail: " + companyContactEmail);

                if (documentSnapshot.getBoolean("company_cleaning_type").equals(true))
                    mClean.setChecked(true);

                if (documentSnapshot.getBoolean("company_moving_type").equals(true))
                    mMove.setChecked(true);

                String id = documentSnapshot.getId();
                Log.i("ID: ", id);
            }
        });


        return view;
    }
}
