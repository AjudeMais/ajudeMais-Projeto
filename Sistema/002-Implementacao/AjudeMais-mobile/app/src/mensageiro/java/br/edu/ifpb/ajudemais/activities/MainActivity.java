package br.edu.ifpb.ajudemais.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.TabFragment;

/**
 * <p>
 * <b>MainActivity</b>
 * </p>
 * <p>
 * <p>
 * Activity inicial para Mensageiro
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class MainActivity extends DrawerMenuActivity {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
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
        init();

        setUpAccount();
        setUpToggle();
        setupNavDrawer();

        componentLoading.setVisibility(View.VISIBLE);
        componentView.setVisibility(View.GONE);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();


    }

    @Override
    protected void onStart() {
        super.onStart();
        new LoadingColetasTask().execute();

    }

    @Override
    public void init() {
        super.init();
        componentLoading = (RelativeLayout) findViewById(R.id.loadingPanel);
        componentLoading.setVisibility(View.VISIBLE);

        componentView = (FrameLayout) findViewById(R.id.containerView);
        componentView.setVisibility(View.GONE);

        componentNoInternet = (RelativeLayout) findViewById(R.id.no_internet_fragment);
        componentNoInternet.setVisibility(View.GONE);

    }

    private void showVisibleComponentNoInternet() {
        componentNoInternet.setVisibility(View.VISIBLE);
        componentLoading.setVisibility(View.GONE);
        componentView.setVisibility(View.GONE);
    }


    /**
     * Classe auxiliar para acessar Serviços
     */
    private class LoadingColetasTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String message) {
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            findViewById(R.id.containerView).setVisibility(View.VISIBLE);
        }

    }
}
