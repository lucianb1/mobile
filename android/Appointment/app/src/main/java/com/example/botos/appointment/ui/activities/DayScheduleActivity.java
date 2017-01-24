package com.example.botos.appointment.ui.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.models.MemberModel;
import com.example.botos.appointment.models.ServicesModel;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.platform.Engine;
import com.example.botos.appointment.ui.BaseActivity;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DateUtils;
import com.example.botos.appointment.utils.DialogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DayScheduleActivity extends BaseActivity {

    public static String MEMBER = "member";

    private Calendar mMainCalendar;
    private ImageView mLeftArrow;
    private ImageView mRightArrow;
    private TextView mTodayText;
    private Spinner mOperationSpinner;
    private LinearLayout mMainLayout;
    private int mNormalUnitHeight;
    private int mPopupUnitHeight;
    private int mUnitNeeded = 4;
    private MemberModel mMemberModel;
    private ArrayList<ServicesModel> mServicesModels = new ArrayList<>();
    private String[] mServicesTexts;
    private int[] mTimeTable;

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
        mLeftArrow = (ImageView) findViewById(R.id.scheduleLeftArrow);
        mRightArrow = (ImageView) findViewById(R.id.scheduleRightArrow);
        mTodayText = (TextView) findViewById(R.id.scheduleTodayText);
    }

    private void load() {
        mMemberModel = getIntent().getParcelableExtra(MEMBER);
        mNormalUnitHeight = (int) (20 * getResources().getDisplayMetrics().density);
        mPopupUnitHeight = (int) (50 * getResources().getDisplayMetrics().density);
        mMainCalendar = Calendar.getInstance();
        mTodayText.setText(DateUtils.formatDate(mMainCalendar.getTime(), DateUtils.DATE_FORMATTER_PATTERN));
        mLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainCalendar.add(Calendar.DAY_OF_MONTH, -1);
                mTodayText.setText(DateUtils.formatDate(mMainCalendar.getTime(), DateUtils.DATE_FORMATTER_PATTERN));
            }
        });
        mRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainCalendar.add(Calendar.DAY_OF_MONTH, 1);
                mTodayText.setText(DateUtils.formatDate(mMainCalendar.getTime(), DateUtils.DATE_FORMATTER_PATTERN));
            }
        });
