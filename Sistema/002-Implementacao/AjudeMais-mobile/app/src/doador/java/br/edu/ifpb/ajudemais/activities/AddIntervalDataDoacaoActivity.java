package br.edu.ifpb.ajudemais.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.DisponibilidadeHorario;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.utils.ConvertsDate;
import br.edu.ifpb.ajudemais.utils.CustomToast;
import br.edu.ifpb.ajudemais.validator.annotations.ValidDatePtBr;
import br.edu.ifpb.ajudemais.validator.annotations.ValidHourPtBr;


public class AddIntervalDataDoacaoActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener, Validator.ValidationListener, TimePickerDialog.OnTimeSetListener {

    private Calendar myCalendar;
    private Validator validator;
    private Button btnAddDisponibilidade;
    private DisponibilidadeHorario disponibilidadeHorario;
    private TimePickerDialog mTimePickerDialog;
    private int position = 0;
    private Toolbar mToolbar;
    private Donativo donativo;
    private DatePickerDialog datePickerDialog;

    @Order(3)
    @ValidDatePtBr(messageResId = R.string.invalide_date, sequence = 2)
    @NotEmpty(messageResId = R.string.data_not_informed, sequence = 1)
    private TextInputEditText edtData;

    @Order(2)
    @ValidHourPtBr(messageResId = R.string.invalide_hour, sequence = 2)
    @NotEmpty(messageResId = R.string.initial_hour_not_informed, sequence = 1)
    private TextInputEditText edtInitalHour;

    @Order(1)
    @ValidHourPtBr(messageResId = R.string.invalide_hour, sequence = 2)
    @NotEmpty(messageResId = R.string.end_hour_not_informed, sequence = 1)
    private TextInputEditText edtEndHour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_interval_data_doacao);
        init();

        setOperComponentsDateAndHourInFiels();



    }

    /**
     * Configura para quando tiver foco no componente abrir Componente para selecionar data ou hora
     */
    private void setOperComponentsDateAndHourInFiels() {
        edtData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openDataPicker();
                } else {
                    if (datePickerDialog != null) {
                        datePickerDialog.dismiss();
                    }
                }
            }
        });

        edtInitalHour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openTimePickerDialog("Selecione o Horário Inicial");
                } else {
                    if (mTimePickerDialog != null) {
                        mTimePickerDialog.dismiss();
                    }
                }
            }
        });

        edtEndHour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openTimePickerDialog("Selecione o Horário Para o fim da disponibilidade");
                } else {
                    if (mTimePickerDialog != null) {
                        mTimePickerDialog.dismiss();
                    }
                }
            }
        });

    }

    @Override
    public void init() {
        initProperties();
        donativo = (Donativo) getIntent().getSerializableExtra("Donativo");

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myCalendar = Calendar.getInstance();
        btnAddDisponibilidade = (Button) findViewById(R.id.btnAddDisponibilidade);
        btnAddDisponibilidade.setOnClickListener(this);


        edtData = (TextInputEditText) findViewById(R.id.edtData);
        edtData.setOnClickListener(this);
        //androidUtil.setMaskDate(edtData);


        edtInitalHour = (TextInputEditText) findViewById(R.id.edtInitialHour);
        edtInitalHour.setOnClickListener(this);
        //androidUtil.setMaskHour(edtData);

        edtEndHour = (TextInputEditText) findViewById(R.id.edtEndHour);
        edtEndHour.setOnClickListener(this);
        //androidUtil.setMaskHour(edtEndHour);

        validator = new Validator(this);
        Validator.registerAnnotation(ValidDatePtBr.class);
        Validator.registerAnnotation(ValidHourPtBr.class);
        validator.setValidationListener(this);

    }

    /**
     * Mostra componente data picker para selecionar a data.
     */
    private void openDataPicker() {
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(AddIntervalDataDoacaoActivity.this, this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
        }

        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        edtData.setText(ConvertsDate.getInstance().convertDateToStringFormat(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edtData) {
            openDataPicker();
        } else if (v.getId() == R.id.btnAddDisponibilidade) {

            validator.validate();

        } else if (v.getId() == R.id.edtInitialHour) {
            position = 1;
            openTimePickerDialog("Selecione o Horário Inicial");


        } else if (v.getId() == R.id.edtEndHour) {
            position = 2;
            openTimePickerDialog("Selecione o Horário Para o fim da disponibilidade");
        }
    }

    /**
     * Abre Time Picker dialog para Inserir horas.
     */
    private void openTimePickerDialog(String titleDialog) {
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendar.get(Calendar.MINUTE);
        mTimePickerDialog = new TimePickerDialog(AddIntervalDataDoacaoActivity.this, this, hour, minute, true);
        mTimePickerDialog.setTitle(titleDialog);
        mTimePickerDialog.show();
    }

    /**
     * Valida se data informada é superior a atual considerando também a hora.
     *
     * @return
     */
    private boolean validateDate() {

        String stringData = edtData.getText().toString().trim() + " " + edtInitalHour.getText().toString().trim();
        Date date = ConvertsDate.getInstance().convertStringWithDateAndHourToDate(stringData);

        Date actualDate = new Date();

        if (date.after(actualDate)) {
            return true;

        } else if (date.before(actualDate)) {
            return false;
        }

        if (date.equals(actualDate)) {
            return true;
        }

        return false;
    }


    @Override
    public void onValidationSucceeded() {

        if (validateDate()) {
            Date initialDate = ConvertsDate.getInstance().
                    convertStringWithDateAndHourToDate(edtData.getText().toString().trim() + " " +
                            edtInitalHour.getText().toString().trim());

            Date endDate = ConvertsDate.getInstance().convertStringWithDateAndHourToDate(edtData.getText().toString().trim() + " " +
                    edtEndHour.getText().toString().trim());

            if (initialDate.before(endDate)) {

                disponibilidadeHorario = new DisponibilidadeHorario();
                disponibilidadeHorario.setHoraInicio(initialDate);
                disponibilidadeHorario.setHoraFim(endDate);

                if (donativo.getHorariosDisponiveis() == null) {
                    donativo.setHorariosDisponiveis(new ArrayList<DisponibilidadeHorario>());
                }

                donativo.getHorariosDisponiveis().add(disponibilidadeHorario);

                Intent intent = new Intent();
                intent.setClass(AddIntervalDataDoacaoActivity.this, AgendamentoDoacaoActivity.class);
                intent.putExtra("Donativo", donativo);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                CustomToast.getInstance(this).createSuperToastSimpleCustomSuperToast("A hora inicial é superior a hora final do intervalo");
            }
        } else {
            CustomToast.getInstance(this).createSuperToastSimpleCustomSuperToast("A data/hora Inicial informada é inferior a atual.");
        }
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
                CustomToast.getInstance(AddIntervalDataDoacaoActivity.this).createSuperToastSimpleCustomSuperToast(message);
            }
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String hour = String.format("%02d:%02d", hourOfDay, minute);
        if (position == 1) {
            edtInitalHour.setText(hour);

        } else {
            edtEndHour.setText(hour);
        }
    }
}
