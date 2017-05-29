package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.asyncTasks.UpdateMensageiroTask;
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
    private TextInputEditText edtCep;
    private TextInputEditText edtLogradouro;
    private TextInputEditText edtNumero;
    private TextInputEditText edtBairro;
    private TextInputEditText edtLocalidade;
    private TextInputEditText edtUf;
    private TextInputEditText edtComplemento;
    private Button btnCadastrarEndereco;
    private Endereco endereco;
    private Toast toast;

    private UpdateMensageiroTask updateMensageiroTask;


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
     * Set nos campos do formulário o endereço
     */
    private void setEndereco(Endereco endereco) {
        if (endereco != null) {
            edtCep.setText(endereco.getCep() != null ? endereco.getCep() : "");
            edtLogradouro.setText(endereco.getLogradouro() != null ? endereco.getLogradouro() : "");
            edtLocalidade.setText(endereco.getLocalidade() != null ? endereco.getLocalidade() : "");
            edtBairro.setText(endereco.getBairro() != null ? endereco.getBairro() : "");
            edtUf.setText(endereco.getUf() != null ? endereco.getUf() : "");

        }

    }

    /**
     * Inicializa todos os componentes da activity
     */
    private void init() {

        mensageiroEdit = (Mensageiro) getIntent().getSerializableExtra("Mensageiro");
        indexEndereco = (Integer) getIntent().getExtras().get("Index");
        endereco = (Endereco) getIntent().getExtras().get("Endereco");

        edtCep = (TextInputEditText) findViewById(R.id.edtCep);
        edtLogradouro = (TextInputEditText) findViewById(R.id.edtLogradouro);
        edtNumero = (TextInputEditText) findViewById(R.id.edtNumero);
        edtBairro = (TextInputEditText) findViewById(R.id.edtBairro);
        edtLocalidade = (TextInputEditText) findViewById(R.id.edtLocalidade);
        edtComplemento = (TextInputEditText) findViewById(R.id.edtComplemento);
        edtUf = (TextInputEditText) findViewById(R.id.edtUf);
        btnCadastrarEndereco = (Button) findViewById(R.id.btnCadastrarEndereco);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setEndereco(endereco);


        if (indexEndereco != null) {
            mToolbar.setTitle("Editar Endereço");
            btnCadastrarEndereco.setText(getString(R.string.btn_edit));
        } else {
            mToolbar.setTitle("Novo Endereço");
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        resources = getResources();


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
        if (mensageiroEdit.getEnderecos().get(indexEndereco).getComplemento() != null) {
            edtComplemento.setText(mensageiroEdit.getEnderecos().get(indexEndereco).getComplemento());
        }
    }

    public boolean validateEnderecoCreate() {
        String cep = edtCep.getText().toString().trim();
        String logradouro = edtLogradouro.getText().toString().trim();
        String numero = edtNumero.getText().toString().trim();
        String bairro = edtBairro.getText().toString().trim();
        String localidade = edtLocalidade.getText().toString().trim();
        String uf = edtUf.getText().toString().trim();

        return !validateEmptyFields(cep, logradouro, numero, bairro, localidade, uf) && !validateFieldsAddress();
    }

    private boolean validateEmptyFields(String cep, String logradouro, String numero,
                                        String bairro, String localidade, String uf) {
        if (TextUtils.isEmpty(cep)) {
            edtCep.requestFocus();
            edtCep.setError(resources.getString(R.string.msgEmptyCep));
            return true;
        } else if (TextUtils.isEmpty(logradouro)) {
            //edtLogradouro.requestFocus();
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

        } else if (TextUtils.isEmpty(uf)) {
            edtUf.requestFocus();
            edtUf.setError(resources.getString(R.string.msgEmptyUf));
            return true;
        }
        return false;
    }

    private boolean validateFieldsAddress(){
        if (edtUf.getText().toString().trim().length() != 2){
            edtUf.requestFocus();
            edtUf.setError(resources.getString(R.string.msgFormatUfInvalide));
            return true;

        }else if (edtCep.getText().toString().length()<9){
            edtCep.requestFocus();
            edtCep.setError(resources.getString(R.string.msgFormatCepInvalide));
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

                if (edtComplemento.getText().toString().trim().length() > 0) {
                    if (indexEndereco != null) {
                        mensageiroEdit.getEnderecos().get(indexEndereco).setComplemento(edtComplemento.getText().toString().trim());

                    } else {
                        endereco.setComplemento(edtComplemento.getText().toString().trim());
                    }
                }
                executeCreateEnderecoTask();
            }
        }
    }

    /**
     * Executa task para criar novo endreco.
     */
    private void executeCreateEnderecoTask(){
        updateMensageiroTask = new UpdateMensageiroTask(this, mensageiroEdit);
        updateMensageiroTask.delegate = new AsyncResponse<Mensageiro>() {
            @Override
            public void processFinish(Mensageiro output) {
                mensageiroEdit = output;
                SharedPrefManager.getInstance(EnderecoActivity.this).storeUser(output.getConta());
                Intent intent = new Intent(EnderecoActivity.this, MyEnderecosActivity.class);
                intent.putExtra("Mensageiro", output);
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
            }
        };

        updateMensageiroTask.execute();
    }
}
