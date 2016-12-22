package com.example.botos.appointment.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.botos.appointment.R;
import com.example.botos.appointment.models.CompanyModel;
import com.example.botos.appointment.ui.activities.userScreens.CompanyDetailsActivity;

import java.util.List;

/**
 * Created by gabibotos on 13/07/16.
 */
public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {

    private List<CompanyModel> mList;
    private Context mContext;

    public CompanyAdapter(Context context, List<CompanyModel> contactList) {
        this.mList = contactList;
        mContext = context;
    }

    public void update(List<CompanyModel> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.company_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CompanyModel rowModel = mList.get(position);

        holder.mTitle.setText(rowModel.getName());
        setDimension(holder);

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent company = new Intent(mContext, CompanyDetailsActivity.class);
                mContext.startActivity(company);
            }
        });
    }

    private void setDimension(ViewHolder holder) {
        float width = mContext.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width / 2), ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.mRoot.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout mRoot;
        protected TextView mTitle;
        protected ImageView mImage;

        public ViewHolder(View v) {
            super(v);
            mRoot =  (LinearLayout) v.findViewById(R.id.companyRowRoot);
            mTitle =  (TextView) v.findViewById(R.id.companyRowTitle);
            mImage = (ImageView) v.findViewById(R.id.companyRowImage);
        }
    }
}