package com.example.botos.appointment.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.platform.Engine;
import com.example.botos.appointment.ui.BaseActivity;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DialogUtils;

import java.util.HashMap;

public class UserMainMenuActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            logoutRequest();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}