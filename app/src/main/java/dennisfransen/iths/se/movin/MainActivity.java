package dennisfransen.iths.se.movin;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_add:
                        Toast.makeText(MainActivity.this, "Action add", Toast.LENGTH_SHORT).show();
                    case R.id.action_edit:
                        Toast.makeText(MainActivity.this, "Action edit", Toast.LENGTH_SHORT).show();
                    case R.id.action_remove:
                        Toast.makeText(MainActivity.this, "Action remove", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}
