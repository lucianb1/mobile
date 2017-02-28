package com.example.botos.appointment.ui.activities.userScreens.fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.botos.appointment.R;
import com.example.botos.appointment.models.CompanyModel;
import com.example.botos.appointment.models.DomainModel;
import com.example.botos.appointment.models.MultiDrawable;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.platform.Engine;
import com.example.botos.appointment.ui.activities.userScreens.CompanyDetailsActivity;
import com.example.botos.appointment.utils.ApiLibrary;
import com.example.botos.appointment.utils.Constants;
import com.example.botos.appointment.utils.DialogUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapCompaniesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapCompaniesFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, ClusterManager.OnClusterClickListener<CompanyModel>, ClusterManager.OnClusterInfoWindowClickListener<CompanyModel>, ClusterManager.OnClusterItemClickListener<CompanyModel>, ClusterManager.OnClusterItemInfoWindowClickListener<CompanyModel> {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View mView;

    private ArrayList<CompanyModel> mCompanyModels = new ArrayList<>();
    private DomainModel mDomainModel;
    CameraPosition mPreviousCameraPosition = null;
    private GoogleApiClient mGoogleApiClient;
    private Double mLati;
    private Double mLong;
    private Location mLastLocation;
//    private MapFragment mMapFragment;
    private GoogleMap mGoogleMap;
    private ClusterManager<CompanyModel> mClusterManager;
    private Random mRandom = new Random(1984);


    public MapCompaniesFragment() {
        // Required empty public constructor
    }

    public static MapCompaniesFragment newInstance(DomainModel param1, ArrayList<CompanyModel> companyModels) {
        MapCompaniesFragment fragment = new MapCompaniesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putParcelableArrayList(ARG_PARAM2, companyModels);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDomainModel = getArguments().getParcelable(ARG_PARAM1);
            mCompanyModels = getArguments().getParcelableArrayList(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null)
                parent.removeView(mView);
        }
        try {
            mView = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
        }
        buildGoogleApiClient();
//        if (mMapFragment != null)
//            mMapFragment.getMapAsync(this);
//        else {
//            mMapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.googleMap);
//            mMapFragment.getMapAsync(this);
//        }

        return mView;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        getLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap != null) {
            return;
        }
        mGoogleMap = googleMap;
        startDemo();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLati = mLastLocation.getLatitude();
            mLong = mLastLocation.getLongitude();
            if (mGoogleMap != null) {
                LatLng current = new LatLng(mLati, mLong);
                mGoogleMap.addMarker(new MarkerOptions().position(current).title("CurrentPlace"));
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 13));
            }
        }
    }

    private class PersonRenderer extends DefaultClusterRenderer<CompanyModel> {
        private final IconGenerator mIconGenerator = new IconGenerator(getActivity().getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getActivity().getApplicationContext());
        private final ImageView mImageView;
        private final ImageView mClusterImageView;
        private final int mDimension;

        public PersonRenderer() {
            super(getActivity().getApplicationContext(), mGoogleMap, mClusterManager);

            View multiProfile = getActivity().getLayoutInflater().inflate(R.layout.multi_profile, null);
            mClusterIconGenerator.setContentView(multiProfile);
            mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

            mImageView = new ImageView(getActivity().getApplicationContext());
            mDimension = (int) getResources().getDimension(R.dimen.custom_profile_image);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);
            mImageView.setPadding(padding, padding, padding, padding);
            mIconGenerator.setContentView(mImageView);
        }

        @Override
        protected void onBeforeClusterItemRendered(CompanyModel companyModel, MarkerOptions markerOptions) {
            // Draw a single person.
            // Set the info window to show their name.
            mImageView.setImageResource(companyModel.getProfilePhoto());
            Bitmap icon = mIconGenerator.makeIcon();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(companyModel.getName());
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<CompanyModel> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
            int width = mDimension;
            int height = mDimension;

            for (CompanyModel p : cluster.getItems()) {
                // Draw 4 at most.
                if (profilePhotos.size() == 4) break;
                Drawable drawable = getResources().getDrawable(p.getProfilePhoto());
                drawable.setBounds(0, 0, width, height);
                profilePhotos.add(drawable);
            }
            MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
            multiDrawable.setBounds(0, 0, width, height);

            mClusterImageView.setImageDrawable(multiDrawable);
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }
    }


    @Override
    public boolean onClusterClick(Cluster<CompanyModel> cluster) {
        String firstName = cluster.getItems().iterator().next().getName();
        Toast.makeText(getActivity(), cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();

        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
        // inside of bounds, then animate to center of the bounds.

        // Create the builder to collect all essential cluster items for the bounds.
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();

        // Animate camera to the bounds
        try {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<CompanyModel> cluster) {

    }

    @Override
    public boolean onClusterItemClick(CompanyModel companyModel) {
        Intent company = new Intent(getActivity(), CompanyDetailsActivity.class);
        company.putExtra(CompanyDetailsActivity.COMPANY, company);
        getActivity().startActivity(company);
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(CompanyModel companyModel) {

    }

    private void startDemo() {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 9.5f));

        mClusterManager = new ClusterManager<>(getActivity(), mGoogleMap);
        mClusterManager.setRenderer(new PersonRenderer());
        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition position = mGoogleMap.getCameraPosition();
                if(mPreviousCameraPosition == null || mPreviousCameraPosition.zoom != position.zoom) {
                    mPreviousCameraPosition = mGoogleMap.getCameraPosition();
                    mClusterManager.cluster();
                }
            }
        });
        mGoogleMap.setOnMarkerClickListener(mClusterManager);
        mGoogleMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        for (int i = 0; i < mCompanyModels.size(); i++) {
            CompanyModel companyModel = mCompanyModels.get(i);
            companyModel.setProfilePhoto(R.drawable.teacher);
            mClusterManager.addItem(companyModel);
        }
        mClusterManager.cluster();
    }

    private void addItems() {
        // http://www.flickr.com/photos/sdasmarchives/5036248203/
        mClusterManager.addItem(new CompanyModel(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683), "Walter", R.drawable.walter));

        // http://www.flickr.com/photos/usnationalarchives/4726917149/
        mClusterManager.addItem(new CompanyModel(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683), "Gran", R.drawable.gran));

        // http://www.flickr.com/photos/nypl/3111525394/
        mClusterManager.addItem(new CompanyModel(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683), "Ruth", R.drawable.ruth));

        // http://www.flickr.com/photos/smithsonian/2887433330/
        mClusterManager.addItem(new CompanyModel(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683), "Stefan", R.drawable.stefan));

        // http://www.flickr.com/photos/library_of_congress/2179915182/
        mClusterManager.addItem(new CompanyModel(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683), "Mechanic", R.drawable.mechanic));

        // http://www.flickr.com/photos/nationalmediamuseum/7893552556/
        mClusterManager.addItem(new CompanyModel(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683), "Yeats", R.drawable.yeats));

        // http://www.flickr.com/photos/sdasmarchives/5036231225/
        mClusterManager.addItem(new CompanyModel(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683), "John", R.drawable.john));

        // http://www.flickr.com/photos/anmm_thecommons/7694202096/
        mClusterManager.addItem(new CompanyModel(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683), "Trevor the Turtle", R.drawable.turtle));

        // http://www.flickr.com/photos/usnationalarchives/4726892651/
        mClusterManager.addItem(new CompanyModel(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683), "Teach", R.drawable.teacher));
    }

    private double random(double min, double max) {
        return mRandom.nextDouble() * (max - min) + min;
    }

    private void getCompanies() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", Engine.getInstance().userModel.getToken());
        HashMap<String, String> params = new HashMap<>();
        params.put("domainID", String.valueOf(mDomainModel.getId()));
        final ProgressDialog progreeDialog = DialogUtils.createProgressDialog(getActivity(), false, null, getResources().getString(R.string.loading));
        progreeDialog.show();
        ApiLibrary.getRequestCompanies(Constants.BASE_URL + Constants.GET_COMPANIES, params, header, new AppointmentApiResponse<ArrayList<CompanyModel>>() {
            @Override
            public void onSuccess(ArrayList<CompanyModel> response) {
//                mCompanyModels.clear();
//                mCompanyModels.addAll(response);


                progreeDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progreeDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                progreeDialog.dismiss();
            }
        });
    }
}
