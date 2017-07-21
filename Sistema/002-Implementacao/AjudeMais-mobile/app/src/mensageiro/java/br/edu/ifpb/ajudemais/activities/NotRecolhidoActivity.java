package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.Date;
import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.asyncTasks.UpdateEstadoDonativoTask;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.domain.EstadoDoacao;
import br.edu.ifpb.ajudemais.enumarations.Estado;
import br.edu.ifpb.ajudemais.utils.CustomToast;

public class NotRecolhidoActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    private Toolbar mToolbar;
    private CheckBox checkOther;
    private CheckBox checkNotWasHouse;
    private CheckBox checkNotHadTime;
    private TextInputLayout textInputLayout;
    private CardView cardNotFound;
    private CardView cardNotDisponivel;
    private CardView cardOther;
    private Button btnConfim;
    private Validator validator;
    private Donativo donativo;

    @Order(1)
    @NotEmpty(messageResId = R.string.motivo_not_informed, sequence = 1)
    private TextInputEditText edtDescricao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_recolhido);
        donativo = (Donativo) getIntent().getSerializableExtra("Donativo");
        init();
    }


    @Override
    public void init() {
        initProperties();

        validator = new Validator(this);
        validator.setValidationListener(this);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        mToolbar.setTitle(getString(R.string.SelectMotivoToCanceled));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        checkOther = (CheckBox) findViewById(R.id.checkOther);
        checkOther.setOnClickListener(this);

        checkNotWasHouse = (CheckBox) findViewById(R.id.check_not_foun_in_house);
        checkNotWasHouse.setOnClickListener(this);

        checkNotHadTime = (CheckBox) findViewById(R.id.check_not_avaiable_mensageiro);
        checkNotHadTime.setOnClickListener(this);

        textInputLayout = (TextInputLayout) findViewById(R.id.ltedtOther);

        cardNotFound = (CardView) findViewById(R.id.card_not_found_in_house);
        cardNotDisponivel = (CardView) findViewById(R.id.card_not_avaiable_mensageiro);
        cardOther = (CardView) findViewById(R.id.card_not_coleted_other);
        cardNotFound.setOnClickListener(this);
        cardNotDisponivel.setOnClickListener(this);
        cardOther.setOnClickListener(this);

        edtDescricao = (TextInputEditText) findViewById(R.id.edtOther);
        btnConfim = (Button) findViewById(R.id.btn_confirm);
        btnConfim.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.checkOther) {
            if (checkOther.isChecked()) {
                textInputLayout.setVisibility(View.VISIBLE);
                validateIsChecked(checkOther);
            } else {
                textInputLayout.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.check_not_foun_in_house) {
            if (checkNotWasHouse.isChecked()) {
                textInputLayout.setVisibility(View.GONE);
                validateIsChecked(checkNotWasHouse);
            }
        } else if (v.getId() == R.id.check_not_avaiable_mensageiro) {
            if (checkNotHadTime.isChecked()) {
                textInputLayout.setVisibility(View.GONE);
                validateIsChecked(checkNotHadTime);
            }
        } else if (v.getId() == R.id.card_not_found_in_house) {
            textInputLayout.setVisibility(View.GONE);
            validateIsChecked(checkNotWasHouse);

        } else if (v.getId() == R.id.card_not_avaiable_mensageiro) {
            textInputLayout.setVisibility(View.GONE);
            validateIsChecked(checkNotHadTime);


        } else if (v.getId() == R.id.card_not_coleted_other) {
            textInputLayout.setVisibility(View.VISIBLE);
            validateIsChecked(checkOther);
        } else if (v.getId() == R.id.btn_confirm) {

            if (checkOther.isChecked()) {
                validator.validate();

            } else if (checkNotWasHouse.isChecked()) {
                EstadoDoacao estadoDoacao = new EstadoDoacao();
                estadoDoacao.setAtivo(true);
                estadoDoacao.setData(new Date());
                estadoDoacao.setEstadoDoacao(Estado.CANCELADO);
                donativo.getEstadosDaDoacao().add(estadoDoacao);
                executeUpdateDonativoTask(donativo, getString(R.string.coleta_cancelada));
            } else if (checkNotHadTime.isChecked()) {

            }else {
                CustomToast.getInstance(this).createSuperToastSimpleCustomSuperToast(getString(R.string.not_selected_motivo));
            }
        }
    }

    /**
     * Executa Asycn task para atualizar o estado do donativo;
     */
    private void executeUpdateDonativoTask(final Donativo donativo, final String msg) {
        UpdateEstadoDonativoTask updateEstadoDonativoTask = new UpdateEstadoDonativoTask(getApplicationContext(), donativo);
        updateEstadoDonativoTask.delegate = new AsyncResponse<Donativo>() {
            @Override
            public void processFinish(Donativo output) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                CustomToast.getInstance(getApplicationContext()).createSuperToastSimpleCustomSuperToast(msg);
            }
        };
        updateEstadoDonativoTask.execute();
    }


    private void validateIsChecked(CheckBox checkBox) {
        if (checkOther.isChecked()) {
            checkOther.setChecked(false);
        }
        if (checkNotWasHouse.isChecked()) {
            checkNotWasHouse.setChecked(false);
        }
        if (checkNotHadTime.isChecked()) {
            checkNotHadTime.setChecked(false);
        }
        checkBox.setChecked(true);
    }

    @Override
    public void onValidationSucceeded() {
        EstadoDoacao estadoDoacao = new EstadoDoacao();
        estadoDoacao.setAtivo(true);
        estadoDoacao.setData(new Date());
        estadoDoacao.setEstadoDoacao(Estado.CANCELADO);
        donativo.getEstadosDaDoacao().add(estadoDoacao);
        executeUpdateDonativoTask(donativo, getString(R.string.coleta_cancelada));
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
                CustomToast.getInstance(NotRecolhidoActivity.this).createSuperToastSimpleCustomSuperToast(message);
            }
        }
    }
}
