package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.listeners.RecyclerItemClickListener;

public class MySolicitacoesActivity extends BaseActivity implements RecyclerItemClickListener.OnItemClickListener {

    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private Integer position;
    private FloatingActionButton fab;
    private RelativeLayout componentNoInternet;
    private FrameLayout componentListEmpty;
    private RelativeLayout componentView;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_solicitacoes);
    }

    @Override
    public void init() {
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        mToolbar.setTitle(getString(R.string.mysolicitacoes));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));

        componentNoInternet = (RelativeLayout) findViewById(R.id.no_internet_fragment);
        componentListEmpty = (FrameLayout) findViewById(R.id.empty_list) ;
        componentView = (RelativeLayout) findViewById(R.id.loadingPanelMainSearchInst);

        componentNoInternet.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);
        findViewById(R.id.empty_list).setVisibility(View.GONE);
    }

    /**
     * Auxiliar para mostrar fragmento de sem conexão quando não houver internet no device.
     */
    public void setVisibleNoConnection() {
        componentNoInternet.setVisibility(View.VISIBLE);
        componentView.setVisibility(View.GONE);
        componentListEmpty.setVisibility(View.GONE);
    }

    /**
     * Auxiliar para mostrar lista de solicitações de coleta e esconder demais fragmentos.
     */
    private void showListSolicitacoes() {
        componentNoInternet.setVisibility(View.GONE);
        componentView.setVisibility(View.GONE);
        componentListEmpty.setVisibility(View.GONE);
    }

    /**
     * Auxiliar para mostrar fragmento para lista vazia.
     */
    private void showListEmpty() {
        componentNoInternet.setVisibility(View.GONE);
        componentView.setVisibility(View.GONE);
        componentListEmpty.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(MySolicitacoesActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(View childView, int position) {
        this.position = position;
        goToDetailSolicitacaoActivity();
    }

    @Override
    public void onItemLongPress(View childView, int position) {
        // none
    }

    /**
     * Metodo responsável por navegar entre as telas de todas as solicitações e de uma solicitação específica
     */
    private void goToDetailSolicitacaoActivity() {
    }
}
