package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.EnderecoAdapter;
import br.edu.ifpb.ajudemais.domain.Mensageiro;
import br.edu.ifpb.ajudemais.listeners.RecyclerItemClickListener;
import br.edu.ifpb.ajudemais.remoteServices.ContaRemoteService;
import br.edu.ifpb.ajudemais.remoteServices.MensageiroRemoteService;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;

/**
 * <p>
 * <b>{@link MyEnderecosActivity}</b>
 * </p>
 * <p>
 * <p>
 * Activity para gerenciar endereços de mensageiro.
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class MyEnderecosActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener  {

    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Mensageiro mensageiro;
    private AndroidUtil androidUtil;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_enderecos);

        androidUtil = new AndroidUtil(this);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        mToolbar.setTitle(getString(R.string.myaddress));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        findViewById(R.id.no_internet_fragment).setVisibility(View.GONE);

        swipeRefreshLayout.setOnRefreshListener(this);

        findViewById(R.id.loadingPanelMainSearchInst).setVisibility(View.VISIBLE);
        findViewById(R.id.containerViewSearchInst).setVisibility(View.GONE);
        findViewById(R.id.empty_list).setVisibility(View.GONE);

    }

    /**
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        new LoadingEnderecosTask().execute();

    }

    /**
     * Auxiliar para mostrar fragmento de sem conexão quando não houver internet no device.
     */
    public void setVisibleNoConnection() {
        findViewById(R.id.no_internet_fragment).setVisibility(View.VISIBLE);
        findViewById(R.id.loadingPanelMainSearchInst).setVisibility(View.GONE);
        findViewById(R.id.containerViewSearchInst).setVisibility(View.GONE);
        findViewById(R.id.empty_list).setVisibility(View.GONE);
    }


    /**
     * Auxiliar para mostrar lista de endereços e esconder demais fragmentos.
     */
    private void showListEnderecos() {
        findViewById(R.id.no_internet_fragment).setVisibility(View.GONE);
        findViewById(R.id.loadingPanelMainSearchInst).setVisibility(View.GONE);
        findViewById(R.id.containerViewSearchInst).setVisibility(View.VISIBLE);
        findViewById(R.id.empty_list).setVisibility(View.GONE);
    }

    /**
     * Auxiliar para mostrar fragmento para lista vazia.
     */
    private void showListEmpty(){
        findViewById(R.id.no_internet_fragment).setVisibility(View.GONE);
        findViewById(R.id.loadingPanelMainSearchInst).setVisibility(View.GONE);
        findViewById(R.id.containerViewSearchInst).setVisibility(View.GONE);
        findViewById(R.id.empty_list).setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View childView, int position) {
        Intent intent = new Intent(this, EnderecoActivity.class);
        intent.putExtra("Mensageiro", mensageiro);
        intent.putExtra("Index", position);
        startActivity(intent);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }


    @Override
    public void onRefresh() {

        if (androidUtil.isOnline()) {
            new LoadingEnderecosTask().execute();
        } else {
            setVisibleNoConnection();
        }
        swipeRefreshLayout.setRefreshing(false);
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
                Intent intent = new Intent(MyEnderecosActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    /**
     *
     */
    private class LoadingEnderecosTask extends AsyncTask<Void, Void, Mensageiro> {

        private MensageiroRemoteService mensageiroRemoteService;
        private String message = null;
        private AndroidUtil androidUtil;

        /**
         *
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            androidUtil = new AndroidUtil(getApplicationContext());
            mensageiroRemoteService = new MensageiroRemoteService(getApplication());
        }

        /**
         * @param params
         * @return
         */
        @Override
        protected Mensageiro doInBackground(Void... params) {
            try {

                if (androidUtil.isOnline()) {
                    mensageiro = mensageiroRemoteService.getMensageiro(SharedPrefManager.getInstance(getApplicationContext()).getUser().getUsername());

                } else {
                    setVisibleNoConnection();

                }
            } catch (RestClientException e) {
                message = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mensageiro;
        }

        /**
         *
         * @param mensageiro
         */
        @Override
        protected void onPostExecute(Mensageiro mensageiro) {
            if (mensageiro != null) {
                if (mensageiro.getEnderecos().size()>0) {
                    recyclerView.setHasFixedSize(true);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    EnderecoAdapter enderecoAdapter = new EnderecoAdapter(mensageiro.getEnderecos(), getApplicationContext());
                    recyclerView.setAdapter(enderecoAdapter);

                    //  recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), this));
                    showListEnderecos();

                }else {
                    showListEmpty();

                }
            }
        }
    }

}
