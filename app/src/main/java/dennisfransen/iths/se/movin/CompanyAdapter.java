package dennisfransen.iths.se.movin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CompanyAdapter extends FirestoreRecyclerAdapter<CompanyModel, CompanyAdapter.CompanyHolder> {

    private FragmentManager mContext;

    private String companyName;

    public CompanyAdapter(@NonNull FirestoreRecyclerOptions<CompanyModel> options, FragmentManager context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final CompanyHolder holder, int position, @NonNull CompanyModel model) {

        holder.companyName.setText(model.getCompany_name());
        holder.companyAddress.setText(model.getCompany_address());
        holder.clean.setChecked(model.isCompany_cleaning_type());
        holder.move.setChecked(model.isCompany_moving_type());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CompanyPageFragment companyPageFragment = new CompanyPageFragment();

                FragmentTransaction fragmentTransaction = mContext.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, companyPageFragment);
                fragmentTransaction.commit();
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CALL", "Call clicked!");
            }
        });


        holder.addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ReviewFragment reviewFragment = new ReviewFragment();

                companyName = holder.companyName.getText().toString();

                Bundle companyData = new Bundle();
                companyData.putString("COMPANY_NAME", companyName);

                reviewFragment.setArguments(companyData);


                FragmentTransaction fragmentTransaction = mContext.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, reviewFragment);
                fragmentTransaction.commit();
            }
        });

    }

    @NonNull
    @Override
    public CompanyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_company, viewGroup, false);
        return new CompanyHolder(view, mContext);
    }

    class CompanyHolder extends RecyclerView.ViewHolder {

        private TextView companyName;
        private TextView companyAddress;
        private CheckBox clean;
        private CheckBox move;
        // private RatingBar starRating;
        private FloatingActionButton call;
        private FloatingActionButton addReview;
        private CardView cardView;


        public CompanyHolder(@NonNull View itemView, FragmentManager context) {
            super(itemView);
            mContext = context;

            companyName = itemView.findViewById(R.id.card_name_tv);
            companyAddress = itemView.findViewById(R.id.card_address_tv);
            clean = itemView.findViewById(R.id.card_clean_cb);
            move = itemView.findViewById(R.id.card_move_cb);
            call = itemView.findViewById(R.id.card_phone_fab);
            addReview = itemView.findViewById(R.id.card_add_review_fab);

            cardView = itemView.findViewById(R.id.company_cv);


        }
    }
}