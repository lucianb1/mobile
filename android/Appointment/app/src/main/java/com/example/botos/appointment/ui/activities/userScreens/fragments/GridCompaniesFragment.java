package com.example.botos.appointment.ui.activities.userScreens.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.adapters.CompanyAdapter;
import com.example.botos.appointment.models.CompanyModel;
import com.example.botos.appointment.models.DomainModel;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.platform.Engine;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DialogUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class GridCompaniesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DomainModel mDomainModel;
    private RecyclerView mCompanyRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private ArrayList<CompanyModel> mCompanyModels = new ArrayList<>();
    private CompanyAdapter mCompanyAdapter;

    public GridCompaniesFragment() {
        // Required empty public constructor
    }

    public static GridCompaniesFragment newInstance(DomainModel domainModel, ArrayList<CompanyModel> companyModels) {
        GridCompaniesFragment fragment = new GridCompaniesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, domainModel);
        args.putParcelableArrayList(ARG_PARAM2, companyModels);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDomainModel = getArguments().getParcelable(ARG_PARAM1);
            mCompanyModels = getArguments().getParcelableArrayList(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid_companies, container, false);
        findId(view);
        load();

        return view;
    }
    private void findId(View view) {
        mCompanyRecyclerView = (RecyclerView) view.findViewById(R.id.companyRecycleView);
    }

    private void load() {
        setAdapter();
        mCompanyAdapter.update(mCompanyModels);
    }

    private void setAdapter() {
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mCompanyAdapter = new CompanyAdapter(getActivity(), mCompanyModels);
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCompanyRecyclerView.setHasFixedSize(true);
        mCompanyRecyclerView.setLayoutManager(mGridLayoutManager);
        mCompanyRecyclerView.setAdapter(mCompanyAdapter);
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
                mCompanyModels.clear();
                mCompanyModels.addAll(response);
                mCompanyAdapter.update(mCompanyModels);
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
