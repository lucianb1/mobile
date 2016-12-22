package com.example.botos.appointment.adapters;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.botos.appointment.R;
import com.example.botos.appointment.models.DomainModel;
import com.example.botos.appointment.ui.BaseActivity;
import com.example.botos.appointment.ui.activities.userScreens.UserMainMenuActivity;
import com.example.botos.appointment.ui.activities.userScreens.fragments.CompaniesFragment;

import java.util.List;

/**
 * Created by gabibotos on 13/07/16.
 */
public class DomainAdapter extends RecyclerView.Adapter<DomainAdapter.ViewHolder> {

    private List<DomainModel> mList;
    private Context mContext;

    public DomainAdapter(Context context, List<DomainModel> contactList) {
        this.mList = contactList;
        mContext = context;
    }

    public void update(List<DomainModel> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.domain_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DomainModel rowModel = mList.get(position);

        holder.mTitle.setText(rowModel.getName());
        setDimension(holder);

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((UserMainMenuActivity)mContext).getFragmentManager();
                CompaniesFragment newFragment = CompaniesFragment.newInstance("", "");
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.container, newFragment,mContext.getResources().getString(R.string.companies_title)).addToBackStack(mContext.getResources().getString(R.string.companies_title)).commit();
                ((BaseActivity)mContext).setTitle(mContext.getString(R.string.companies_title));
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
            mRoot =  (LinearLayout) v.findViewById(R.id.domainRowRoot);
            mTitle =  (TextView) v.findViewById(R.id.domainRowTitle);
            mImage = (ImageView) v.findViewById(R.id.domainRowImage);
        }
    }
}