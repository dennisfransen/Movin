package dennisfransen.iths.se.movin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    private EditText mAboutText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        mAboutText = view.findViewById(R.id.about_text);
        mAboutText.setEnabled(false);



        return view;
    }
}
