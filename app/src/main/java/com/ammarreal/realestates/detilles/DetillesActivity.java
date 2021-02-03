package com.ammarreal.realestates.detilles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ammarreal.realestates.databinding.ActivityDetillesBinding;
import com.bumptech.glide.Glide;
import com.ammarreal.realestates.R;
import com.ammarreal.realestates.commen.Commen;

import com.ammarreal.realestates.pojo.HomeModel;
import com.ammarreal.realestates.roomdatabase.AppDatabase;
import com.ammarreal.realestates.roomdatabase.RoomAdpter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetillesActivity extends FragmentActivity
        implements OnMapReadyCallback
        , LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private RoomAdpter.Callback mCallback;
    private static final int REQUEST_CALL = 1;
    GoogleApiClient mGoogleApiClient;
    HomeModel list;

    ActivityDetillesBinding binding;
    AppDatabase database;
    RoomAdpter adpter;
    private static String adresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detilles);
        database = AppDatabase.getDatabaseInstance(this);
        adpter = new RoomAdpter(new ArrayList<HomeModel>(), DetillesActivity.this);


        Intent intent = getIntent();
        list = intent.getParcelableExtra("detilles");
        if (list != null)
            adresse = list.getAddresse();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        showDitelise();
        isFavorite(list.getName());

        binding.addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavorite();

            }
        });
        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

    }

    private void addFavorite() {

        if (!database.userDao().findByName(list.getName())) {
            database.userDao().insertUser(list);
            binding.addFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
            StyleableToast.makeText(DetillesActivity.this, getResources().getString(R.string.added), Toast.LENGTH_LONG, R.style.mytoast).show();
        }
    }

    private void isFavorite(String name) {

        if (database.userDao().findByName(name)) {
            binding.addFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
            //  Toast.makeText(this, "is exited", Toast.LENGTH_SHORT).show();
        } else {
            binding.addFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        }
    }

    private void showDitelise() {
        final Typeface typeface = ResourcesCompat.getFont(this, R.font.changa);

        binding.namedet.setText(list.getName());
        binding.Addressdet.setText(getResources().getString(R.string.address) + " :" + list.getAddresse());

        binding.descdet.setText(getResources().getString(R.string.description) + " :" + list.getDesc().toString());
        binding.pricdet.setText(getResources().getString(R.string.price) + " :" + list.getPrice().toString());
        binding.sizedet.setText(getResources().getString(R.string.size) + " :" + list.getSize());
        binding.phonedet.setText(getResources().getString(R.string.phone_number) + " :" + list.getPhone().toString());
        Glide.with(this).load(list.getImage()).into(binding.imageAuthor);
        binding.Addressdet.setTypeface(typeface);
        binding.descdet.setTypeface(typeface);
        binding.pricdet.setTypeface(typeface);
        binding.sizedet.setTypeface(typeface);
        binding.phonedet.setTypeface(typeface);
        binding.namedet.setTypeface(typeface);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);


            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }
        searchLocation(list.getAddresse());

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void searchLocation(String locaion) {
        if (Commen.isNetworkOnline(getApplicationContext())) {
            try {


                List<Address> addressList = null;

                if (locaion != null || !locaion.equals("")) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(locaion, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(locaion));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));


                }
            } catch (Exception e) {
                StyleableToast.makeText(DetillesActivity.this, getResources().getString(R.string.map_address), Toast.LENGTH_LONG, R.style.mytoasterror).show();

            }
        } else {

            StyleableToast.makeText(DetillesActivity.this, getResources().getString(R.string.network), Toast.LENGTH_LONG, R.style.mytoasterror).show();
        }
    }


    private void makePhoneCall() {


        if (ContextCompat.checkSelfPermission(DetillesActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetillesActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + list.getPhone();
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
