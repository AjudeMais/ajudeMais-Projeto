package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.fragments.ProfileSettingsFragment;
import br.edu.ifpb.ajudemais.remoteServices.DoadorRemoteService;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;

public class ProfileSettingsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;
    private AndroidUtil androidUtil;
    private SharedPrefManager sharedPrefManager;
    private Doador doador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        androidUtil = new AndroidUtil(this);
        sharedPrefManager = new SharedPrefManager(this);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fabEditAccount);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ProfileSettingsActivity.this, CreateAccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Doador", doador);
                startActivity(intent);
            }
        });

        new ProfileLoading().execute();


    }

    /**
     *
     */
    private class ProfileLoading extends AsyncTask<Void, Void, Doador> {

        private DoadorRemoteService doadorRemoteService;
        private String message = null;

        /**
         *
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            doadorRemoteService = new DoadorRemoteService(getApplication());
        }

        /**
         *
         * @param params
         * @return
         */
        @Override
        protected Doador doInBackground(Void... params) {
            try {

                if (androidUtil.isOnline()) {
                    doador = doadorRemoteService.getDoador(sharedPrefManager.getUser().getUsername());

                } else {
                }
            } catch (RestClientException e) {
                message = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return doador;
        }

        @Override
        protected void onPostExecute(Doador doador) {
            if (doador != null){

                collapsingToolbarLayout.setTitle(doador.getConta().getUsername());
                ProfileSettingsFragment fragment = new ProfileSettingsFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("doador", doador);
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.editprofile_fragment, fragment);
                fragmentTransaction.commit();

            }
        }
    }
}
