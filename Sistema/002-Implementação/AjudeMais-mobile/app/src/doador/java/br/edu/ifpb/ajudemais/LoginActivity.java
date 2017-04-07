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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCriarConta;
    private Button btnEntrar;
    private TextView tvRecuperarSenha;
    private EditText edtNomeUsuario;
    private EditText edtSenhaUsuario;
    private Resources resources;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnCriarConta = (Button) findViewById(R.id.btnCriarConta);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        tvRecuperarSenha = (TextView) findViewById(R.id.tvEsqueceuSenha);
        edtNomeUsuario = (EditText) findViewById(R.id.edtnomeusuario);
        edtSenhaUsuario = (EditText) findViewById(R.id.edtsenha);
        resources = getResources();

        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, CriarContaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnEntrar.setOnClickListener(this);

        tvRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RecuperarSenhaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


    }

    /**
     * Valida o se nome e senha do usuário estão corretos,
     * @return
     */
    private boolean ValidaCamposLogin() {
        String nomeUsuario = edtNomeUsuario.getText().toString().trim();
        String senha = edtSenhaUsuario.getText().toString().trim();
        return (!validaCampoVazio(nomeUsuario, senha) && validaTamanhoCamposLogin(nomeUsuario, senha));
    }

    /**
     * Recebe o nome do usuário e senha verifica se estão vazios. Se ao menos um destes campos estiverem vazios o método retorna true.
     *
     * @param nomeUsuario
     * @param senha
     * @return boolean
     */
    private boolean validaCampoVazio(String nomeUsuario, String senha) {

        if (TextUtils.isEmpty(nomeUsuario)) {
            edtNomeUsuario.requestFocus();
            edtNomeUsuario.setError(resources.getString(R.string.msgNomeUsuarioNaoInformado));
            return true;

        } else if (TextUtils.isEmpty(senha)) {
            edtSenhaUsuario.requestFocus();
            edtSenhaUsuario.setError(resources.getString(R.string.msgSenhaUsuarioNaoInformada));
            return true;
        }
        return false;
    }

    /**
     * Verifica se o tamanho do nome de usuário informada é maior que 3 caracteres e se a senha informada possui mais de 6 caracteres exigidos.
     *
     * @param nomeUsuario
     * @param senha
     * @return boolean
     */
    private boolean validaTamanhoCamposLogin(String nomeUsuario, String senha) {

        if (!(nomeUsuario.length() > 3)) {
            edtNomeUsuario.requestFocus();
            edtNomeUsuario.setError(resources.getString(R.string.msgNomeUsuarioMenorQueTres));
            return false;
        } else if (!(senha.length() > 5)) {
            edtSenhaUsuario.requestFocus();
            edtSenhaUsuario.setError(resources.getString(R.string.msgSenhaUsuarioMenorQueSeis));
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

        if (v.getId() == R.id.btnEntrar) {
            if (ValidaCamposLogin()) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                salvarNomeUsuarioESenha(edtNomeUsuario.getText().toString().trim(), edtSenhaUsuario.getText().toString().trim());
            }
        }
    }

    private void salvarNomeUsuarioESenha(String nomeUsuario, String senhaUsuario){
        SharedPreferences sharedPref = getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nomeUsuario",nomeUsuario);
        editor.putString("senhaUsuario", senhaUsuario);
        editor.apply();
    }
}
