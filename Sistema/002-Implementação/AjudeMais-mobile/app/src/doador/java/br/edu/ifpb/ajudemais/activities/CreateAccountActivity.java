package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.domain.JwtToken;
import br.edu.ifpb.ajudemais.remoteServices.AuthRemoteService;
import br.edu.ifpb.ajudemais.remoteServices.DoadorRemoteService;

public class CreateAccountActivity extends AbstractAsyncActivity implements View.OnClickListener {


    private Toolbar mToolbar;
    private Button btnCreateAccount;
    private EditText edtName;
    private EditText edtUserName;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private Resources resources;

    /**
     * Método Que é executado no momento inicial da inicialização da activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        init();

        btnCreateAccount.setOnClickListener(this);

    }

    /**
     * Inicializa todos os atributos e propriedades utilizadas na activity.
     */
    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        resources = getResources();

        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        edtName = (EditText) findViewById(R.id.edtNome);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);


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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Valida o cadastro de doador sem facebook,
     *
     * @return boolean
     */
    private boolean validateDoadorCreate() {
        String userName = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        return ((!validateFieldsEmpty(userName, phone, email, confirmPassword, password) && validateLengthFields(userName, password, confirmPassword)) && (validatePasswords(password, confirmPassword) && validateEmail(edtEmail)));
    }

    /**
     * Verifica se o tamanho do nome de usuário informada é maior que 3 caracteres e se a password informada possui mais de 6 caracteres exigidos.
     *
     * @param userName
     * @param password
     * @param confirmPassword
     * @return boolean
     */
    private boolean validateLengthFields(String userName, String password, String confirmPassword) {

        if (!(userName.length() > 3)) {
            edtUserName.requestFocus();
            edtUserName.setError(resources.getString(R.string.msgInvalideUserName));
            return false;
        } else if (!(password.length() > 5)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.msgInvalidePassword));
            return false;

        } else if (!(confirmPassword.length() > 5)) {
            edtConfirmPassword.requestFocus();
            edtConfirmPassword.setError(resources.getString(R.string.msgInvalidePassword));
            return false;
        }
        return true;
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
            Toast.makeText(this, R.string.msgPasswordAndConfirmPasswordNotCombined, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * Recebe os campos de cadastro de um novo doador e verifica se estão vazios. Se ao menos um destes campos estiverem vazios o método retorna true.
     *
     * @param userName
     * @param password
     * @return boolean
     */
    private boolean validateFieldsEmpty(String userName, String phone, String email, String confirmPassword, String password) {

        if (TextUtils.isEmpty(userName)) {
            edtUserName.requestFocus();
            edtUserName.setError(resources.getString(R.string.msgUserNameNotInformed));
            return true;

        } else if (TextUtils.isEmpty(password)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.msgPasswordNotInformed));
            return true;
        } else if (TextUtils.isEmpty(phone)) {
            edtPhone.requestFocus();
            edtPhone.setError(resources.getString(R.string.msgPhoneNotInformed));
            return true;
        } else if (TextUtils.isEmpty(email)) {
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.msgEmailNotInformed));
            return true;

        } else if (TextUtils.isEmpty(confirmPassword)) {
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
            if (validateDoadorCreate()) {

                List<String> grupos = new ArrayList<>();
                grupos.add("ROLE_DOADOR");
                Doador doador = new Doador(edtName.getText().toString().trim(), edtPhone.getText().toString().trim(),
                        new Conta(edtUserName.getText().toString().trim(),
                                edtPassword.getText().toString().trim(), true, edtEmail.getText().toString().trim(), grupos));

                new CreateAccounTask(doador).execute();


            }

        }
    }

    /**
     * Armazena informações de login para usuário ficar logado.
     *
     * @param userName
     * @param password
     */
    private void saveInformationsLoginAndToken(String userName, String password, String accessToken) {
        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.putString("accessToken", accessToken);
        editor.apply();
    }


    /**
     *
     * @param edtEmail
     */
    private boolean validateEmail(EditText edtEmail) {
        String email = edtEmail.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email.matches(emailPattern)) {
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.msgEmailNotValid));
            return false;
        }else {
            return true;
        }
    }


    private class CreateAccounTask extends AsyncTask<Void, Void, JwtToken> {


        private String message;
        private String password;
        private Doador doador;
        private JwtToken jwtToken;
        private DoadorRemoteService doadorRemoteService;
        private AuthRemoteService authRemoteService;


        public CreateAccounTask(Doador doador) {
            this.doador = doador;
            this.password = doador.getConta().getSenha();
            this.authRemoteService = new AuthRemoteService();
            this.doadorRemoteService = new DoadorRemoteService();
        }


        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

        }

        @Override
        protected JwtToken doInBackground(Void... params) {

            try {
                doadorRemoteService = new DoadorRemoteService();
                doador = doadorRemoteService.saveDoador(doador);
                doador.getConta().setSenha(password);
                jwtToken = authRemoteService.createAuthenticationToken(doador.getConta());

                return jwtToken;

            } catch (HttpStatusCodeException e) {
                message = e.getResponseBodyAsString().replace("[", "").replace("]", "");
                e.printStackTrace();

            } catch (RestClientException e) {
                message = "Ocorreu um erro inesperado no sistema aguarde e tente novamente.";
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(JwtToken jwtToken) {
            dismissProgressDialog();

            if (jwtToken != null) {
                Intent intent = new Intent();
                intent.setClass(CreateAccountActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Conta", doador.getConta());
                startActivity(intent);
                saveInformationsLoginAndToken(doador.getConta().getUsername(), doador.getConta().getSenha(), jwtToken.getToken());

                finish();

            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }

        }


    }
}
