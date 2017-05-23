package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.EnderecoAdapter;
import br.edu.ifpb.ajudemais.domain.Endereco;
import br.edu.ifpb.ajudemais.domain.Mensageiro;
import br.edu.ifpb.ajudemais.listeners.RecyclerItemClickListener;
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
public class MyEnderecosActivity extends AbstractAsyncActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {

    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Mensageiro mensageiro;
    private Integer position;
    private AndroidUtil androidUtil;
    private FloatingActionButton fab;


    /**
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

        fab = (FloatingActionButton) findViewById(R.id.btnNewAddress);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EnderecoActivity.class);
                intent.putExtra("Mensageiro", mensageiro);
                startActivity(intent);
            }
        });
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
    private void showListEmpty() {
        findViewById(R.id.no_internet_fragment).setVisibility(View.GONE);
        findViewById(R.id.loadingPanelMainSearchInst).setVisibility(View.GONE);
        findViewById(R.id.containerViewSearchInst).setVisibility(View.GONE);
        findViewById(R.id.empty_list).setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View childView, int position) {
        this.position = position;
        openDialogEditAddress();
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
     * Dialog para seleção da opção de editar ou remover endereço.
     */
    private void openDialogEditAddress() {
        final CharSequence[] items = {getString(R.string.tv_edit), getString(R.string.tv_delete), getString(R.string.cancelar)};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.selectOption));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals(getString(R.string.tv_edit))) {
                    Intent intent = new Intent(getApplicationContext(), EnderecoActivity.class);
                    intent.putExtra("Mensageiro", mensageiro);
                    intent.putExtra("Index", position);
                    startActivity(intent);

                } else if (items[item].equals(getString(R.string.tv_delete))) {
                    Endereco endereco = mensageiro.getEnderecos().get(position);
                    mensageiro.getEnderecos().remove(endereco);
                    new DeleteEnderecoTask(mensageiro, getApplicationContext()).execute();

                } else if (items[item].equals(getString(R.string.cancelar))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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
         * @param mensageiro
         */
        @Override
        protected void onPostExecute(Mensageiro mensageiro) {
            if (mensageiro != null) {
                if (mensageiro.getEnderecos().size() > 0) {
                    recyclerView.setHasFixedSize(true);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    EnderecoAdapter enderecoAdapter = new EnderecoAdapter(mensageiro.getEnderecos(), getApplicationContext());
                    recyclerView.setAdapter(enderecoAdapter);

                    showListEnderecos();

                } else {
                    showListEmpty();

                }
            }
        }
    }

    private class DeleteEnderecoTask extends AsyncTask<Void, Void, Mensageiro> {

        private String message;
        private Mensageiro mensageiro;
        private MensageiroRemoteService mensageiroRemoteService;
        private Mensageiro mensageiroUpdated;
        private Toast toast;

        public DeleteEnderecoTask(Mensageiro mensageiro, Context context) {
            this.mensageiro = mensageiro;
            mensageiroRemoteService = new MensageiroRemoteService(context);
        }

        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
        }

        @Override
        protected Mensageiro doInBackground(Void... params) {
            try {
                mensageiroUpdated = mensageiroRemoteService.updateMensageiro(mensageiro);
            } catch (RestClientException e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Mensageiro mensageiro) {
            dismissProgressDialog();
            if (mensageiroUpdated != null) {

                EnderecoAdapter enderecoAdapter = new EnderecoAdapter(mensageiro.getEnderecos(), getApplicationContext());
                recyclerView.setAdapter(enderecoAdapter);

                toast = Toast.makeText(getApplicationContext(), getString(R.string.deletedAddress), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();

                showListEnderecos();


            } else {
                toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            }
        }
    }

}
