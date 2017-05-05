package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;
import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.TabFragmentMain;
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
public class MainActivity extends AbstractActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private FloatingActionButton fab;
    private SharedPrefManager sharedPrefManager;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this);

        initGoogleAPIClient();
        checkPermissions();

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        findViewById(R.id.containerView).setVisibility(View.GONE);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragmentMain()).commit();

        init();
        setUpAccount();
        setUpToggle();
        setupNavDrawer();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLastLocation = getLocation();
                if (mLastLocation != null) {
                    sharedPrefManager.storeLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                }
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainSearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        new LoadingCampanhasDoacoesTask().execute();
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
            Toast.makeText(getApplication(), "CARREGADO", Toast.LENGTH_LONG).show();
        }
    }
}
