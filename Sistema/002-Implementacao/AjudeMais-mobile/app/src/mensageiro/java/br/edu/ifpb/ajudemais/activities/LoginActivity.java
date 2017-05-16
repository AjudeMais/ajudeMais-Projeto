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
import org.springframework.web.client.RestClientException;
import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Grupo;
import br.edu.ifpb.ajudemais.remoteServices.AuthRemoteService;


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
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a> and
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 */
public class LoginActivity extends AbstractAsyncActivity implements View.OnClickListener {

    private Button btnOpenApp;
    private TextView tvRecoveryPassword;
    private EditText edtUserName;
    private EditText edtPassword;
    private Button btnCreateAccount;
    private Resources resources;

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

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, CreateMensageiroAccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    /**
     * Inicializa todos os atributos e propriedades utilizadas na activity.
     */
    public void init() {
        btnOpenApp = (Button) findViewById(R.id.btnOpen);
        tvRecoveryPassword = (TextView) findViewById(R.id.tvForgotPassword);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
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
        } else if (!(password.length() > 3)) {
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
     * Exibi Toast com resultado da operação de acesso ao RESt.
     * @param result
     */
    private void showResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

    }


    /**
     * Classe auxiliar para acessar Serviços
     */
    private class LoginTask extends AsyncTask<Void, Void, Conta> {

        private String message = null;
        private Conta conta;
        private AuthRemoteService authRemoteService;
        private String username;
        private String senha;

        /**
         *
         * @param context
         * @param username
         * @param senha
         */
        public LoginTask(Context context, String username, String senha) {
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
                conta = new Conta(username, senha);
                conta = authRemoteService.createAuthenticationToken(conta, Grupo.MENSAGEIRO);

               return conta;

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
}
