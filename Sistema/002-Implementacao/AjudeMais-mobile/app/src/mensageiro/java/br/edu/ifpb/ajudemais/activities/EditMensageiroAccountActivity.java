package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifpb.ajudemais.R;

public class EditMensageiroAccountActivity extends AbstractAsyncActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextInputEditText edtNome;
    private TextInputEditText edtUsername;
    private TextInputEditText edtPhone;
    private TextInputEditText edtEmail;
    private TextInputEditText edtCpf;
    private TextInputEditText edtPassword;
    private TextInputEditText edtConfirmPassword;
    private Button btnEditAccount;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mensageiro_account);
        init();

        btnEditAccount.setOnClickListener(this);
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        resources = getResources();

        edtNome = (TextInputEditText) findViewById(R.id.edtNome);
        edtUsername = (TextInputEditText) findViewById(R.id.edtUserName);
        edtPhone = (TextInputEditText) findViewById(R.id.edtPhone);
        edtEmail = (TextInputEditText) findViewById(R.id.edtEmail);
        edtCpf = (TextInputEditText) findViewById(R.id.edtCpf);
        edtPassword = (TextInputEditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (TextInputEditText) findViewById(R.id.edtConfirmPassword);
        btnEditAccount = (Button) findViewById(R.id.btnEditAccount);
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
                Intent intent = new Intent(EditMensageiroAccountActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validateMensageiroAccountCreate() {
        String password = edtPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        return ((!validateFieldsEmpty(phone, email, confirmPassword, password) && validateLengthFields(password, confirmPassword)) && (validatePasswords(password, confirmPassword) && validateEmail(edtEmail)));
    }


    /**
     * Verifica se username e senha possuem o tamanho mínimo requerido
     *
     *
     * @param password
     *      senha informada pelo usuário
     *
     * @param confirmPassword
     *      confirmação de senha informada pelo usuário
     *
     * @return
     *      retorna se os dados inseridos estão com o tamanho minimo requerido
     */
    private boolean validateLengthFields(String password, String confirmPassword) {

        if (!(password.length() > 5)) {
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
     * Verifica se a senha e a confirmação de senha informadas pelo usuário são iguais
     *
     * @param password
     *      senha informada
     *
     * @param confirmPassword
     *      confirmacao de senha informada
     *
     * @return
     *      retorna se os dados inseridos são iguais
     */
    private boolean validatePasswords(String password, String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, R.string.msgPasswordAndConfirmPasswordDoesNotMatch, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Verifica se o usuario deixou algum campo em branco
     *
     *
     * @param phone
     *      numero de telefone informado
     *
     *
     * @param email
     *      endereco de email informado
     *
     * @param password
     *      senha informada
     *
     * @param confirmPassword
     *      confirmacao de senha informada
     *
     * @return
     *      se existe algum campo em branco
     */
    private boolean validateFieldsEmpty(String phone, String email, String confirmPassword, String password) {
        if (TextUtils.isEmpty(password)) {
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
     * Verifica se o email informado pelo usuário é válido
     *
     * @param edtEmail
     *      email informado pelo usuario
     *
     * @return
     *      se o endereco de email é válido ou não
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEditAccount) {
            if (validateMensageiroAccountCreate()) {

            }
        }
    }
}
