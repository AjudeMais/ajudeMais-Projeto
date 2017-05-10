package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.springframework.web.client.RestClientException;

import java.util.Arrays;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Grupo;
import br.edu.ifpb.ajudemais.remoteServices.AuthRemoteService;
import br.edu.ifpb.ajudemais.util.FacebookAccount;


/**
 * <p>
 * <b>LoginActivity</b>
 * </p>
 * <p>
 *     Activity para controlar Login.
 * <p>
 *
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class LoginActivity extends AbstractAsyncActivity implements View.OnClickListener {

    private Button btnCreateAccount;
    private Button btnOpenApp;
    private LoginButton btnFacebook;
    private TextView tvRecoveryPassword;
    private EditText edtUserName;
    private EditText edtPassword;
    private Resources resources;
    private CallbackManager callbackManager;
    private Conta contaFacebook;

    /**
     * Método Que é executado no momento inicial da inicialização da activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        contaFacebook = new Conta();

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

            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RecoveryPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        callbackManager = CallbackManager.Factory.create();
        btnFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        btnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            /**
             *
             * @param loginResult
             */
            @Override
            public void onSuccess(LoginResult loginResult) {
                goToMainActivity(loginResult, getApplicationContext());
            }

            /**
             *
             */
            @Override
            public void onCancel() {
                goBackToLoginScreen();
                Toast.makeText(getApplicationContext(), R.string.cancelOperation, Toast.LENGTH_LONG).show();
            }

            /**
             *
             * @param error
             */
            @Override
            public void onError(FacebookException error) {
                goBackToLoginScreen();
                Toast.makeText(getApplicationContext(), R.string.errorOnLogin, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Método que retorna para a tela de login em casos de cancelamento de login via Facebook
     * ou falha na tentativa de login
     */
    private void goBackToLoginScreen() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Método que obtem os dados de um usuário do facebook após uma solicitação bem sucedida
     * de login. A partir deste resultado, encaminha o user para a tela principal da aplicação
     * @param loginResult
     *      Resultado da solicitação de login
     * @param context
     *      Contexto da aplicação
     */
    private void goToMainActivity(LoginResult loginResult, Context context) {
        contaFacebook = FacebookAccount.userFacebookData(loginResult, context);
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Inicializa todos os atributos e propriedades utilizadas na activity.
     */
    public void init() {
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnOpenApp = (Button) findViewById(R.id.btnOpen);
        btnFacebook = (LoginButton) findViewById(R.id.btnFacebook);
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
                new LoginTask(this, edtUserName.getText().toString().trim(), edtPassword.getText().toString().trim()).execute();
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
     * Classe para carregar recursos da api REST.
     */
    private class LoginTask extends AsyncTask<Void, Void, Conta> {

        private String message = null;
        private Conta conta;
        private AuthRemoteService authRemoteService;
        private String username;
        private String senha;
        private Context context;

        public LoginTask(Context context, String username, String senha) {
            this.context = context;
            this.authRemoteService = new AuthRemoteService(context);
            this.username = username;
            this.senha = senha;


        }

        /**
         *
         */
        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();

        }

        /**
         *
         * @param params
         * @return
         */
        @Override
        protected Conta doInBackground(Void... params) {
            try {
                if (contaFacebook != null) {
                    conta = contaFacebook;
                    return conta;
                } else {
                    conta = new Conta(username, senha);
                    conta = authRemoteService.createAuthenticationToken(conta, Grupo.DOADOR);
                    return conta;
                }
            } catch (RestClientException e) {
                message = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         *
         * @param conta
         */
        @Override
        protected void onPostExecute(Conta conta) {
            dismissProgressDialog();

            if (conta != null) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Conta", conta);
                startActivity(intent);
                finish();
            } else {
                showResult(message);
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
