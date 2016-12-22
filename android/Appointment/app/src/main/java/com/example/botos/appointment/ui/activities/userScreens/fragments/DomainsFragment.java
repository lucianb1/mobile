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
import com.example.botos.appointment.adapters.DomainAdapter;
import com.example.botos.appointment.models.DomainModel;

import java.util.ArrayList;

public class DomainsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView mDomainRecycleView;
    private DomainAdapter mDomainAdapter;
    private ArrayList<DomainModel> mDomainModels = new ArrayList<>();


    public DomainsFragment() {

    }
    public static DomainsFragment newInstance(String param1, String param2) {
        DomainsFragment fragment = new DomainsFragment();
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
        View view = inflater.inflate(R.layout.fragment_domains, container, false);
        findId(view);
        load();

        return view;
    }

    private void findId(View view) {
        mDomainRecycleView = (RecyclerView) view.findViewById(R.id.domainRecycleView);
    }

    private void load() {
        addtest();
        setAdapter();
    }

    private void setAdapter() {
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mDomainAdapter = new DomainAdapter(getActivity(), mDomainModels);
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDomainRecycleView.setHasFixedSize(true);
        mDomainRecycleView.setLayoutManager(mGridLayoutManager);
        mDomainRecycleView.setAdapter(mDomainAdapter);
    }

    private void addtest() {
        for (int i = 0; i < 20; i++) {
            DomainModel domainModel = new DomainModel();
            domainModel.setName(String.valueOf(i));
            mDomainModels.add(domainModel);
        }
    }
}
