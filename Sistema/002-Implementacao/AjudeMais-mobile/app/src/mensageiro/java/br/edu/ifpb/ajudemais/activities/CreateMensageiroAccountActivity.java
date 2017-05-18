package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.Intent;
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

import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Grupo;
import br.edu.ifpb.ajudemais.domain.Mensageiro;
import br.edu.ifpb.ajudemais.remoteServices.AuthRemoteService;
import br.edu.ifpb.ajudemais.remoteServices.MensageiroRemoteService;

/**
 * <p>
 * <b>{@link CreateMensageiroAccountActivity}</b>
 * </p>
 * <p>
 *     Activity para criar uma conta de mensageiro no sistema.
 * <p>
 *
 * </p>
 *
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
public class CreateMensageiroAccountActivity extends AbstractAsyncActivity implements View.OnClickListener {


    private Toolbar mToolbar;
    private Button btnCreateAccount;
    private EditText edtName;
    private EditText edtUserName;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtCpf;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mensageiro_account);

        init();

        btnCreateAccount.setOnClickListener(this);
    }

    /**
     * Inicializa todos os componentes contidos na activity
     */
    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        resources = getResources();

        btnCreateAccount = (Button) findViewById(R.id.btnEditAccount);
        edtName = (EditText) findViewById(R.id.edtNome);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtCpf = (EditText) findViewById(R.id.edtCpf);
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
                Intent intent = new Intent(CreateMensageiroAccountActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validateMensageiroAccountCreate() {
        String userName = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String cpf = edtCpf.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        return ((!validateFieldsEmpty(userName, phone, cpf, email, confirmPassword, password) && validateLengthFields(userName, password, confirmPassword)) && (validatePasswords(password, confirmPassword) && validateEmail(edtEmail)));
    }


    /**
     * Verifica se username e senha possuem o tamanho mínimo requerido
     *
     * @param userName
     *      username informado pelo usuário
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
     * @param userName
     *      nome de usuario informado
     *
     * @param phone
     *      numero de telefone informado
     *
     * @param cpf
     *      numero de cpf informado
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
    private boolean validateFieldsEmpty(String userName, String phone, String cpf, String email, String confirmPassword, String password) {
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
        } else if (TextUtils.isEmpty(cpf)) {
            edtCpf.requestFocus();
            edtCpf.setError(resources.getString(R.string.msgCpfNotInformed));
            return true;

        }
        else if (TextUtils.isEmpty(email)) {
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEditAccount) {
            if (validateMensageiroAccountCreate()) {

                List<String> grupos = new ArrayList<>();
                grupos.add("ROLE_MENSAGEIRO");
                Mensageiro mensageiro =
                        new Mensageiro(edtName.getText().toString().trim(),
                                       edtCpf.getText().toString().trim(),
                                       edtPhone.getText().toString().trim(),

                        new Conta(edtUserName.getText().toString().trim(),
                                edtPassword.getText().toString().trim(), true,
                                edtEmail.getText().toString().trim(), grupos));
                new CreateMensageiroAccountTask(mensageiro, this).execute();
            }
        }
    }


    /**
     *
     */
    private class CreateMensageiroAccountTask extends AsyncTask<Void, Void, Conta> {

        private String message;
        private Mensageiro mensageiro;
        private String password;
        private MensageiroRemoteService mensageiroRemoteService;
        private AuthRemoteService authRemoteService;

        /**
         *
         * @param mensageiro
         * @param context
         */
        public CreateMensageiroAccountTask(Mensageiro mensageiro, Context context) {
            this.mensageiro = mensageiro;
            mensageiroRemoteService = new MensageiroRemoteService(context);
            authRemoteService = new AuthRemoteService(context);
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
                password = mensageiro.getConta().getSenha();
                mensageiro  = mensageiroRemoteService.saveMensageiro(mensageiro);
                Conta conta = authRemoteService.createAuthenticationToken(new Conta(mensageiro.getConta().getUsername(), password), Grupo.MENSAGEIRO);
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
                intent.setClass(CreateMensageiroAccountActivity.this, MainActivity.class);
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
