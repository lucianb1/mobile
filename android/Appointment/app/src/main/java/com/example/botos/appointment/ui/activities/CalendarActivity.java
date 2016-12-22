package com.example.botos.appointment.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.botos.appointment.R;
import com.example.botos.appointment.ui.BaseActivity;
import com.tripadvisor.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends BaseActivity {

    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setToolbar();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        findId();
        load();

    }

    private void findId() {
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
    }

    private void load() {
        startCalendar();
    }

    private void startCalendar() {
        Date minDate = null;
        Date maxDate = null;
        try {
            minDate = setMinDate();
            maxDate = setMaxDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mCalendarView.init(minDate, maxDate, Locale.ENGLISH, new ArrayList<Date>());
    }

    private Date setMinDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.parse("01/01/2015");
    }

    private Date setMaxDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.parse("01/01/2017");
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

}
