package dennisfransen.iths.se.movin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
    }

    private Switch mChangeTheme;
    private Button mSignOut, mChangePassword;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mChangeTheme = view.findViewById(R.id.switch1);
        mChangeTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //TODO:  Changes some theme settings. Not all.
                    getActivity().setTheme(R.style.CustomTheme);
                    Log.i("CHECKED", "IS CHECKED");
                } else {
                    //TODO:  Changes some theme settings. Not all.
                    getActivity().setTheme(R.style.AppTheme);
                    Log.i("CHECKED", "ISN'T CHECKED");
                }
            }
        });

        mSignOut = view.findViewById(R.id.sign_out_btn);
        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        // Change password button. Send end mUser a "Change password" email.
        mChangePassword = view.findViewById(R.id.change_password_btn);
        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(mUser.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Check your email to change your password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMsg = e.getMessage();
                        Toast.makeText(getActivity(), "Error: " + errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
