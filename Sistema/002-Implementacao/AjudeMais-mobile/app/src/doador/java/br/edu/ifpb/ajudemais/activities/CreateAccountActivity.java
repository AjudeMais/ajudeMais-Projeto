package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
 * <b>CreateAccountActivity</b>
 * </p>
 * <p>
 * Activity para controlar Conta Usuário.
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a> and
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public class CreateAccountActivity extends AbstractAsyncActivity implements View.OnClickListener {


    private Toolbar mToolbar;
    private Button btnCreateAccount;
    private TextInputEditText edtName;
    private TextInputEditText edtUserName;
    private TextInputEditText edtPhone;
    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;
    private TextInputEditText edtConfirmPassword;
    private Resources resources;
    private AndroidUtil androidUtil;
    private Doador doadorEdit;
    private SharedPrefManager sharedPrefManager;
    private TextInputLayout ltedtConfirmPassword;
    private TextInputLayout ltedtPassword;
    private TextInputLayout ltedtUserName;

    /**
     * Método Que é executado no momento inicial da inicialização da activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        androidUtil = new AndroidUtil(this);
        init();

        if (doadorEdit != null) {
            setEditValueInFields();
        }
        btnCreateAccount.setOnClickListener(this);

    }

    /**
     * Inicializa todos os atributos e propriedades utilizadas na activity.
     */
    private void init() {
        doadorEdit = (Doador) getIntent().getSerializableExtra("Doador");
        sharedPrefManager = new SharedPrefManager(this);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        if (doadorEdit != null) {
            mToolbar.setTitle("Editar Conta");
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        resources = getResources();

        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        edtName = (TextInputEditText) findViewById(R.id.edtNome);
        edtUserName = (TextInputEditText) findViewById(R.id.edtUserName);
        edtPhone = (TextInputEditText) findViewById(R.id.edtPhone);
        edtEmail = (TextInputEditText) findViewById(R.id.edtEmail);
        edtPassword = (TextInputEditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (TextInputEditText) findViewById(R.id.edtConfirmPassword);
        ltedtConfirmPassword = (TextInputLayout) findViewById(R.id.ltedtConfirmPassword);
        ltedtPassword = (TextInputLayout) findViewById(R.id.ltedtPassword);
        ltedtUserName = (TextInputLayout) findViewById(R.id.ltedtUserName);

        androidUtil.setMaskPhone(edtPhone);
    }


    /**
     * Implementação para controlar operações na action bar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);

                if (doadorEdit != null) {
                    intent = new Intent(CreateAccountActivity.this, ProfileSettingsActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Valida o cadastro de doadorEdit sem facebook,
     *
     * @return boolean
     */
    private boolean validateDoadorCreate() {
        String userName = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        return ((!validateFieldsEmpty(name, userName, phone, email, confirmPassword, password) && (validateLengthFields(userName, phone, password, confirmPassword))) && (validateEmail() && validatePasswords(password, confirmPassword)));
    }

    /**
     * Valida o cadastro de doadorEdit sem facebook,
     *
     * @return boolean
     */
    private boolean validateDoadorEdit() {
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        return ((!validateFieldsEmpty(name, null, phone, email, null, null) && (validateLengthFields(null, phone, null, null))) && (validateEmail()));
    }

    /**
     * Valida e-mail digita se o mesmo é válido
     *
     * @return
     */
    private boolean validateEmail() {

        if (!androidUtil.isEmailValid(edtEmail.getText().toString().trim())) {
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.msgInvalideEmail));
            return false;
        }
        return true;
    }

    /**
     * Verifica se o tamanho do nome de usuário informada é maior que 3 caracteres e se a password informada possui mais de 6 caracteres exigidos.
     *
     * @param userName
     * @param password
     * @param confirmPassword
     * @return boolean
     */
    private boolean validateLengthFields(String userName, String phone, String password, String confirmPassword) {

        if (userName !=null && !(userName.length() > 3)) {
            edtUserName.requestFocus();
            edtUserName.setError(resources.getString(R.string.msgInvalideUserName));
            return false;
        } else if (!(phone.length() > 14 && phone.length() < 16)) {
            edtPhone.requestFocus();
            edtPhone.setError(resources.getString(R.string.msgPhoneNotCompleted));
            return false;

        } else if (password != null && !(password.length() > 5)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.msgInvalidePassword));
            return false;

        } else if (confirmPassword != null && !(confirmPassword.length() > 5)) {
            edtConfirmPassword.requestFocus();
            edtConfirmPassword.setError(resources.getString(R.string.msgInvalidePassword));
            return false;
        }
        return true;
    }

    /**
     * Set valores do Doador.
     */
    public void setEditValueInFields() {
        if (doadorEdit != null) {
            edtName.setText(doadorEdit.getNome());
            edtUserName.setVisibility(View.GONE);
            ltedtUserName.setVisibility(View.GONE);
            edtPhone.setText(doadorEdit.getTelefone());
            edtEmail.setText(doadorEdit.getConta().getEmail());
            edtConfirmPassword.setVisibility(View.GONE);
            edtPassword.setVisibility(View.GONE);
            ltedtConfirmPassword.setVisibility(View.GONE);
            ltedtPassword.setVisibility(View.GONE);
            btnCreateAccount.setText("Editar");
        }
    }

    /**
     * Verifica se o tamanho do nome de usuário informada é maior que 3 caracteres e se a password informada possui mais de 6 caracteres exigidos.
     *
     * @param confirmPassword
     * @param password
     * @return boolean
     */
    private boolean validatePasswords(String password, String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, R.string.msgPasswordAndConfirmPasswordDoesNotMatch, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * Recebe os campos de cadastro de um novo doadorEdit e verifica se estão vazios. Se ao menos um destes campos estiverem vazios o método retorna true.
     *
     * @param userName
     * @param password
     * @return boolean
     */
    private boolean validateFieldsEmpty(String name, String userName, String phone, String email, String confirmPassword, String password) {
        if (TextUtils.isEmpty(name)) {
            edtName.requestFocus();
            edtName.setError(resources.getString(R.string.msgNameNotInformed));
            return true;

        } else if (userName != null && TextUtils.isEmpty(userName)) {
            edtUserName.requestFocus();
            edtUserName.setError(resources.getString(R.string.msgUserNameNotInformed));
            return true;

        } else if (TextUtils.isEmpty(phone)) {
            edtPhone.requestFocus();
            edtPhone.setError(resources.getString(R.string.msgPhoneNotInformed));
            return true;

        } else if (TextUtils.isEmpty(email)) {
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.msgEmailNotInformed));
            return true;

        } else if (password != null && TextUtils.isEmpty(password)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.msgPasswordNotInformed));
            return true;

        } else if (confirmPassword != null && TextUtils.isEmpty(confirmPassword)) {
            edtConfirmPassword.requestFocus();
            edtConfirmPassword.setError(resources.getString(R.string.msgConfirmPasswordNotInformed));
            return true;
        }
        return false;
    }


    /**
     * Implementa as operação de clique.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCreateAccount) {
            if (doadorEdit != null) {
                if (validateDoadorEdit()) {
                    new CreateAccounTask(doadorEdit, this).execute();
                }

            } else {
                if (validateDoadorCreate()) {

                    List<String> grupos = new ArrayList<>();
                    grupos.add("ROLE_DOADOR");
                    Doador doador = new Doador(edtName.getText().toString().trim(), edtPhone.getText().toString().trim(),
                            new Conta(edtUserName.getText().toString().trim(),
                                    edtPassword.getText().toString().trim(), true, edtEmail.getText().toString().trim(), grupos));

                    new CreateAccounTask(doador, this).execute();


                }
            }
        }
    }


    /**
     * Carrega recursos da API REST.
     */
    private class CreateAccounTask extends AsyncTask<Void, Void, Conta> {

        private String message;
        private Doador doador;
        private String password;
        private DoadorRemoteService doadorRemoteService;
        private AuthRemoteService authRemoteService;
        private Doador doadorUpdated;

        public CreateAccounTask(Doador doador, Context context) {
            this.doador = doador;
            doadorRemoteService = new DoadorRemoteService(context);
            authRemoteService = new AuthRemoteService(context);
        }

        /**
         *
         */
        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

            if (doadorEdit != null){
                Conta conta = doadorEdit.getConta();
                conta.setEmail(edtEmail.getText().toString().trim());
                doadorEdit.setNome(edtName.getText().toString().trim());
                doadorEdit.setTelefone(edtPhone.getText().toString().trim());
                doadorEdit.setConta(conta);
            }
        }

        /**
         * @param params
         * @return
         */
        @Override
        protected Conta doInBackground(Void... params) {

            try {
                if (doadorEdit == null) {
                    password = doador.getConta().getSenha();
                    doador = doadorRemoteService.saveDoador(doador);
                    Conta conta = authRemoteService.createAuthenticationToken(new Conta(doador.getConta().getUsername(), password), Grupo.DOADOR);
                    return conta;

                } else {
                    doadorUpdated = doadorRemoteService.updateDoador(doadorEdit);
                }
                return null;

            } catch (RestClientException e) {
                message = e.getMessage();
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        /**
         * @param conta
         */
        @Override
        protected void onPostExecute(Conta conta) {
            dismissProgressDialog();
            if (conta != null) {
                Intent intent = new Intent();
                intent.setClass(CreateAccountActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Conta", conta);
                startActivity(intent);
                finish();

            } else if (doador != null && doadorUpdated != null) {
                SharedPrefManager.getInstance(getApplication()).storeUser(doador.getConta());
                Intent intent = new Intent(CreateAccountActivity.this, ProfileSettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Informações atualizadas.", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }

        }

    }
}
