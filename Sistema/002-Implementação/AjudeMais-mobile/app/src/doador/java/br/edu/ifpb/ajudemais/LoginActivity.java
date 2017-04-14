package br.edu.ifpb.ajudemais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.JwtToken;
import br.edu.ifpb.ajudemais.remoteServices.AuthRemoteService;
import br.edu.ifpb.ajudemais.remoteServices.DoadorRemoteService;

public class LoginActivity extends AbstractAsyncActivity implements View.OnClickListener {

    private Button btnCreateAccount;
    private Button btnOpenApp;
    private TextView tvRecoveryPassword;
    private EditText edtUserName;
    private EditText edtPassword;
    private Resources resources;
    private SharedPreferences sharedPref;
    private AuthRemoteService authRemoteService;

    /**
     * Método Que é executado no momento inicial da inicialização da activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        init();

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, CreateAccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnOpenApp.setOnClickListener(this);

        tvRecoveryPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RecoveryPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


    }

    /**
     * Inicializa todos os atributos e propriedades utilizadas na activity.
     */
    public void init() {
        authRemoteService = new AuthRemoteService();
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnOpenApp = (Button) findViewById(R.id.btnOpen);
        tvRecoveryPassword = (TextView) findViewById(R.id.tvForgotPassword);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        resources = getResources();

    }

    /**
     * Valida o se nome e senha do usuário estão corretos,
     *
     * @return boolean
     */
    private boolean validateLoginFields() {
        String userName = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        return (!validateEmptyFields(userName, password) && ValidateFieldsLength(userName, password));
    }

    /**
     * Recebe o nome do usuário e password verifica se estão vazios. Se ao menos um destes campos estiverem vazios o método retorna true.
     *
     * @param userName
     * @param password
     * @return boolean
     */
    private boolean validateEmptyFields(String userName, String password) {

        if (TextUtils.isEmpty(userName)) {
            edtUserName.requestFocus();
            edtUserName.setError(resources.getString(R.string.msgUserNameNotInformed));
            return true;

        } else if (TextUtils.isEmpty(password)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.msgPasswordNotInformed));
            return true;
        }
        return false;
    }

    /**
     * Verifica se o tamanho do nome de usuário informada é maior que 3 caracteres e se a password informada possui mais de 6 caracteres exigidos.
     *
     * @param userName
     * @param password
     * @return boolean
     */
    private boolean ValidateFieldsLength(String userName, String password) {

        if (!(userName.length() > 3)) {
            edtUserName.requestFocus();
            edtUserName.setError(resources.getString(R.string.msgInvalideUserName));
            return false;
        } else if (!(password.length() > 5)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.msgInvalidePassword));
            return false;
        }
        return true;
    }

    /**
     * Método implementado da interface View.OnClickListener para criação de eventos de clicks.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnOpen) {
            if (validateLoginFields()) {
                new LoginTask(edtUserName.getText().toString().trim(), edtPassword.getText().toString().trim()).execute();
            }
        }
    }

    /**
     * @param result
     */
    private void showResult(String result) {
        if (result != null) {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "I got null, something happened!", Toast.LENGTH_LONG).show();
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


    private class LoginTask extends AsyncTask<Void, Void, JwtToken> {

        private String message = null;
        private Conta conta;
        private JwtToken jwtToken;
        private AuthRemoteService authRemoteService;
        private String username;
        private String senha;

        public LoginTask(String username, String senha) {
            this.authRemoteService = new AuthRemoteService();
            this.username = username;
            this.senha = senha;

        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

        }

        @Override
        protected JwtToken doInBackground(Void... params) {

            try {
                conta = new Conta(username, senha);
                jwtToken = authRemoteService.createAuthenticationToken(conta);
                conta = authRemoteService.getUsuario(jwtToken.getToken());

                return jwtToken;

            } catch (HttpStatusCodeException e) {
                message = e.getResponseBodyAsString().replace("[", "").replace("]", "");
                e.printStackTrace();
            } catch (RestClientException e) {
                message = "Ocorreu um problema, tente novamente mais tarde";
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
                intent.setClass(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Conta", conta);
                startActivity(intent);
                saveInformationsLoginAndToken(username, senha, jwtToken.getToken());

                finish();

            }else{
                showResult(message);
            }
        }

    }
}
