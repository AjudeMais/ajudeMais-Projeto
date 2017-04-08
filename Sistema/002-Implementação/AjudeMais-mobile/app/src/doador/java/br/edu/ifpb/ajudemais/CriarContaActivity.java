package br.edu.ifpb.ajudemais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CriarContaActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;

    private Button btnCriaConta;
    private EditText edtNomeUsuario;
    private EditText edtTelefone;
    private EditText edtEmail;
    private EditText edtSenhaUsuario;
    private EditText edtConfirmacaoSenha;
    private Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        resources = getResources();

        btnCriaConta = (Button) findViewById(R.id.btnCriarConta);
        edtNomeUsuario = (EditText) findViewById(R.id.edtNomeUsuario);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenhaUsuario = (EditText) findViewById(R.id.edtSenha);
        edtConfirmacaoSenha = (EditText) findViewById(R.id.edtConfirmarSenha);

        btnCriaConta.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CriarContaActivity.this, LoginActivity.class);
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
    private boolean validaCadastroDoador() {
        String nomeUsuario = edtNomeUsuario.getText().toString().trim();
        String senhaUsuario = edtSenhaUsuario.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String confirmacaoSenha = edtConfirmacaoSenha.getText().toString().trim();

        return ((!validaCampoVazio(nomeUsuario, telefone, email, confirmacaoSenha, senhaUsuario) && validaTamanhoCamposLogin(nomeUsuario, senhaUsuario, confirmacaoSenha)) && validaSenhas(senhaUsuario, confirmacaoSenha));
    }

    /**
     * Verifica se o tamanho do nome de usuário informada é maior que 3 caracteres e se a senha informada possui mais de 6 caracteres exigidos.
     *
     * @param nomeUsuario
     * @param senha
     * @param confirmacaoSenha
     * @return boolean
     */
    private boolean validaTamanhoCamposLogin(String nomeUsuario, String senha, String confirmacaoSenha) {

        if (!(nomeUsuario.length() > 3)) {
            edtNomeUsuario.requestFocus();
            edtNomeUsuario.setError(resources.getString(R.string.msgNomeUsuarioMenorQueTres));
            return false;
        } else if (!(senha.length() > 5)) {
            edtSenhaUsuario.requestFocus();
            edtSenhaUsuario.setError(resources.getString(R.string.msgSenhaUsuarioMenorQueSeis));
            return false;

        } else if (!(confirmacaoSenha.length() > 5)) {
            edtConfirmacaoSenha.requestFocus();
            edtConfirmacaoSenha.setError(resources.getString(R.string.msgSenhaUsuarioMenorQueSeis));
            return false;
        }
        return true;
    }

    /**
     * Verifica se o tamanho do nome de usuário informada é maior que 3 caracteres e se a senha informada possui mais de 6 caracteres exigidos.
     *
     * @param confirmacaoSenha
     * @param senha
     * @return boolean
     */
    private boolean validaSenhas(String senha, String confirmacaoSenha) {

        if (!senha.equals(confirmacaoSenha)) {
            Toast.makeText(this, R.string.msgSenhaNaoConferem, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * Recebe os campos de cadastro de um novo doador e verifica se estão vazios. Se ao menos um destes campos estiverem vazios o método retorna true.
     *
     * @param nomeUsuario
     * @param senha
     * @return boolean
     */
    private boolean validaCampoVazio(String nomeUsuario, String telefone, String email, String confirmacaoSenha, String senha) {

        if (TextUtils.isEmpty(nomeUsuario)) {
            edtNomeUsuario.requestFocus();
            edtNomeUsuario.setError(resources.getString(R.string.msgNomeUsuarioNaoInformado));
            return true;

        } else if (TextUtils.isEmpty(senha)) {
            edtSenhaUsuario.requestFocus();
            edtSenhaUsuario.setError(resources.getString(R.string.msgSenhaUsuarioNaoInformada));
            return true;
        } else if (TextUtils.isEmpty(telefone)) {
            edtTelefone.requestFocus();
            edtTelefone.setError(resources.getString(R.string.msgTelfoneoNaoInformada));
            return true;
        } else if (TextUtils.isEmpty(email)) {
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.msgEmailNaoInformada));
            return true;

        } else if (TextUtils.isEmpty(confirmacaoSenha)) {
            edtConfirmacaoSenha.requestFocus();
            edtConfirmacaoSenha.setError(resources.getString(R.string.msgConfirmacaoSenhaNaoInformada));
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCriarConta) {
            if (validaCadastroDoador()) {


                Intent intent = new Intent();
                intent.setClass(CriarContaActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                salvarNomeUsuarioESenha(edtNomeUsuario.getText().toString().trim(), edtSenhaUsuario.getText().toString().trim(), edtEmail.getText().toString().trim());
                finish();

            }

        }
    }

    private void salvarNomeUsuarioESenha(String nomeUsuario, String senhaUsuario, String emailUsuario) {
        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nomeUsuario", nomeUsuario);
        editor.putString("senhaUsuario", senhaUsuario);
        editor.putString("emailUsuario", emailUsuario);
        editor.apply();
    }
}
