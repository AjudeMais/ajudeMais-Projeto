package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import java.util.Date;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.SelectHorarioColetaAdapter;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.asyncTasks.UpdateEstadoDonativoTask;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.domain.EstadoDoacao;
import br.edu.ifpb.ajudemais.enumarations.Estado;

public class SelectHorarioColetaActivity extends BaseActivity {

    private Donativo donativo;
    private Button btnConfirm;
    private RecyclerView recyclerView;
    private UpdateEstadoDonativoTask updateEstadoDonativoTask;
    private SelectHorarioColetaAdapter selectHorarioColetaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_horario_coleta);

        Intent intentDonativo = getIntent();
        this.donativo = (Donativo) intentDonativo.getSerializableExtra("Donativo");
        init();
    }

    @Override
    public void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_list);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        selectHorarioColetaAdapter = new SelectHorarioColetaAdapter(donativo.getHorariosDisponiveis(), this);
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
    private void executeUpdateEstadoDoacaoTask(final Donativo donativo) {
        updateEstadoDonativoTask = new UpdateEstadoDonativoTask(this, donativo);
        updateEstadoDonativoTask.delegate = new AsyncResponse<Donativo>() {
            @Override
            public void processFinish(Donativo output) {

            }
        };
        updateEstadoDonativoTask.execute();
    }

}
