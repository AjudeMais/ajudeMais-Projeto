package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.domain.Grupo;
import br.edu.ifpb.ajudemais.remoteServices.AuthRemoteService;
import br.edu.ifpb.ajudemais.remoteServices.DoadorRemoteService;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;

/**
 * <p>
 * <b>{@link CreateAccountHelperActivity}</b>
 * </p>
 * <p>
 *     Activity para finalizar criação de conta utilizando o facebook
 * <p>
 *
 * </p>
 *
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 */
public class CreateAccountHelperActivity extends AbstractAsyncActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private Button btnFinalizeAccountCreation;
    private TextInputEditText edtTelefone;
    private TextInputEditText edtEmail;
    private Resources resources;
    private AndroidUtil androidUtil;
    private Doador doador;
    private TextInputLayout ltEdtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_helper);
        androidUtil = new AndroidUtil(this);
        init();
        if (doador.getConta().getEmail() != null) {
            editFieldsVisualization();
        }
        androidUtil.setMaskPhone(edtTelefone);
        btnFinalizeAccountCreation.setOnClickListener(this);
    }

    private void editFieldsVisualization() {
        ltEdtEmail.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                logoutFaceBook();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logoutFaceBook() {
        LoginManager.getInstance().logOut();
    }

    private void init() {
        doador = (Doador) getIntent().getSerializableExtra("Doador");
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        mToolbar.setTitle("Criar Conta");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        resources = getResources();

        btnFinalizeAccountCreation = (Button) findViewById(R.id.btnFinalizeAccountCreation);
        edtTelefone = (TextInputEditText) findViewById(R.id.edtTelefone);
        edtEmail = (TextInputEditText) findViewById(R.id.edtEmail);
        ltEdtEmail = (TextInputLayout) findViewById(R.id.ltEdtEmail);
    }

    public boolean validateDoadorCreate() {
        String phone = edtTelefone.getText().toString().trim();
        return validateDataInformed(phone, null);
    }

    public boolean validateDataInformed(String phone, String email) {
        if (email != null) {
            if(!androidUtil.isEmailValid(email)) {
                edtEmail.requestFocus();
                edtEmail.setError(resources.getString(R.string.msgInvalideEmail));
                return false;
            }
        }
        if (!androidUtil.isPhoneValid(phone)) {
            edtTelefone.requestFocus();
            edtTelefone.setError(resources.getString(R.string.msgPhoneNotCompleted));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnFinalizeAccountCreation) {
            if (validateDoadorCreate()) {
                List<String> grupos = new ArrayList<>();
                grupos.add("ROLE_DOADOR");
                doador.getConta().setGrupos(grupos);
                doador.setTelefone(edtTelefone.getText().toString().trim());
                if (doador.getConta().getEmail() == null) {
                    doador.getConta().setEmail(edtEmail.getText().toString().trim());
                }
                new CreateDoadorAccountTask(doador, this).execute();
            }
        }
    }

    public class CreateDoadorAccountTask extends AsyncTask<Void, Void, Conta> {

        private String message;
        private Doador doador;
        private String password;
        private DoadorRemoteService doadorRemoteService;
        private AuthRemoteService authRemoteService;

        public CreateDoadorAccountTask(Doador doador, Context context) {
            this.doador = doador;
            doadorRemoteService = new DoadorRemoteService(context);
            authRemoteService = new AuthRemoteService(context);
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Conta doInBackground(Void... params) {
            try{
                password = doador.getConta().getSenha();
                doador = doadorRemoteService.saveDoador(doador);
                Conta conta = authRemoteService.createAuthenticationToken(new Conta(doador.getConta().getUsername(), password), Grupo.DOADOR);
                return conta;
            } catch (RestClientException e) {
                message = e.getMessage();
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Conta conta) {
            dismissProgressDialog();
            if (conta != null) {
                SharedPrefManager.getInstance(getApplication()).storeUser(doador.getConta());
                Intent intent = new Intent();
                intent.setClass(CreateAccountHelperActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Conta", conta);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
