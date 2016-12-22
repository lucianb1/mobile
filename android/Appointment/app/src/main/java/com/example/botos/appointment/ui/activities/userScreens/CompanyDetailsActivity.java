package com.example.botos.appointment.ui.activities.userScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.botos.appointment.R;
import com.example.botos.appointment.ui.BaseActivity;

public class CompanyDetailsActivity extends BaseActivity {

    private Button mAppointmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
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
        mAppointmentButton = (Button) findViewById(R.id.companyDetailsAppointmentButton);
    }

    private void load() {
        mAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent employees = new Intent(CompanyDetailsActivity.this, EmployeesActivity.class);
                startActivity(employees);
            }
        });
    }

}
