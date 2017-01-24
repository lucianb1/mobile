package com.example.botos.appointment.ui.activities.userScreens;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.adapters.DomainAdapter;
import com.example.botos.appointment.adapters.EmployeeAdapter;
import com.example.botos.appointment.models.CompanyModel;
import com.example.botos.appointment.models.MemberModel;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.ui.BaseActivity;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DialogUtils;

import java.util.ArrayList;

public class EmployeesActivity extends BaseActivity {

    private RecyclerView mEmployeeRecyclerView;
    private LinearLayoutManager mGridLayoutManager;
    private ArrayList<MemberModel> mEmployeeModels = new ArrayList<>();
    private EmployeeAdapter mEmployeeAdapter;
    private CompanyModel mCompanyModel;

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
        mCompanyModel = getIntent().getParcelableExtra(CompanyDetailsActivity.COMPANY);
        addtest();
        setAdapter();
//        getMembers();
    }

    private void setAdapter() {
        mGridLayoutManager = new GridLayoutManager(EmployeesActivity.this, 2);
        mEmployeeAdapter = new EmployeeAdapter(EmployeesActivity.this, mEmployeeModels);
        mGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mEmployeeRecyclerView.setHasFixedSize(true);
        mEmployeeRecyclerView.setLayoutManager(mGridLayoutManager);
        mEmployeeRecyclerView.setAdapter(mEmployeeAdapter);
    }

    private void getMembers() {
//        HashMap<String, String> header = new HashMap<>();
//        header.put("Authorization", Engine.getInstance().userModel.getToken());
//        HashMap<String, String> params = new HashMap<>();
//        params.put("", String.valueOf(mDomainModel.getId()));
        final ProgressDialog progreeDialog = DialogUtils.createProgressDialog(EmployeesActivity.this, false, null, getResources().getString(R.string.loading));
        progreeDialog.show();
        ApiLibrary.getRequestMembrers(Constants.BASE_URL + Constants.GET_COMPANIES + "/" + mCompanyModel.getId() + Constants.GET_MEMBERS, null, null, new AppointmentApiResponse<ArrayList<MemberModel>>() {
            @Override
            public void onSuccess(ArrayList<MemberModel> response) {
                mEmployeeModels.clear();
                mEmployeeModels.addAll(response);
                setAdapter();
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(EmployeesActivity.this, error, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
            }
        });
    }

    private void addtest() {
        for (int i = 0; i < 20; i++) {
            MemberModel domainModel = new MemberModel();
            domainModel.setName(String.valueOf(i));
            mEmployeeModels.add(domainModel);
        }
    }

}
