package br.edu.ifpb.ajudemais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCreateAccount;
    private Button btnOpenApp;
    private LoginButton btnFacebook;
    private TextView tvRecoveryPassword;
    private EditText edtUserName;
    private EditText edtPassword;
    private Resources resources;
    private SharedPreferences sharedPref;
    private CallbackManager callbackManager;

    /**
     * Método Que é executado no momento inicial da inicialização da activity.
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

        callbackManager = CallbackManager.Factory.create();
        btnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // TODO: 13/04/17  
            }

            @Override
            public void onCancel() {
                // TODO: 13/04/17  
            }

            @Override
            public void onError(FacebookException error) {
                // TODO: 13/04/17
            }
        });
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
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                saveInformationsLogin(edtUserName.getText().toString().trim(), edtPassword.getText().toString().trim());
                finish();

            }
        }
    }

    /**
     * Armazena informações de login para usuário ficar logado.
     * @param userName
     * @param password
     */
    private void saveInformationsLogin(String userName, String password) {
        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.apply();
    }
}
