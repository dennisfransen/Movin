package dennisfransen.iths.se.movin;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CompanyAdapter extends FirestoreRecyclerAdapter<CompanyModel, CompanyAdapter.CompanyHolder> {

    private FragmentManager mContext;

    public CompanyAdapter(@NonNull FirestoreRecyclerOptions<CompanyModel> options, FragmentManager context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull CompanyHolder holder, int position, @NonNull CompanyModel model) {

        holder.companyName.setText(model.getCompany_name());
        holder.companyOrgNumber.setText(model.getCompany_org_number());
        holder.clean.setChecked(model.isCompany_cleaning_type());
        holder.move.setChecked(model.isCompany_moving_type());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CALL", "Call clicked!");
            }
        });

        holder.addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("REVIEW", "Add review clicked!");
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
        private TextView companyOrgNumber;
        private CheckBox clean;
        private CheckBox move;
        // private RatingBar starRating;
        private FloatingActionButton call;
        private FloatingActionButton addReview;

        public CompanyHolder(@NonNull View itemView, FragmentManager context) {
            super(itemView);
            mContext = context;

            companyName = itemView.findViewById(R.id.card_company_name_tv);
            companyOrgNumber = itemView.findViewById(R.id.card_company_org_number_tv);
            clean = itemView.findViewById(R.id.card_clean_cb);
            move = itemView.findViewById(R.id.card_move_cb);
            call = itemView.findViewById(R.id.card_phone_fab);
            addReview = itemView.findViewById(R.id.card_add_review_fab);


        }
    }
}