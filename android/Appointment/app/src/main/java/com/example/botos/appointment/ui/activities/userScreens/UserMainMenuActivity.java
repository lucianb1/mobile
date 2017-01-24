package com.example.botos.appointment.ui.activities.userScreens;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.models.UserModel;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.platform.Engine;
import com.example.botos.appointment.ui.BaseActivity;
import com.example.botos.appointment.ui.activities.SigninActivity;
import com.example.botos.appointment.ui.activities.userScreens.fragments.DomainsFragment;
//import com.example.botos.appointment.ui.activities.userScreens.fragments.MapCompaniesFragment;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DialogUtils;

import java.sql.SQLException;
import java.util.HashMap;

public class UserMainMenuActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "userMainMenu";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        setToolbar();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        findId();
        load();
    }

    private void findId() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, getToolBar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void load() {
        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        setDefaultScreen();
        checkPermission();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.user_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDefaultScreen() {
        FragmentManager fragmentManager = getFragmentManager();
        DomainsFragment newFragment = DomainsFragment.newInstance("", "");
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, newFragment,getResources().getString(R.string.domains_title)).addToBackStack(getResources().getString(R.string.domains_title)).commit();
        setTitle(getString(R.string.domains_title));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            logoutRequest();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            int count = getFragmentManager().getBackStackEntryCount();

            if(count > 1) {
//                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//                FragmentManager fragmentManager = getFragmentManager();
//                Fragment newFragment = null;
//                newFragment = HomeFragment.newInstance();
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//
//                if (newFragment != null) {
//                    ft.replace(R.id.container, newFragment).commit();
//                }
                setTitle(getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 2).getName());
                getFragmentManager().popBackStack();

                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
            }
            else {
                Log.d(TAG, "onKeyDown() called with: keyCode = [" + keyCode + "], event = [" + event + "]");
                finish();
                //showAlert();
                //exit app
            }
        }
        return false;
    }

    private void logoutRequest() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", Engine.getInstance().userModel.getToken());
        final ProgressDialog progreeDialog = DialogUtils.createProgressDialog(UserMainMenuActivity.this, false, null, getResources().getString(R.string.loading));
        progreeDialog.show();
        ApiLibrary.postRequestString(Constants.BASE_URL + Constants.LOGOUT, null, header, new AppointmentApiResponse<String>() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(UserMainMenuActivity.this, R.string.success_request, Toast.LENGTH_SHORT).show();
                Engine.getInstance().userModel.setIsLoging(false);
                addToDataBase(Engine.getInstance().userModel);
                Engine.getInstance().userModel = null;
                progreeDialog.dismiss();
                Intent main = new Intent(UserMainMenuActivity.this, SigninActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                finish();
            }

            @Override
            public void onFailure() {
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(UserMainMenuActivity.this, error, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
            }
        });
    }

    public void addToDataBase(UserModel userModel) {
        try {
            getHelper().getUserModelDao().createOrUpdate(userModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkPermission() {
        ActivityCompat.requestPermissions(UserMainMenuActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(UserMainMenuActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
