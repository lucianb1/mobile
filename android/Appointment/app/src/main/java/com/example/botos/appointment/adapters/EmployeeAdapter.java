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
import com.example.botos.appointment.models.EmployeeModel;
import com.example.botos.appointment.models.MemberModel;
import com.example.botos.appointment.ui.activities.CalendarActivity;
import com.example.botos.appointment.ui.activities.DayScheduleActivity;

import java.util.List;

/**
 * Created by gabibotos on 13/07/16.
 */
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private List<MemberModel> mList;
    private Context mContext;

    public EmployeeAdapter(Context context, List<MemberModel> contactList) {
        this.mList = contactList;
        mContext = context;
    }

    public void update(List<MemberModel> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.employee_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MemberModel rowModel = mList.get(position);

        holder.mTitle.setText(rowModel.getName());

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calendar = new Intent(mContext, DayScheduleActivity.class);
                calendar.putExtra(DayScheduleActivity.MEMBER, rowModel);
                mContext.startActivity(calendar);
            }
        });
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
            mRoot =  (LinearLayout) v.findViewById(R.id.employeeRowRoot);
            mTitle =  (TextView) v.findViewById(R.id.employeeRowName);
            mImage = (ImageView) v.findViewById(R.id.employeeRowImage);
        }
    }
}