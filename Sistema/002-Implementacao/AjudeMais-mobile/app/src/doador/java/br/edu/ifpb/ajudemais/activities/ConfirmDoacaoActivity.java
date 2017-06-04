package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.DisponibilidadeHorarioAdapter;
import br.edu.ifpb.ajudemais.asycnTasks.RealizarDoacaoTask;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.domain.Endereco;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;
import br.edu.ifpb.ajudemais.utils.CustomToast;


public class ConfirmDoacaoActivity extends BaseActivity implements View.OnClickListener{

    private Donativo donativo;
    private Button btnDoar;
    private TextView tvNomeDonativo;
    private TextView tvDescriptionDonativo;
    private TextView tvCategoria;

    private CardView cardView;
    private RecyclerView recyclerView;

    private Toolbar mToolbar;
    private SeekBar seekBar;
    private TextView tvQuantImg;
    private RealizarDoacaoTask realizarDoacaoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_doacao);
        init();
    }

    @Override
    public void init() {
        initProperties();
        donativo = (Donativo) getIntent().getSerializableExtra("Donativo");

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        tvQuantImg = (TextView) findViewById(R.id.tv_quant_images);
        btnDoar = (Button) findViewById(R.id.btnDoar);
        btnDoar.setOnClickListener(this);

        tvNomeDonativo = (TextView) findViewById(R.id.tv_donative_name);
        tvDescriptionDonativo = (TextView) findViewById(R.id.tv_description);

        tvCategoria = (TextView) findViewById(R.id.tv_categoria);

        setAtrAddressIntoCard(donativo.getEndereco());

        setInformationDonative();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_list);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        DisponibilidadeHorarioAdapter disponibilidadeHorarioAdapter = new DisponibilidadeHorarioAdapter(donativo.getHorariosDisponiveis(),this);
        recyclerView.setAdapter(disponibilidadeHorarioAdapter);
    }

    /**
     * Seta nos TextView as informações do donativo.
     */
    private void setInformationDonative(){
        tvNomeDonativo.setText(donativo.getNome());
        tvDescriptionDonativo.setText(donativo.getDescricao());
        tvCategoria.setText(getString(R.string.categoria)+donativo.getCategoria().getNome());
        if (donativo.getFotosDonativo() != null){
            seekBar.setProgress(donativo.getFotosDonativo().size());
            tvQuantImg.setText(donativo.getFotosDonativo().size()+"/3");
        }
    }

    /**
     * Seta endereço no cardview
     */
    private void setAtrAddressIntoCard(Endereco endereco) {
        cardView = (CardView) findViewById(R.id.componentAddress);
        ((TextView) cardView.findViewById(R.id.tv_logradouro_name)).setText(endereco.getLogradouro());
        ((TextView) cardView.findViewById(R.id.tv_bairro)).setText(endereco.getBairro());
        ((TextView) cardView.findViewById(R.id.tv_number)).setText(endereco.getNumero());
        ((TextView) cardView.findViewById(R.id.tv_cep_name)).setText(endereco.getCep());
        ((TextView) cardView.findViewById(R.id.tv_city)).setText(endereco.getLocalidade());
        ((TextView) cardView.findViewById(R.id.tv_uf_name)).setText(endereco.getUf());
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDoar){
            executeRealizarDoacaoTask();
        }
    }

    /**
     * Executa task para realizar uma doação.
     */
    private void executeRealizarDoacaoTask(){
        realizarDoacaoTask = new RealizarDoacaoTask(this, SharedPrefManager.getInstance(this).getUser().getUsername(), donativo);
        realizarDoacaoTask.delegate = new AsyncResponse<Donativo>() {
            @Override
            public void processFinish(Donativo output) {
                CustomToast.getInstance(ConfirmDoacaoActivity.this).createSuperToastSimpleCustomSuperToast(getString(R.string.doacao_save));
                Intent intent = new Intent(ConfirmDoacaoActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        realizarDoacaoTask.execute();
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
}
