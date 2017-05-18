package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.dto.ChangePasswordDTO;
import br.edu.ifpb.ajudemais.fragments.ProfileSettingsFragment;
import br.edu.ifpb.ajudemais.remoteServices.ContaRemoteService;
import br.edu.ifpb.ajudemais.remoteServices.DoadorRemoteService;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;

public class ProfileSettingsActivity extends AbstractAsyncActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;
    private AndroidUtil androidUtil;
    private SharedPrefManager sharedPrefManager;
    private Doador doador;
    private Button btnChangePassword;
    private Context context;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        context = this;
        androidUtil = new AndroidUtil(this);
        sharedPrefManager = new SharedPrefManager(this);

        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        nestedScrollView = (NestedScrollView) findViewById(R.id.netScroll);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.loading));

        fab = (FloatingActionButton) findViewById(R.id.fabEditAccount);
        new ProfileLoading().execute();

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

        btnChangePassword.setOnClickListener(this);

    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnChangePassword) {
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
            View mView = layoutInflaterAndroid.inflate(R.layout.dialog_change_password, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
            alertDialogBuilderUserInput.setView(mView);

            final TextInputEditText password = (TextInputEditText) mView.findViewById(R.id.edtPassword);
            final TextInputEditText newPassword = (TextInputEditText) mView.findViewById(R.id.edtNewPassword);

            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton(R.string.btn_change_password, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {

                            if (newPassword.getText().toString().trim().length() > 5) {
                                new ChangePasswordTask(password.getText().toString().trim(), newPassword.getText().toString().trim()).execute();
                            } else {
                                Toast.makeText(getApplication(), "A nova senha informada deve contém no mínimo 6 caracteres", Toast.LENGTH_LONG).show();

                            }
                        }
                    })

                    .setNegativeButton(R.string.cancelar,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    dialogBox.cancel();
                                }
                            });

            AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
            alertDialogAndroid.show();
        }
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
            if (doador != null) {

                collapsingToolbarLayout.setTitle(doador.getConta().getUsername());
                ProfileSettingsFragment fragment = new ProfileSettingsFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("doador", doador);
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.editprofile_fragment, fragment);
                fragmentTransaction.commit();
                nestedScrollView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     *
     */
    private class ChangePasswordTask extends AsyncTask<Void, Void, Doador> {

        private ContaRemoteService contaRemoteService;
        private String message = null;
        private String password;
        private String newPassword;

        public ChangePasswordTask(String password, String newPassword) {
            this.password = password;
            this.newPassword = newPassword;
        }

        /**
         *
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
            contaRemoteService = new ContaRemoteService(getApplication());
        }

        /**
         * @param params
         * @return
         */
        @Override
        protected Doador doInBackground(Void... params) {
            try {

                if (androidUtil.isOnline()) {
                    contaRemoteService.changePassword(new ChangePasswordDTO(password, newPassword));

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
            dismissProgressDialog();
            if (message != null) {
                Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
    }
}
