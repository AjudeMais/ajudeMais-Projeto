package br.edu.ifpb.ajudemais.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.dto.LatLng;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;
import br.edu.ifpb.ajudemais.util.FacebookAccount;
import br.edu.ifpb.ajudemais.utils.CapturePhotoUtils;
import br.edu.ifpb.ajudemais.utils.ImagePicker;

/**
 * <p>
 * <b>AbstractActivity</b>
 * </p>
 * <p>
 * Activity para controlar tele inicial de carregamento do aplicativo.
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public class AbstractActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ImageView profilePhoto;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private CapturePhotoUtils capturePhotoUtils;
    private static final int PICK_IMAGE_ID = 234;
    private TextView tvUserName;
    private TextView tvEmail;
    private Conta conta = new Conta();
    protected SharedPrefManager sharedPrefManager;

    protected GoogleApiClient mGoogleApiClient;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";
    protected LocationManager locationManager;
    protected Location mLastLocation;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    protected Context mContext;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // no minimo 10m de deslocamento pra atualizar
    private static final long MIN_TIME_BW_UPDATES = 0; //1000 * 60 * 1; // 1 minuto no minimo


    /**
     *
     */
    protected void init() {
        capturePhotoUtils = new CapturePhotoUtils(this);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mNavigationView = (NavigationView) findViewById(R.id.menuNav);
    }

    /**
     * Recupera a localização do device
     *
     * @return
     */
    protected Location getLocation() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation == null) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                if (locationManager != null) {
                    mLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else {
                    Log.e("LOCATION", "----nNULLLLLLLLLLLLLLLLLLLLLLLLLLL");
                }
            }

        }


        return mLastLocation;
    }


    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item);

    }

    /**
     * Set Action Bar na tela de exibição.
     */
    protected void setUpToggle() {
        setSupportActionBar(mToolbar);


        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Set as informações do usuário logado no app
     */
    protected void setUpAccount() {
        View hView = mNavigationView.getHeaderView(0);
        profilePhoto = (ImageView) hView.findViewById(R.id.photoProfile);
        tvUserName = (TextView) hView.findViewById(R.id.tvUserNameProfile);
        tvEmail = (TextView) hView.findViewById(R.id.tvEmailProfile);

        conta = (Conta) getIntent().getSerializableExtra("Conta");

        if (conta != null ) {
            tvUserName.setText(conta.getUsername() != null ? conta.getUsername() : Profile.getCurrentProfile().getName());
            tvEmail.setText(conta.getEmail());
        }
        Bitmap bitmap = capturePhotoUtils.loadImageFromStorage();

        if (bitmap != null) {
            profilePhoto.setImageBitmap(bitmap);
        }

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(getApplicationContext());
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
            }
        });
    }

    /**
     * Set Configuração para Navegation Drawer
     */
    protected void setupNavDrawer() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (mNavigationView != null && mDrawerLayout != null) {

                mNavigationView.setNavigationItemSelectedListener(
                        new NavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(MenuItem menuItem) {
                                menuItem.setChecked(true);
                                mDrawerLayout.closeDrawers();
                                onNavDrawerItemSelected(menuItem);
                                return true;
                            }
                        });
            }
        }
    }

    /**
     * @param menuItem
     */
    private void onNavDrawerItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_config_conta:
                break;
            case R.id.nav_notificacoes:
                break;
            case R.id.nav_sair:
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                    goToLoginScreen();
                    break;
                } else {
                    SharedPrefManager.getInstance(this).clearSharedPrefs();
                    System.out.println(capturePhotoUtils.deleteImageProfile());
                    goToLoginScreen();
                    break;
                }
        }
    }

    private void goToLoginScreen() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /**
     * Inicia serviço Google API client
     */
    protected void initGoogleAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(AbstractActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    /**
     * Checa se o device pertence ao SDK versão 23 para exibir permissão
     */
    protected void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(AbstractActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();

    }

    /**
     * Exibi dialog para ligar GPS no device.
     */
    private void showSettingDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        onLocationChanged(getLocation());
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            status.startResolutionForResult(AbstractActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }


    /**
     * Requisita permissão para acessar GPS do device.
     */
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(AbstractActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(AbstractActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(AbstractActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }


    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));
    }

    /**
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gpsLocationReceiver != null)
            unregisterReceiver(gpsLocationReceiver);
    }

    /**
     *
     */
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            showSettingDialog();
        }
    };

    /**
     * Verifica se GPS está ativado, se não pede para ligar
     */
    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                onLocationChanged(getLocation());

            } else {
                new Handler().postDelayed(sendUpdatesToUI, 10);
                Log.e("About GPS", "GPS is Disabled in your device");
            }

        }

    };


    /**
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE_ID) {
                if (data.getExtras() == null) {
                    Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    profilePhoto.setImageBitmap(bitmap);
                    capturePhotoUtils.saveToInternalStorage(bitmap);
                } else {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    profilePhoto.setImageBitmap(photo);
                    capturePhotoUtils.saveToInternalStorage(photo);

                }
            }
        }
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_INTENT_ID: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (mGoogleApiClient == null) {
                        initGoogleAPIClient();
                        showSettingDialog();
                    } else
                        showSettingDialog();


                } else {
                    Toast.makeText(AbstractActivity.this, getString(R.string.permissionDenied), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    /**
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            sharedPrefManager.storeLatLng(new LatLng(location.getLatitude(), location.getAltitude()));
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
}
