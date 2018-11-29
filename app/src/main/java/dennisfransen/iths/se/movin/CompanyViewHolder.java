package dennisfransen.iths.se.movin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class CompanyViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public CompanyViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
    }

    public void setDetails (Context context, String company_name, String company_address, boolean company_cleaning_type, boolean company_moving_type) {

        // Add text views, buttons, checkboxes, floating action buttons, rating bar.
        TextView mCompanyName = mView.findViewById(R.id.card_name_tv);
        TextView mCompanyAddress = mView.findViewById(R.id.card_address_tv);
        CheckBox mCleanCb = mView.findViewById(R.id.card_clean_cb);
        CheckBox mMoveCb = mView.findViewById(R.id.card_move_cb);

        // Set data to text views, buttons, checkboxes, floating action buttons, rating bar
        mCompanyName.setText(company_name);
        mCompanyAddress.setText(company_address);
        mCleanCb.setChecked(company_cleaning_type);
        mMoveCb.setChecked(company_moving_type);
    }
}