package dennisfransen.iths.se.movin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton mAbout, mSettings;
    private SearchFragment searchFragment;
    private AboutFragment aboutFragment;
    private SettingsFragment settingsFragment;
    private ProfileFragment profileFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instance of all fragments that are supposed to be view in frame_container
        aboutFragment = new AboutFragment();
        settingsFragment = new SettingsFragment();
        searchFragment = new SearchFragment();

        // Start with SearchFragment.
        profileFragment = new ProfileFragment();
        setFragment(searchFragment);

        mAbout = findViewById(R.id.about_btn);
        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(aboutFragment);
            }
        });

        mSettings = findViewById(R.id.settings_btn);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(settingsFragment);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.search:
                        setFragment(searchFragment);
                        break;
                    case R.id.profile:
                        setFragment(profileFragment);
                        break;
                    case R.id.map:
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