//        getMemmerServices();
        addTest();
        createHoursLayout();
        createList();
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DayScheduleActivity.this,
                R.layout.spinner_row, mServicesTexts);
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

    private RelativeLayout createUnavailableLayout(LinearLayout linearLayout, int height, int unitHeight) {
        RelativeLayout relativeLayout = new RelativeLayout(DayScheduleActivity.this);
        TextView textView = new TextView(DayScheduleActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, unitHeight * height);
        textView.setLayoutParams(params);
        textView.setBackgroundColor(Color.GRAY);
        textView.setGravity(Gravity.CENTER);
        relativeLayout.addView(textView);
        relativeLayout.addView(createHotizontalLine(Color.BLACK));
        linearLayout.addView(relativeLayout);
        return relativeLayout;
    }

    private RelativeLayout createBussyLayout(LinearLayout linearLayout, int height, int unitHeight) {
        RelativeLayout relativeLayout = new RelativeLayout(DayScheduleActivity.this);
        TextView textView = new TextView(DayScheduleActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, unitHeight * height);
        textView.setLayoutParams(params);
        textView.setBackgroundColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        relativeLayout.addView(textView);
        relativeLayout.addView(createHotizontalLine(Color.BLACK));
        linearLayout.addView(relativeLayout);
        return relativeLayout;
    }

    private RelativeLayout createFreeLayout(LinearLayout linearLayout, int height, int unitHeight) {
        RelativeLayout relativeLayout = new RelativeLayout(DayScheduleActivity.this);
        TextView textView = new TextView(DayScheduleActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, unitHeight * height);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
//        if (height >= mUnitNeeded) {
            textView.setBackgroundColor(Color.GREEN);
//        } else {
//            textView.setBackgroundColor(ContextCompat.getColor(DayScheduleActivity.this, R.color.darker_green));
//            textView.setGravity(Gravity.CENTER);
//            textView.setText("Insuficient timp");
//        }
        relativeLayout.addView(textView);
        relativeLayout.addView(createHotizontalLine(Color.BLACK));
        linearLayout.addView(relativeLayout);
        return relativeLayout;
    }

    private void createNotEnoughTimeLayout(LinearLayout linearLayout, int height) {
        RelativeLayout relativeLayout = new RelativeLayout(DayScheduleActivity.this);
        TextView textView = new TextView(DayScheduleActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mNormalUnitHeight * height);
        textView.setLayoutParams(params);
//        if (height >= mUnitNeeded) {
//            textView.setBackgroundColor(Color.GREEN);
//        } else {
            textView.setBackgroundColor(ContextCompat.getColor(DayScheduleActivity.this, R.color.darker_green));
            textView.setGravity(Gravity.CENTER);
            textView.setText("Insuficient timp");
//        }
        relativeLayout.addView(textView);
        relativeLayout.addView(createHotizontalLine(Color.BLACK));
        linearLayout.addView(relativeLayout);
    }

    private void createHoursLayout() {
        LinearLayout leftLayout = new LinearLayout(DayScheduleActivity.this);
        leftLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 6; i < 24; i++) {
            RelativeLayout relativeLayout = new RelativeLayout(DayScheduleActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(4 * mNormalUnitHeight, 4 * mNormalUnitHeight);
            relativeLayout.setLayoutParams(params);
            TextView hourText = new TextView(DayScheduleActivity.this);
            RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(4 * mNormalUnitHeight, 4 * mNormalUnitHeight);
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

    private void getMemmerServices() {
//        HashMap<String, String> header = new HashMap<>();
//        header.put("Authorization", Engine.getInstance().userModel.getToken());
//        HashMap<String, String> params = new HashMap<>();
//        params.put("", String.valueOf(mDomainModel.getId()));
        final ProgressDialog progreeDialog = DialogUtils.createProgressDialog(DayScheduleActivity.this, false, null, getResources().getString(R.string.loading));
        progreeDialog.show();
        ApiLibrary.getRequestMembrerServices(Constants.BASE_URL + Constants.GET_MEMBERS + "/" + mMemberModel.getId() + Constants.GET_SERVICES, null, null, new AppointmentApiResponse<ArrayList<ServicesModel>>() {
            @Override
            public void onSuccess(ArrayList<ServicesModel> response) {
                mServicesModels.clear();
                mServicesModels.addAll(response);
                mServicesTexts = new String[mServicesModels.size()];
                for (int i = 0; i < mServicesModels.size(); i++) {
                    mServicesTexts[i] = mServicesModels.get(i).getName();
                }
                if (mServicesModels.size() <= 1) {
                    setupSpinner();
                    getTimeTable();
                } else {
                    showServicesPopup();
                }
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(DayScheduleActivity.this, error, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
            }
        });
    }

    private void showServicesPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DayScheduleActivity.this);
        builder.setTitle(R.string.select_services_title)
        .setItems(mServicesTexts, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setupSpinner();
                        mOperationSpinner.setSelection(which);
                        getTimeTable();
                    }
                })
        .setCancelable(false);
        builder.create().show();
    }

    private void getTimeTable() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", Engine.getInstance().userModel.getToken());
        HashMap<String, String> params = new HashMap<>();
        params.put("date", DateUtils.formatDate(mMainCalendar.getTime(), DateUtils.SERVER_DATE_FORMATTER_PATTERN));
        final ProgressDialog progreeDialog = DialogUtils.createProgressDialog(DayScheduleActivity.this, false, null, getResources().getString(R.string.loading));
        progreeDialog.show();
        ApiLibrary.getRequestMembrerTimeTable(Constants.BASE_URL + Constants.GET_MEMBERS + "/" + mMemberModel.getId() + Constants.TIME_TABLE, params, header, new AppointmentApiResponse<int[]>() {
            @Override
            public void onSuccess(int[] response) {
                mTimeTable = response;
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(DayScheduleActivity.this, error, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
            }
        });
    }

    private void createList() {
        LinearLayout rightLayout = new LinearLayout(DayScheduleActivity.this);
        rightLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < mTimeTable.length - 24; i++) {
            int count = 1;
            int x = mTimeTable[i];
            for (int j = i + 1; j < mTimeTable.length - 24; j++) {
                int x2 = mTimeTable[j];
                if (x != x2) {
                    break;
                } else {
                    count++;
                }
            }

            switch (x) {
                case -1:
                    createUnavailableLayout(rightLayout, count, mNormalUnitHeight);
                    break;
                case 0:
                    createBussyLayout(rightLayout, count, mNormalUnitHeight);
                    break;
                case 1:
                    if (count >= mUnitNeeded) {
                        for (int j = 0; j < count; j++) {
                            RelativeLayout relativeLayout = createFreeLayout(rightLayout, 1, mNormalUnitHeight);
                            createPopupOnClick(relativeLayout);
                        }
                    } else {
                        createNotEnoughTimeLayout(rightLayout, count);
                    }
                    break;
                default:
                    throw new RuntimeException();
            }
            i += count - 1;
        }
        mMainLayout.addView(rightLayout);
    }

    private void createPopupOnClick(RelativeLayout relativeLayout) {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWithTimeTable();
            }
        });
    }

    public void showPopupWithTimeTable(){
        final Dialog dialog = new Dialog(DayScheduleActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.time_table_popup);

        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.timeTablePopupLinearLayout);
        final ScrollView scrollView = (ScrollView) dialog.findViewById(R.id.timeTablePopupScrollView);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 6);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);

        for (int i = 0; i < mTimeTable.length - 24; i++) {
            switch (mTimeTable[i]) {
                case -1:
                    RelativeLayout unavailableLayout= createUnavailableLayout(linearLayout, 1, mPopupUnitHeight);
                    TextView unavailableText = (TextView) unavailableLayout.getChildAt(0);
                    unavailableText.setText(DateUtils.formatDate(c.getTime(), DateUtils.TIME_DATE_FORMAT));
                    break;
                case 0:
                    RelativeLayout bussyLayout = createBussyLayout(linearLayout, 1, mPopupUnitHeight);
                    TextView bussyText = (TextView) bussyLayout.getChildAt(0);
                    bussyText.setText(DateUtils.formatDate(c.getTime(), DateUtils.TIME_DATE_FORMAT));
                    break;
                case 1:
                    RelativeLayout freeLayout = createFreeLayout(linearLayout, 1, mPopupUnitHeight);
                    TextView freeText = (TextView) freeLayout.getChildAt(0);
                    freeText.setText(DateUtils.formatDate(c.getTime(), DateUtils.TIME_DATE_FORMAT));
                    break;
                default:
                    throw new RuntimeException();
            }
            c.add(Calendar.MINUTE, 15);
        }

//        scrollView.scrollTo(10, 10);
//        .scrollBy(10, 10);
//        scrollView.scrollTo(0, 100);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.scrollTo(500, 500);
            }
        });


        dialog.show();

    }

    private void addTest() {
        mTimeTable = new int[96];
        mTimeTable[0] = 1;
        mTimeTable[1] = 1;
        mTimeTable[2] = 1;
        mTimeTable[3] = 1;
        mTimeTable[4] = -1;
        mTimeTable[5] = 1;
        mTimeTable[6] = 1;
        mTimeTable[7] = 1;
        mTimeTable[8] = -1;
        mTimeTable[9] = -1;
        mTimeTable[10] = 0;
        mTimeTable[11] = 0;
        mTimeTable[12] = 0;
        mTimeTable[13] = 0;
        mTimeTable[14] = 0;
        mTimeTable[15] = 1;
        mTimeTable[16] = 1;
        mTimeTable[17] = 1;
        for (int i = 18; i < 96; i++) {
            mTimeTable[i] = 0;
        }
    }

}
