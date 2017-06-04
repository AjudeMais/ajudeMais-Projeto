package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.domain.Endereco;
import br.edu.ifpb.ajudemais.utils.CustomToast;


/**
 * <p>
 * <b>{@link AddEnderecoActivity}</b>
 * </p>
 * <p>
 * Activity cadastrar endereço para realizar uma doação.
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a> and
 */
public class AddEnderecoActivity extends BaseActivity implements Validator.ValidationListener, View.OnClickListener {

    private Toolbar mToolbar;

    private Button btnCadastrarEndereco;
    private Validator validator;
    private Endereco endereco;
    private Donativo donativo;

    private TextInputEditText edtComplemento;

    @Order(6)
    @NotEmpty(messageResId = R.string.msgEmptyCep, sequence = 1)
    private TextInputEditText edtCep;

    @Order(5)
    @NotEmpty(messageResId = R.string.msgEmptyLogradouro, sequence = 1)
    private TextInputEditText edtLogradouro;

    @Order(4)
    @NotEmpty(messageResId = R.string.msgEmptyNumero, sequence = 1)
    private TextInputEditText edtNumero;

    @Order(3)
    @NotEmpty(messageResId = R.string.msgEmptyBairro, sequence = 1)
    private TextInputEditText edtBairro;

    @Order(2)
    @NotEmpty(messageResId = R.string.msgEmptyLocalidade, sequence = 1)
    private TextInputEditText edtLocalidade;

    @Order(1)
    @Length(max = 2, min = 2, messageResId = R.string.msgFormatUfInvalide, sequence = 3)
    @NotEmpty(messageResId = R.string.msgEmptyUf, sequence = 1)
    private TextInputEditText edtUf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_endereco);
        init();

        validator = new Validator(this);
        validator.setValidationListener(this);

        btnCadastrarEndereco.setOnClickListener(this);

        this.endereco = (Endereco) getIntent().getExtras().get("Endereco");
        this.donativo = (Donativo) getIntent().getExtras().get("Donativo");

        if (donativo.getEndereco() != null){
            endereco = donativo.getEndereco();
            setEndereco(donativo.getEndereco());
        }else {
            setEndereco(endereco);
        }
    }

    /**
     * Set nos campos do formulário o endereço
     */
    private void setEndereco(Endereco endereco) {
        if (endereco != null) {
            if (endereco.getCep() != null){
                edtCep.setText(endereco.getCep());
                edtCep.setVisibility(View.GONE);
            }
            if (endereco.getUf() != null){
                edtUf.setText(endereco.getUf());
                edtUf.setVisibility(View.GONE);
            }
            if (endereco.getLocalidade() != null){
                edtLocalidade.setText(endereco.getLocalidade());
                edtLocalidade.setVisibility(View.GONE);
            }
            if (endereco.getBairro() != null){
                edtBairro.setText(endereco.getBairro());
                edtBairro.setVisibility(View.GONE);
            }

            edtLogradouro.setText(endereco.getLogradouro() != null ? endereco.getLogradouro() : "");
            edtNumero.setText(endereco.getNumero() != null ? endereco.getNumero() : "");
            edtNumero.setText(endereco.getComplemento() != null ? endereco.getNumero() : "");
        }

    }

    /**
     * Inicializa todos os componentes da activity
     */
    public void init() {
        initProperties();
        edtCep = (TextInputEditText) findViewById(R.id.edtCep);
        edtLogradouro = (TextInputEditText) findViewById(R.id.edtLogradouro);
        edtNumero = (TextInputEditText) findViewById(R.id.edtNumero);
        edtBairro = (TextInputEditText) findViewById(R.id.edtBairro);
        edtLocalidade = (TextInputEditText) findViewById(R.id.edtLocalidade);
        edtComplemento = (TextInputEditText) findViewById(R.id.edtComplemento);
        edtUf = (TextInputEditText) findViewById(R.id.edtUf);
        btnCadastrarEndereco = (Button) findViewById(R.id.btnCadastrarEndereco);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        mToolbar.setTitle("Complete o endereço");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCadastrarEndereco) {
            validator.validate();
        }
    }


    @Override
    public void onValidationSucceeded() {
        addAddress();

        Intent intent = new Intent(AddEnderecoActivity.this, DoacaoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Endereco", endereco);
        intent.putExtra("Donativo", donativo);
        startActivity(intent);
        finish();
        CustomToast.getInstance(AddEnderecoActivity.this).createSuperToastSimpleCustomSuperToast(getString(R.string.endereco_adicionado));

    }

    /**
     * Seta as informações do formulário no endereço
     */
    private void addAddress() {
        this.endereco.setLogradouro(edtLogradouro.getText().toString().trim());
        this.endereco.setNumero(edtNumero.getText().toString().trim());
        this.endereco.setBairro(edtBairro.getText().toString().trim());
        this.endereco.setUf(edtUf.getText().toString().trim());
        this.endereco.setLocalidade(edtLocalidade.getText().toString().trim());
        this.endereco.setComplemento(edtComplemento.getText().toString().trim().length() > 0 ? edtComplemento.getText().toString().trim() : null);
        this.endereco.setCep(edtCep.getText().toString().trim());
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
                view.requestFocus();
            } else {
                CustomToast.getInstance(AddEnderecoActivity.this).createSuperToastSimpleCustomSuperToast(message);
            }
        }
    }
}
