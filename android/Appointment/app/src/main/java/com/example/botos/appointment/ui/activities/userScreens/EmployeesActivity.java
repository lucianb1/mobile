package com.example.botos.appointment.ui.activities.userScreens;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.botos.appointment.R;
import com.example.botos.appointment.adapters.EmployeeAdapter;
import com.example.botos.appointment.models.EmployeeModel;
import com.example.botos.appointment.ui.BaseActivity;

import java.util.ArrayList;

public class EmployeesActivity extends BaseActivity {

    private RecyclerView mEmployeeRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<EmployeeModel> mEmployeeModels = new ArrayList<>();
    private EmployeeAdapter mEmployeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        setToolbar();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        findId();
        load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void findId() {
        mEmployeeRecyclerView = (RecyclerView) findViewById(R.id.employeesRecyclerView);
    }

    private void load() {
        addtest();
        setAdapter();
    }

    private void setAdapter() {
        mLinearLayoutManager = new LinearLayoutManager(EmployeesActivity.this, LinearLayoutManager.VERTICAL, false);
        mEmployeeAdapter = new EmployeeAdapter(EmployeesActivity.this, mEmployeeModels);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mEmployeeRecyclerView.setHasFixedSize(true);
        mEmployeeRecyclerView.setLayoutManager(mLinearLayoutManager);
        mEmployeeRecyclerView.setAdapter(mEmployeeAdapter);
    }

    private void addtest() {
        for (int i = 0; i < 20; i++) {
            EmployeeModel domainModel = new EmployeeModel();
            domainModel.setName(String.valueOf(i));
            mEmployeeModels.add(domainModel);
        }
    }

}
