package com.example.botos.appointment.ui.activities.userScreens.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.botos.appointment.R;
import com.example.botos.appointment.adapters.CompanyAdapter;
import com.example.botos.appointment.models.CompanyModel;

import java.util.ArrayList;

public class CompaniesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mCompanyRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private ArrayList<CompanyModel> mCompanyModels = new ArrayList<>();
    private CompanyAdapter mCompanyAdapter;


    public CompaniesFragment() {
        // Required empty public constructor
    }
    public static CompaniesFragment newInstance(String param1, String param2) {
        CompaniesFragment fragment = new CompaniesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        mCompanyRecyclerView = (RecyclerView) view.findViewById(R.id.companyRecycleView);
    }

    private void load() {
        addtest();
        setAdapter();
    }

    private void setAdapter() {
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mCompanyAdapter = new CompanyAdapter(getActivity(), mCompanyModels);
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCompanyRecyclerView.setHasFixedSize(true);
        mCompanyRecyclerView.setLayoutManager(mGridLayoutManager);
        mCompanyRecyclerView.setAdapter(mCompanyAdapter);
    }

    private void addtest() {
        for (int i = 0; i < 20; i++) {
            CompanyModel domainModel = new CompanyModel();
            domainModel.setName(String.valueOf(i));
            mCompanyModels.add(domainModel);
        }
    }

}
