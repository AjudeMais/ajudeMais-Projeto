package br.edu.ifpb.ajudemais.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.asycnTasks.LoadingDonativoByMensageiroTask;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.listeners.RecyclerItemClickListener;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;

/**
 *
 */
public class MainSearchMyColetas extends Fragment implements RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener{

    private DonativosAdapter donativosAdapter;
    private static RecyclerView recyclerView;
    private static View view;
    private List<Donativo> donativos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AndroidUtil androidUtil;
    private LoadingDonativoByMensageiroTask loadingDonativoByMensageiroTask;
    private RecyclerItemClickListener.OnItemClickListener clickListener;
    private SearchView searchView;
    private SharedPrefManager sharedPrefManager;

    public MainSearchMyColetas() {
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    /**
     *
     */
    @Override
    public void onStart() {
        super.onStart();
        this.clickListener = this;
        androidUtil = new AndroidUtil(getContext());

        if (androidUtil.isOnline()) {
            executeLoadingDonativosTask();
        } else {
            setVisibleNoConnection();
        }
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_search_my_coletas, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_list_my_coletas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        view.findViewById(R.id.no_internet_fragment).setVisibility(View.GONE);

        swipeRefreshLayout.setOnRefreshListener(this);

        view.findViewById(R.id.loading_panel_my_coletas).setVisibility(View.VISIBLE);
        view.findViewById(R.id.container_fragment_my_coletas).setVisibility(View.GONE);
        view.findViewById(R.id.empty_list).setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(View childView, int position) {

    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }

    private void executeLoadingDonativosTask() {
        sharedPrefManager = SharedPrefManager.getInstance(getContext());
        String username = sharedPrefManager.getUser().getUsername();
        loadingDonativoByMensageiroTask = new LoadingDonativoByMensageiroTask(getContext(), username);
        loadingDonativoByMensageiroTask.delegate = new AsyncResponse<List<Donativo>>() {

            @Override
            public void processFinish(List<Donativo> output) {
                if (output.size() < 1) {
                    showListEmpty();

                } else {
                    donativos = output;
                    showListCampanhas();
                    donativosAdapter = new DonativosAdapter(donativos, getActivity());
                    recyclerView.setAdapter(donativosAdapter);
                    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), clickListener));
                    searchView.setOnQueryTextListener(MainSearchMyColetas.this);
                }
            }
        };

        loadingDonativoByMensageiroTask.execute();
    }

    /**
     * Auxiliar para mostrar fragmento para lista vazia.
     */
    private void showListEmpty() {
        view.findViewById(R.id.no_internet_fragment).setVisibility(View.GONE);
        view.findViewById(R.id.loading_panel_my_coletas).setVisibility(View.GONE);
        view.findViewById(R.id.container_fragment_my_coletas).setVisibility(View.GONE);
        view.findViewById(R.id.empty_list).setVisibility(View.VISIBLE);
    }

    /**
     * Auxiliar para mostrar fragmento de sem conexão quando não houver internet no device.
     */
    private void setVisibleNoConnection() {
        view.findViewById(R.id.no_internet_fragment).setVisibility(View.VISIBLE);
        view.findViewById(R.id.loading_panel_my_coletas).setVisibility(View.GONE);
        view.findViewById(R.id.container_fragment_my_coletas).setVisibility(View.GONE);
        view.findViewById(R.id.empty_list).setVisibility(View.GONE);
    }

    /**
     * Auxiliar para mostrar lista de campanhas e esconder demais fragmentos.
     */
    private void showListCampanhas() {
        view.findViewById(R.id.no_internet_fragment).setVisibility(View.GONE);
        view.findViewById(R.id.loading_panel_my_coletas).setVisibility(View.GONE);
        view.findViewById(R.id.container_fragment_my_coletas).setVisibility(View.VISIBLE);
        view.findViewById(R.id.empty_list).setVisibility(View.GONE);
    }
}
