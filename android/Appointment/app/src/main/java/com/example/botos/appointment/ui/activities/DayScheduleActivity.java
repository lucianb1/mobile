package com.example.botos.appointment.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.botos.appointment.R;
import com.example.botos.appointment.ui.BaseActivity;
import com.example.botos.appointment.utils.StringUtils;

import java.util.ArrayList;

public class DayScheduleActivity extends BaseActivity {

    private Spinner mOperationSpinner;
    private LinearLayout mMainLayout;
    private ArrayList<Integer> mNumberList = new ArrayList<>();
    private int mUnitHeight;
    private int mUnitNeeded = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_schedule);
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
        mOperationSpinner = (Spinner) findViewById(R.id.scheduleOperationSpinner);
        mMainLayout = (LinearLayout) findViewById(R.id.scheduleMainLayout);
    }

    private void load() {
        mUnitHeight = (int) (20 * getResources().getDisplayMetrics().density);
        addTest();
        setupSpinner();
//        createList();
        createHoursLayout();
        createList();
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DayScheduleActivity.this,
                R.layout.spinner_row, new String[] {"3", "4", "5"});
        adapter.setDropDownViewResource(R.layout.spinner_row);
        mOperationSpinner.setAdapter(adapter);

        mOperationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mUnitNeeded = 3;
                        break;
                    case 1:
                        mUnitNeeded = 4;
                        break;
                    case 2:
                        mUnitNeeded = 5;
                        break;
                    default:
                        break;
                }
                mMainLayout.removeAllViews();
                createHoursLayout();
                createList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createList() {
        LinearLayout rightLayout = new LinearLayout(DayScheduleActivity.this);
        rightLayout.setOrientation(LinearLayout.VERTICAL);
        int i = 0;
        while (i < mNumberList.size()) {

            switch (mNumberList.get(i)) {
                case -1:
                    createUnavailableLayout(rightLayout);
                    i++;
                    break;
                case 0:
                    createBussyLayout(rightLayout);
                    i++;
                    break;
                default:
                    createFreeLayout(rightLayout, mNumberList.get(i));
                    i += mNumberList.get(i);
                    break;
            }
//            if (i % 4 == 0) {
//                rightLayout.addView(createHotizontalLine(Color.BLACK));
//            } else {
//                rightLayout.addView(createHotizontalLine(Color.WHITE));
//            }
        }
        mMainLayout.addView(rightLayout);
    }

    private void createUnavailableLayout(LinearLayout linearLayout) {
        RelativeLayout relativeLayout = new RelativeLayout(DayScheduleActivity.this);
        View view = new View(DayScheduleActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mUnitHeight);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.GRAY);
        relativeLayout.addView(view);
        relativeLayout.addView(createHotizontalLine(Color.BLACK));
        linearLayout.addView(relativeLayout);
    }

    private void createBussyLayout(LinearLayout linearLayout) {
        RelativeLayout relativeLayout = new RelativeLayout(DayScheduleActivity.this);
        View view = new View(DayScheduleActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mUnitHeight);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.RED);
        relativeLayout.addView(view);
        relativeLayout.addView(createHotizontalLine(Color.BLACK));
        linearLayout.addView(relativeLayout);
    }

    private void createFreeLayout(LinearLayout linearLayout, int height) {
        RelativeLayout relativeLayout = new RelativeLayout(DayScheduleActivity.this);
        TextView textView = new TextView(DayScheduleActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mUnitHeight * height);
        textView.setLayoutParams(params);
        if (height >= mUnitNeeded) {
            textView.setBackgroundColor(Color.GREEN);
        } else {
            textView.setBackgroundColor(ContextCompat.getColor(DayScheduleActivity.this, R.color.darker_green));
            textView.setGravity(Gravity.CENTER);
            textView.setText("Insuficient timp");
        }
        relativeLayout.addView(textView);
        relativeLayout.addView(createHotizontalLine(Color.BLACK));
        linearLayout.addView(relativeLayout);
    }

    private void createHoursLayout() {
        LinearLayout leftLayout = new LinearLayout(DayScheduleActivity.this);
        leftLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 6; i < 24; i++) {
            RelativeLayout relativeLayout = new RelativeLayout(DayScheduleActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(4 * mUnitHeight, 4 * mUnitHeight);
            relativeLayout.setLayoutParams(params);
            TextView hourText = new TextView(DayScheduleActivity.this);
            RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(4 * mUnitHeight, 4 * mUnitHeight);
            textParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            hourText.setLayoutParams(textParams);
            hourText.setGravity(Gravity.CENTER);
            hourText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            hourText.setTextColor(Color.WHITE);
            hourText.setText(String.valueOf(i));
            relativeLayout.addView(hourText);
            relativeLayout.addView(createHotizontalLine(Color.BLACK));
            leftLayout.addView(relativeLayout);
        }
        mMainLayout.addView(leftLayout);

        View verticalLine = new View(DayScheduleActivity.this);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams((int) (1 * getResources().getDisplayMetrics().density), ViewGroup.LayoutParams.MATCH_PARENT);
        verticalLine.setLayoutParams(lineParams);
        verticalLine.setBackgroundColor(Color.BLACK);
        mMainLayout.addView(verticalLine);
    }

    private View createHotizontalLine(int color) {
        View horizontalLine = new View(DayScheduleActivity.this);
        RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (1 * getResources().getDisplayMetrics().density));
        lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        horizontalLine.setLayoutParams(lineParams);
        horizontalLine.setBackgroundColor(color);
        return horizontalLine;
    }

    private void addTest() {
        mNumberList.add(2);
        mNumberList.add(1);
        mNumberList.add(0);
        mNumberList.add(5);
        mNumberList.add(4);
        mNumberList.add(3);
        mNumberList.add(2);
        mNumberList.add(1);
        mNumberList.add(-1);
        mNumberList.add(-1);
        mNumberList.add(3);
        mNumberList.add(2);
        mNumberList.add(1);
        mNumberList.add(0);
        mNumberList.add(0);
        mNumberList.add(-1);
        mNumberList.add(-1);
    }
}
