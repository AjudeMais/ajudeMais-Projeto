package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.DisponibilidadeHorarioAdapter;
import br.edu.ifpb.ajudemais.domain.Donativo;


public class AgendamentoDoacaoActivity extends BaseActivity implements View.OnClickListener {

    private Button btnKeep;
    private Donativo donativo;
    private FloatingActionButton fbAddDisponibilidade;
    private RecyclerView recyclerView;
    private Toolbar mToolbar;
    private DisponibilidadeHorarioAdapter disponibilidadeHorarioAdapte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento_doacao);
        init();
    }

    @Override
    public void init() {
        initProperties();
        donativo = (Donativo) getIntent().getSerializableExtra("Donativo");

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        btnKeep = (Button) findViewById(R.id.btnKeepDetalhes);
        fbAddDisponibilidade = (FloatingActionButton) findViewById(R.id.fbAddDisponibilidade);
        fbAddDisponibilidade.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_list);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        setSupportActionBar(mToolbar);


        if (donativo != null &&
                (donativo.getHorariosDisponiveis() != null
                        && donativo.getHorariosDisponiveis().size() > 0)) {
            disponibilidadeHorarioAdapte = new DisponibilidadeHorarioAdapter(donativo.getHorariosDisponiveis(), this);
            recyclerView.setAdapter(disponibilidadeHorarioAdapte);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fbAddDisponibilidade) {
            Intent intent = new Intent();
            intent.setClass(AgendamentoDoacaoActivity.this, AddIntervalDataDoacaoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Donativo", donativo);
            startActivity(intent);
        }
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

    /**
     * @param outState
     */
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Donativo", donativo);
    }

}
