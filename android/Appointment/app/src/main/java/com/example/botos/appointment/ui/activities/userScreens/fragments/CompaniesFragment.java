package com.example.botos.appointment.ui.activities.userScreens.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.models.CompanyModel;
import com.example.botos.appointment.models.DomainModel;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.platform.Engine;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DialogUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class CompaniesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private DomainModel mDomainModel;
    private Button mSwitchViewButton;
    private GridCompaniesFragment mGridFragment;
    private MapCompaniesFragment mMapFragment;
    private boolean mGridViewOn = true;

    public CompaniesFragment() {
        // Required empty public constructor
    }
    public static CompaniesFragment newInstance(DomainModel domainModel) {
        CompaniesFragment fragment = new CompaniesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, domainModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDomainModel = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_companies, container, false);
        findId(view);
        load();

        return view;
    }

    private void findId(View view) {
        mSwitchViewButton = (Button) view.findViewById(R.id.companySwichViewButton);
    }

    private void load() {
        getCompanies();
        mSwitchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGridViewOn) {
                    if (mMapFragment != null) {
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.container, mMapFragment).commit();
                    }
                    mSwitchViewButton.setText(R.string.back_grid_view_button_text);
                    mGridViewOn = false;
                } else {
                    if (mGridFragment != null) {
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.container, mGridFragment).commit();
                    }
                    mSwitchViewButton.setText(R.string.map_view_button_text);
                    mGridViewOn = true;
                }
            }
        });
    }

    private void getCompanies() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", Engine.getInstance().userModel.getToken());
        HashMap<String, String> params = new HashMap<>();
        params.put("domainID", String.valueOf(mDomainModel.getId()));
        final ProgressDialog progreeDialog = DialogUtils.createProgressDialog(getActivity(), false, null, getResources().getString(R.string.loading));
        progreeDialog.show();
        ApiLibrary.getRequestCompanies(Constants.BASE_URL + Constants.GET_COMPANIES, params, header, new AppointmentApiResponse<ArrayList<CompanyModel>>() {
            @Override
            public void onSuccess(ArrayList<CompanyModel> response) {
                mGridFragment = GridCompaniesFragment.newInstance(mDomainModel, response);
                mMapFragment = MapCompaniesFragment.newInstance(mDomainModel, response);
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.container, mGridFragment).commit();
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
            }
        });
    }
}
