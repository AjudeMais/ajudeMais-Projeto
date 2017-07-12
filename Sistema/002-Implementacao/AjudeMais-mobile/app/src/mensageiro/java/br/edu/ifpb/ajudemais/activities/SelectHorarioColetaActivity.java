package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.SelectHorarioColetaAdapter;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.asyncTasks.UpdateEstadoDonativoTask;
import br.edu.ifpb.ajudemais.domain.DisponibilidadeHorario;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.domain.EstadoDoacao;
import br.edu.ifpb.ajudemais.enumarations.Estado;
import br.edu.ifpb.ajudemais.utils.CustomToast;

public class SelectHorarioColetaActivity extends BaseActivity implements View.OnClickListener{

    private Donativo donativo;
    private Button btnConfirm;
    private RecyclerView recyclerView;
    private UpdateEstadoDonativoTask updateEstadoDonativoTask;
    private SelectHorarioColetaAdapter selectHorarioColetaAdapter;
    private List<DisponibilidadeHorario> currentSelectedHorarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_horario_coleta);

        Intent intentDonativo = getIntent();
        this.donativo = (Donativo) intentDonativo.getSerializableExtra("Donativo");
        currentSelectedHorarios = new ArrayList<>();
        init();
    }

    @Override
    public void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_list);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        selectHorarioColetaAdapter = new SelectHorarioColetaAdapter(donativo.getHorariosDisponiveis(), this, new SelectHorarioColetaAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(DisponibilidadeHorario item) {
                currentSelectedHorarios.add(item);
            }

            @Override
            public void onItemUncheck(DisponibilidadeHorario item) {
                currentSelectedHorarios.remove(item);
            }
        });
        recyclerView.setAdapter(selectHorarioColetaAdapter);
    }

    /**
     * Valida o estado da doação e seta o estado na label.
     */
    private void validateAndSetStateDoacao() {
        for (EstadoDoacao estado : donativo.getEstadosDaDoacao()) {
            if (estado.getAtivo() != null && estado.getAtivo()) {
                estado.setAtivo(false);
            }
        }

        EstadoDoacao estadoDoacao = new EstadoDoacao();
        estadoDoacao.setAtivo(true);
        estadoDoacao.setEstadoDoacao(Estado.ACEITO);
        estadoDoacao.setData(new Date());

        donativo.getEstadosDaDoacao().add(estadoDoacao);

        Log.e("AJUDEMAISLIST VLSTATE", donativo.getEstadosDaDoacao().toString());
    }

    /**
     * Executa Asycn task para atualizar o estado do donativo;
     */
    private void executeUpdateDonativoTask(final Donativo donativo) {
        updateEstadoDonativoTask = new UpdateEstadoDonativoTask(this, donativo);
        updateEstadoDonativoTask.delegate = new AsyncResponse<Donativo>() {
            @Override
            public void processFinish(Donativo output) {

            }
        };
        updateEstadoDonativoTask.execute();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm){
            if (validateSelectedHorario()){
                for(int i = 0 ; i<donativo.getHorariosDisponiveis().size();i++){
                    if(donativo.getHorariosDisponiveis().get(i).getId().equals(currentSelectedHorarios.get(0).getId())){
                        donativo.getHorariosDisponiveis().get(i).setAtivo(true);
                    }
                }
                executeUpdateDonativoTask(donativo);
            }
        }
    }

    /**
     * Valida horário escolhido
     * @return
     */
    private boolean validateSelectedHorario(){

        if (currentSelectedHorarios.size()<1){
            CustomToast.getInstance(this).createSuperToastSimpleCustomSuperToast(getString(R.string.horario_not_informed));
            return false;
        }
        if (currentSelectedHorarios.size() > 1){
            CustomToast.getInstance(this).createSuperToastSimpleCustomSuperToast(getString(R.string.unique_horario_select));
            return false;
        }

        return true;
    }
}
