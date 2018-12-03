package dennisfransen.iths.se.movin;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button mUser, mCompany;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        searchFragment = new SearchFragment();
        mUser = findViewById(R.id.start_user_btn);
        mUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(MainActivity.class);
            }
        });

        mCompany = findViewById(R.id.start_company_btn);
        mCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity(RegisterActivity.class);
            }
        });
    }

    public void setActivity(Class<?> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);
    }

}
