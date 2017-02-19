package com.example.botos.appointment.ui.activities.memberScreens;


import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.platform.Engine;
import com.example.botos.appointment.ui.activities.ForgotPasswordActivity;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DialogUtils;
import com.example.botos.appointment.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekPlanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "weekPlan";

    private int NO_WORK = -2;
    private int FREE = 0;
    private int WORKING = 1;
    private int PAUSE = -1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mLastHour = 8;
    private int mLastMinute = 0;
    private SimpleDateFormat mSimpleDateFormat;
    private SimpleDateFormat mSimpleHourFormat;
    private Button mMondayStartButton;
    private Button mMondayEndButton;
    private Button mTuesdayStartButton;
    private Button mTuesdayEndButton;
    private Button mWednesdayStartButton;
    private Button mWednesdayEndButton;
    private Button mThursdayStartButton;
    private Button mThursdayEndButton;
    private Button mFridayStartButton;
    private Button mFridayEndButton;
    private Button mSaturdayStartButton;
    private Button mSaturdayEndButton;
    private Button mSundayStartButton;
    private Button mSundayEndButton;
    private Button mStartWeekButton;
    private Button mEndWeekButton;
    private Button mSaveButton;

    public WeekPlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeekPlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeekPlanFragment newInstance(String param1, String param2) {
        WeekPlanFragment fragment = new WeekPlanFragment();
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
        View view = inflater.inflate(R.layout.fragment_week_plan, container, false);

        findId(view);
        load();

        return view;
    }

    private void findId(View view) {
        mMondayStartButton = (Button) view.findViewById(R.id.mondayStartHourButton);
        mMondayEndButton = (Button) view.findViewById(R.id.mondayEndHourButton);
        mTuesdayStartButton = (Button) view.findViewById(R.id.tuesdayStartHourButton);
        mTuesdayEndButton = (Button) view.findViewById(R.id.tuesdayEndHourButton);
        mWednesdayStartButton = (Button) view.findViewById(R.id.wednesdayStartHourButton);
        mWednesdayEndButton = (Button) view.findViewById(R.id.wednesdayEndHourButton);
        mThursdayStartButton = (Button) view.findViewById(R.id.thursdayStartHourButton);
        mThursdayEndButton = (Button) view.findViewById(R.id.thursdayEndHourButton);
        mFridayStartButton = (Button) view.findViewById(R.id.fridayStartHourButton);
        mFridayEndButton = (Button) view.findViewById(R.id.fridayEndHourButton);
        mSaturdayStartButton = (Button) view.findViewById(R.id.saturdayStartHourButton);
        mSaturdayEndButton = (Button) view.findViewById(R.id.saturdayEndHourButton);
        mSundayStartButton = (Button) view.findViewById(R.id.sundayStartHourButton);
        mSundayEndButton = (Button) view.findViewById(R.id.sundayEndHourButton);
        mStartWeekButton = (Button) view.findViewById(R.id.startWeekButton);
        mEndWeekButton = (Button) view.findViewById(R.id.endWeekButton);
        mSaveButton = (Button) view.findViewById(R.id.saveWeekPlanButton);
    }
    private void load() {
        mSimpleDateFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
        mSimpleHourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        mMondayStartButton.setOnClickListener(startTimeClickListener);
        mMondayEndButton.setOnClickListener(startTimeClickListener);
        mTuesdayStartButton.setOnClickListener(startTimeClickListener);
        mTuesdayEndButton.setOnClickListener(startTimeClickListener);
        mWednesdayStartButton.setOnClickListener(startTimeClickListener);
        mWednesdayEndButton.setOnClickListener(startTimeClickListener);
        mThursdayStartButton.setOnClickListener(startTimeClickListener);
        mThursdayEndButton.setOnClickListener(startTimeClickListener);
        mFridayStartButton.setOnClickListener(startTimeClickListener);
        mFridayEndButton.setOnClickListener(startTimeClickListener);
        mSaturdayStartButton.setOnClickListener(startTimeClickListener);
        mSaturdayEndButton.setOnClickListener(startTimeClickListener);
        mSundayStartButton.setOnClickListener(startTimeClickListener);
        mSundayEndButton.setOnClickListener(startTimeClickListener);
        mStartWeekButton.setOnClickListener(startTimeClickListener);
        mEndWeekButton.setOnClickListener(startTimeClickListener);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[][] matrix = createWeekOrar();
                int[] array = convertMatrixToArray(matrix);
                byte[] bytes = convertToBytes(array);
                String base64 = convertBytesToString(bytes);
                sendOrar(base64);
                Log.d(TAG, "onClick() called with: v = [" + v + "]");
            }
        });
    }

    private View.OnClickListener startTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            final int tag = Integer.valueOf(view.getTag().toString()
            );
            new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    mLastHour = i;
                    mLastMinute = i1;
                    aproxMinutes();
                    Calendar slotCalendar;
                    slotCalendar = Calendar.getInstance();
                    clearCalendarFields(slotCalendar);
                    slotCalendar.set(Calendar.HOUR_OF_DAY, mLastHour);
                    slotCalendar.set(Calendar.MINUTE, mLastMinute);
