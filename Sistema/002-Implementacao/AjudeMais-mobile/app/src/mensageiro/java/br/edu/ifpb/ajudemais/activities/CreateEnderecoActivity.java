package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Endereco;
import br.edu.ifpb.ajudemais.domain.Mensageiro;
import br.edu.ifpb.ajudemais.remoteServices.MensageiroRemoteService;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;

public class CreateEnderecoActivity extends AbstractAsyncActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private Button btnCriarEndereco;
    private TextInputEditText edtCep;
    private TextInputEditText edtNumero;
    private TextInputEditText editBairro;
    private TextInputEditText editLogradouro;
    private TextInputEditText editUf;
    private TextInputEditText editComplemento;
    private Resources resources;
    private AndroidUtil androidUtil;
    private Mensageiro mensageiroEdit;
    private Integer indexEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_endereco);
        androidUtil = new AndroidUtil(getApplicationContext());
        init();
        btnCriarEndereco.setOnClickListener(this);
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        resources = getResources();
        edtCep = (TextInputEditText) findViewById(R.id.edtCpf);
        edtNumero = (TextInputEditText) findViewById(R.id.edtNumero);
        editBairro = (TextInputEditText) findViewById(R.id.editBairro);
        editLogradouro = (TextInputEditText) findViewById(R.id.editLogradouro);
        editUf = (TextInputEditText) findViewById(R.id.editUf);
        editComplemento = (TextInputEditText) findViewById(R.id.editComplemento);
        btnCriarEndereco = (Button) findViewById(R.id.btn_cad_endereco);


        mensageiroEdit = (Mensageiro) getIntent().getSerializableExtra("Mensageiro");
        indexEndereco = (Integer) getIntent().getExtras().get("Index");

        if (mensageiroEdit != null) {
            if (mensageiroEdit.getEnderecos().size() > 0 && indexEndereco != null) {
                edtCep.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getCep());
                edtNumero.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getNumero());
                editBairro.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getBairro());
                editLogradouro.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getLogradouro());
                editUf.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getUf());
                editComplemento.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getComplemento());
            }
            indexEndereco = (Integer) getIntent().getExtras().get("Index");
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CreateEnderecoActivity.this, LoginActivity.class);

                if (mensageiroEdit != null) {
                    intent = new Intent(CreateEnderecoActivity.this, ProfileSettingsActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private Boolean validateEndereco() {
        String cep = edtCep.getText().toString().trim();
        String numero = edtNumero.getText().toString().trim();
        String bairro = editBairro.getText().toString().trim();
        String logradouro = editLogradouro.getText().toString().trim();
        String uf = editUf.getText().toString().trim();

        return (validateEmptyFields(cep, numero, bairro, logradouro, uf));

    }

    private boolean validateEmptyFields(String cep, String numero, String bairro, String logradouro, String uf) {
        if (TextUtils.isEmpty(cep)) {
            edtCep.requestFocus();
            edtCep.setError(resources.getString(R.string.mgsCepEmpty));
            return true;

        } else if (numero != null && TextUtils.isEmpty(numero)) {
            edtNumero.requestFocus();
            edtNumero.setError(resources.getString(R.string.mgsNumeroEmpty));
            return true;

        } else if (TextUtils.isEmpty(bairro)) {
            editBairro.requestFocus();
            editBairro.setError(resources.getString(R.string.mgsBairroEmpty));
            return true;

        } else if (TextUtils.isEmpty(logradouro)) {
            editLogradouro.requestFocus();
            editLogradouro.setError(resources.getString(R.string.mgsLogradouroEmpty));
            return true;

        } else if (TextUtils.isEmpty(uf)) {
            editUf.requestFocus();
            editUf.setError(resources.getString(R.string.mgsUfEmpty));
            return true;

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnCriarEndereco) {
            if (validateEndereco()) {
                if (mensageiroEdit != null && indexEndereco != null) {
                    Endereco endereco = mensageiroEdit.getEnderecos().get(indexEndereco);
                    endereco.setCep(edtCep.getText().toString().trim());
                    endereco.setNumero(edtNumero.getText().toString().trim());
                    endereco.setBairro(editBairro.getText().toString().trim());
                    endereco.setUf(editUf.getText().toString().trim());
                    endereco.setLogradouro(editLogradouro.getText().toString().trim());
                    endereco.setComplemento(editComplemento.getText().toString().trim());

                    mensageiroEdit.getEnderecos().add(indexEndereco, endereco);
                }
                else {
                    Endereco endereco = mensageiroEdit.getEnderecos().get(indexEndereco);
                    endereco.setCep(edtCep.getText().toString().trim());
                    endereco.setNumero(edtNumero.getText().toString().trim());
                    endereco.setBairro(editBairro.getText().toString().trim());
                    endereco.setUf(editUf.getText().toString().trim());
                    endereco.setLogradouro(editLogradouro.getText().toString().trim());
                    endereco.setComplemento(editComplemento.getText().toString().trim());

                    mensageiroEdit.getEnderecos().add(endereco);
                }
            }
        }
    }

    private class CreateEnderecoTask extends AsyncTask<Void, Void, Conta> {

        private String message;
        private Mensageiro mensageiro;
        private Mensageiro mensageiroUpdated;
        private MensageiroRemoteService mensageiroRemoteService;

        public CreateEnderecoTask(Mensageiro mensageiro, Context context) {
            this.mensageiro = mensageiro;
            mensageiroRemoteService = new MensageiroRemoteService(context);
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Conta doInBackground(Void... params) {
            mensageiroUpdated = mensageiroRemoteService.updateMensageiro(mensageiro);
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
                intent.setClass(CreateEnderecoActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Conta", conta);
                startActivity(intent);
                finish();

            } else if (mensageiro != null && mensageiroUpdated != null) {
                SharedPrefManager.getInstance(getApplication()).storeUser(mensageiro.getConta());
                Intent intent = new Intent(CreateEnderecoActivity.this, ProfileSettingsActivity.class);
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
