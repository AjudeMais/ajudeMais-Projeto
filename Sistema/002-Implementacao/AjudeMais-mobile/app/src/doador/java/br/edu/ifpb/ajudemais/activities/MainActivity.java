package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.dto.LatLng;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;


/**
 * <p>
 * <b>MainActivity</b>
 * </p>
 * <p>
 * Activity para controlar Login.
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class MainActivity extends DrawerMenuActivity implements View.OnClickListener{

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private FloatingActionButton fab;
    private RelativeLayout componentLoading;
    private FrameLayout componentView;
    private RelativeLayout componentNoInternet;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGoogleAPIClient();
        checkPermissions();

        init();

        if (!androidUtil.isOnline()) {
            showVisibleComponentNoInternet();
        }

        setUpAccount();
        setUpToggle();
        setupNavDrawer();

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new MainTab01()).commit();

        fab.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        new LoadingCampanhasDoacoesTask().execute();
    }

    /**
     * Modelo default inicial para onRequestPermissionsResult.
     *
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
                        showTurnOnGpsDialog();
                    } else
                        showTurnOnGpsDialog();


                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.permissionDenied), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void init() {
        super.init();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        componentLoading = (RelativeLayout) findViewById(R.id.loadingPanel);
        componentLoading.setVisibility(View.VISIBLE);

        componentView = (FrameLayout) findViewById(R.id.containerView);
        componentView.setVisibility(View.GONE);

        componentNoInternet = (RelativeLayout) findViewById(R.id.no_internet_fragment);
        componentNoInternet.setVisibility(View.GONE);
    }

    private void showVisibleComponentNoInternet(){
        componentNoInternet.setVisibility(View.VISIBLE);
        componentLoading.setVisibility(View.GONE);
        componentView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        if (mLastLocation == null) {
            startLocationUpdates();
            mLastLocation = getLocation();
        }
        if (mLastLocation != null) {
            SharedPrefManager.getInstance(getApplicationContext()).storeLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        }
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MainSearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    /**
     *
     */
    private class LoadingCampanhasDoacoesTask extends AsyncTask<Void, Void, String> {

        /**
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(Void... params) {
            return null;
        }

        /**
         * @param message
         */
        @Override
        protected void onPostExecute(String message) {
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            findViewById(R.id.containerView).setVisibility(View.VISIBLE);
        }
    }
}
