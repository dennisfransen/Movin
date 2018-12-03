package dennisfransen.iths.se.movin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
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
    protected void onBindViewHolder(@NonNull final CompanyHolder holder, final int position, @NonNull final CompanyModel model) {

        holder.companyName.setText(model.getCompany_name());
        holder.companyAddress.setText(model.getCompany_address());
        holder.clean.setChecked(model.isCompany_cleaning_type());
        holder.move.setChecked(model.isCompany_moving_type());
        holder.avgReviewScore.setRating(model.getReview_star_rating());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CompanyPageFragment companyPageFragment = new CompanyPageFragment();

                companyName = holder.companyName.getText().toString();

                Bundle data = new Bundle();

                data.putString("COMPANY_NAME", companyName);

                companyPageFragment.setArguments(data);

                //Transaction to RestaurantFragment on card click
                FragmentTransaction fragmentTransaction = mContext.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, companyPageFragment);
                fragmentTransaction.commit();

            }
        });

        holder.website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyWebsite = model.getCompany_website();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(companyWebsite));
                v.getContext().startActivity(intent);
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyNumber = model.getCompany_contact_number();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + companyNumber));
                v.getContext().startActivity(intent);
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

        private TextView companyName, companyAddress;
        private CheckBox clean, move;
        private FloatingActionButton call, addReview, website;
        private RatingBar avgReviewScore;

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
            website = itemView.findViewById(R.id.card_website_fab);
            avgReviewScore = itemView.findViewById(R.id.card_star_rating_rb);

            cardView = itemView.findViewById(R.id.company_cv);


        }
    }
}