//                    mActivitySlots.put(tag, slotCalendar);
                    ((Button) view).setText(mSimpleHourFormat.format(slotCalendar.getTime()));
                    slotCalendar.clear();
                    slotCalendar.set(Calendar.HOUR_OF_DAY, i);
                    slotCalendar.set(Calendar.MINUTE, i1);
                }
            }, mLastHour, mLastMinute, true).show();
        }
    };

    private void clearCalendarFields(Calendar calendar) {
        calendar.clear(Calendar.HOUR_OF_DAY);
        calendar.clear(Calendar.HOUR);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
    }

    private void aproxMinutes() {
        if (mLastMinute < 8) {
            mLastMinute = 0;
        } else if (mLastMinute < 23) {
            mLastMinute = 15;
        } else if (mLastMinute < 38) {
            mLastMinute = 30;
        } else if (mLastMinute < 53) {
            mLastMinute = 45;
        } else {
            mLastMinute = 0;
            if (mLastHour == 23) {
                mLastHour = 0;
            } else {
                mLastHour++;
            }
        }
    }

    private int[][] createWeekOrar() {
        int startDayHour = Integer.parseInt(mStartWeekButton.getText().toString().split(":")[0]);
        int startDayMinutes = Integer.parseInt(mStartWeekButton.getText().toString().split(":")[1]);
        int endDayHour = Integer.parseInt(mEndWeekButton.getText().toString().split(":")[0]);
        int endDayMinutes = Integer.parseInt(mEndWeekButton.getText().toString().split(":")[1]);
        int[][] matrix = new int[7][96];

        int startWorkInterval = startDayHour * 4 + getValueFromMinutes(startDayMinutes);
        int endWorkInterval = endDayHour * 4 + getValueFromMinutes(endDayMinutes);

        for (int index = 0; index < 5; index++) {

            for (int i = 0; i < startWorkInterval; i++) {
                matrix[index][i] = NO_WORK;
            }

            int pauseStartHour = Integer.parseInt(getButtonByIndex(index * 2 + 1).getText().toString().split(":")[0]);
            int pauseStartMinutes = Integer.parseInt(getButtonByIndex(index * 2 + 1).getText().toString().split(":")[1]);
            int pauseEndHour = Integer.parseInt(getButtonByIndex(index * 2 + 2).getText().toString().split(":")[0]);
            int pauseEndMinutes = Integer.parseInt(getButtonByIndex(index * 2 + 2).getText().toString().split(":")[1]);

            int startPauseInterval = pauseStartHour * 4 + getValueFromMinutes(pauseStartMinutes);
            int endPauseInterval = pauseEndHour * 4 + getValueFromMinutes(pauseEndMinutes);

            for (int i = startWorkInterval; i < startPauseInterval; i++) {
                matrix[index][i] = FREE;
            }

            for (int i = startPauseInterval; i < endPauseInterval; i++) {
                matrix[index][i] = PAUSE;
            }

            for (int i = endPauseInterval; i < endWorkInterval; i++) {
                matrix[index][i] = FREE;
            }

            for (int i = endWorkInterval; i < 24 * 4; i++) {
                matrix[index][i] = NO_WORK;
            }
        }
        return matrix;
    }

    private int getValueFromMinutes(int minutes) {
        if (minutes == 0) {
            return 0;
        } else if (minutes == 15) {
            return 1;
        } else if (minutes == 30) {
            return 2;
        } else {
            return 3;
        }
    }

    private Button getButtonByIndex(int index) {
        switch (index) {
            case 1:
                return mMondayStartButton;
            case 2:
                return mMondayEndButton;
            case 3:
                return mTuesdayStartButton;
            case 4:
                return mTuesdayEndButton;
            case 5:
                return mWednesdayStartButton;
            case 6:
                return mWednesdayEndButton;
            case 7:
                return mThursdayStartButton;
            case 8:
                return mThursdayEndButton;
            case 9:
                return mFridayStartButton;
            case 10:
                return mFridayEndButton;
            case 11:
                return mSaturdayStartButton;
            case 12:
                return mSaturdayEndButton;
            case 13:
                return mSundayStartButton;
            case 14:
                return mSundayEndButton;
            default:
                break;
        }
        return null;
    }

    private int[] convertMatrixToArray(int[][] arr) {
        int[] vector = new int[arr.length * arr[0].length];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            // tiny change 1: proper dimensions
            for (int j = 0; j < arr[i].length; j++) {
                // tiny change 2: actually store the values
                vector[index] = arr[i][j];
                index++;
            }
        }
        return vector;
    }

    private byte[] convertToBytes(int[] array) {
        byte[] data = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            data[i] = (byte) array[i];

        }
        return data;
    }

    private String convertBytesToString(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    private byte[] convertStringToBytes(String encode) {
        return Base64.decode(encode, Base64.NO_WRAP);
    }

    private void sendOrar(String base64) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", Engine.getInstance().userModel.getToken());
        HashMap<String, String> params = new HashMap<>();
        params.put("timetable", base64);
        final ProgressDialog progreeDialog = DialogUtils.createProgressDialog(getActivity(), false, null, getResources().getString(R.string.loading));
        progreeDialog.show();
        ApiLibrary.postRequestString(Constants.BASE_URL + Constants.TIME_TABLE, params, header, new AppointmentApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getActivity(), R.string.success_request, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure() called with: " + "error = [" + error + "]");
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
            }
        });
    }
}
