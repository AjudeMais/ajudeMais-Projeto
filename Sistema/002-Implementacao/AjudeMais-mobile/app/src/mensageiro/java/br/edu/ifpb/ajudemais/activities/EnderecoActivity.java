package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Endereco;
import br.edu.ifpb.ajudemais.domain.Mensageiro;
import br.edu.ifpb.ajudemais.remoteServices.MensageiroRemoteService;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;

public class EnderecoActivity extends AbstractAsyncActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private Resources resources;
    private AndroidUtil androidUtil;
    private Mensageiro mensageiroEdit;
    private Integer indexEndereco;
    private SharedPrefManager sharedPrefManager;
    private TextInputEditText edtCep;
    private TextInputEditText edtLogradouro;
    private TextInputEditText edtNumero;
    private TextInputEditText edtBairro;
    private TextInputEditText edtLocalidade;
    private TextInputEditText edtUf;
    private TextInputEditText edtComplemento;
    private Button btnCadastrarEndereco;
    private Endereco endereco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);
        androidUtil = new AndroidUtil(this);
        init();
        if (indexEndereco != null) {
            setEditEnderecoValues();
        }
        btnCadastrarEndereco.setOnClickListener(this);
    }

    /**
     * Inicializa todos os componentes da activity
     */
    private void init() {

        mensageiroEdit = (Mensageiro) getIntent().getSerializableExtra("Mensageiro");
        indexEndereco = (Integer) getIntent().getExtras().get("Index");

        sharedPrefManager = new SharedPrefManager(this);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);

        if (mensageiroEdit != null) {
            mToolbar.setTitle("Editar Endereço");
        } else {
            mToolbar.setTitle("Cadastrar Endereço");
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        resources = getResources();

        edtCep = (TextInputEditText) findViewById(R.id.edtCep);
        edtLogradouro = (TextInputEditText) findViewById(R.id.edtLogradouro);
        edtNumero = (TextInputEditText) findViewById(R.id.edtNumero);
        edtBairro = (TextInputEditText) findViewById(R.id.edtBairro);
        edtLocalidade = (TextInputEditText) findViewById(R.id.edtLocalidade);
        edtComplemento = (TextInputEditText) findViewById(R.id.edtComplemento);
        edtUf = (TextInputEditText) findViewById(R.id.edtUf);
        btnCadastrarEndereco = (Button) findViewById(R.id.btnCadastrarEndereco);

        androidUtil.setMaskCep(edtCep);
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
                Intent intent = new Intent(EnderecoActivity.this, MyEnderecosActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setEditEnderecoValues() {
        edtCep.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getCep());
        edtLogradouro.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getLogradouro());
        edtLocalidade.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getLocalidade());
        edtBairro.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getBairro());
        edtUf.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getUf());
        edtNumero.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getNumero());
    }

    public boolean validateEnderecoCreate() {
        String cep = edtCep.getText().toString().trim();
        String logradouro = edtLogradouro.getText().toString().trim();
        String numero = edtNumero.getText().toString().trim();
        String bairro = edtBairro.getText().toString().trim();
        String localidade = edtLocalidade.getText().toString().trim();

        return !validateEmptyFields(cep, logradouro, numero, bairro, localidade);
    }

    private boolean validateEmptyFields(String cep, String logradouro, String numero,
                                        String bairro, String localidade) {
        if (TextUtils.isEmpty(cep)) {
            edtCep.requestFocus();
            edtCep.setError(resources.getString(R.string.msgEmptyCep));
            return true;
        } else if (TextUtils.isEmpty(logradouro)) {
            edtLogradouro.requestFocus();
            edtLogradouro.setError(resources.getString(R.string.msgEmptyLogradouro));
            return true;
        } else if (TextUtils.isEmpty(numero)) {
            edtNumero.requestFocus();
            edtNumero.setError(resources.getString(R.string.msgEmptyNumero));
            return true;
        } else if (TextUtils.isEmpty(bairro)) {
            edtBairro.requestFocus();
            edtBairro.setError(resources.getString(R.string.msgEmptyBairro));
            return true;
        } else if (TextUtils.isEmpty(localidade)) {
            edtLocalidade.requestFocus();
            edtLocalidade.setError(resources.getString(R.string.msgEmptyLocalidade));
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnCadastrarEndereco) {
            if (validateEnderecoCreate()) {

                if (indexEndereco != null) {
                    mensageiroEdit.getEnderecos().get(indexEndereco).setLogradouro(edtLogradouro.getText().toString().trim());
                    mensageiroEdit.getEnderecos().get(indexEndereco).setBairro(edtBairro.getText().toString().trim());
                    mensageiroEdit.getEnderecos().get(indexEndereco).setNumero(edtNumero.getText().toString().trim());
                    mensageiroEdit.getEnderecos().get(indexEndereco).setLocalidade(edtLocalidade.getText().toString().trim());
                    mensageiroEdit.getEnderecos().get(indexEndereco).setUf(edtUf.getText().toString().trim());

                } else {
                    endereco = new Endereco(edtCep.getText().toString().trim(),
                            edtNumero.getText().toString().trim(),
                            edtBairro.getText().toString().trim(),
                            edtLocalidade.getText().toString().trim(),
                            edtLogradouro.getText().toString().trim(),
                            edtUf.getText().toString().trim());

                    mensageiroEdit.getEnderecos().add(endereco);
                }

                if (TextUtils.isEmpty(edtComplemento.getText().toString().trim())) {
                    if (indexEndereco != null) {
                        mensageiroEdit.getEnderecos().get(indexEndereco).setComplemento(edtComplemento.getText().toString().trim());

                    } else {
                        endereco.setComplemento(edtComplemento.getText().toString().trim());
                    }
                }


                new CreateEnderecoTask(mensageiroEdit, this).execute();

            }
        }
    }

    private class CreateEnderecoTask extends AsyncTask<Void, Void, Conta> {

        private String message;
        private Mensageiro mensageiro;
        private String password;
        private MensageiroRemoteService mensageiroRemoteService;
        private Mensageiro mensageiroUpdated;
        private Toast toast;

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
            try {
                mensageiroUpdated = mensageiroRemoteService.updateMensageiro(mensageiro);
            } catch (RestClientException e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Conta conta) {
            dismissProgressDialog();
            if (mensageiro != null && mensageiroUpdated != null) {
                SharedPrefManager.getInstance(getApplication()).storeUser(mensageiro.getConta());
                Intent intent = new Intent(EnderecoActivity.this, MyEnderecosActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                if (indexEndereco != null) {
                    toast = Toast.makeText(getApplicationContext(), getString(R.string.updatedAddress), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();

                } else {
                    toast = Toast.makeText(getApplicationContext(), getString(R.string.createdAddress), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
