package br.edu.ifpb.ajudemais.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.activities.DetailSolicitacaoActivity;
import br.edu.ifpb.ajudemais.adapters.DonativosAdapter;
import br.edu.ifpb.ajudemais.asycnTasks.MainSearchSolicitacoesFragmentTask;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.listeners.RecyclerItemClickListener;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;

public class MainSearchSolicitacoesFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private DonativosAdapter donativosAdapter;
    private static RecyclerView recyclerView;
    private static View view;
    private List<Donativo> donativos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AndroidUtil androidUtil;
    private MainSearchSolicitacoesFragmentTask mainSearchSolicitacoesFragmentTask;
    private RecyclerItemClickListener.OnItemClickListener clickListener;
    private SearchView searchView;

    public MainSearchSolicitacoesFragment() {
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_search_solicitacoes, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_list_solicitacoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        view.findViewById(R.id.no_internet_fragment).setVisibility(View.GONE);

        swipeRefreshLayout.setOnRefreshListener(this);

        view.findViewById(R.id.loadingPanelMainSearchSolicitacoes).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerViewSearchSolicitacoes).setVisibility(View.GONE);
        view.findViewById(R.id.empty_list).setVisibility(View.GONE);

        return view;
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
            executeLoadingSolicitacoesTask();
        } else {
            setVisibleNoConnection();
        }
    }

    @Override
    public void onRefresh() {
        this.clickListener = this;
        androidUtil = new AndroidUtil(getContext());
        if (androidUtil.isOnline()) {
            executeLoadingSolicitacoesTask();
        } else {
            setVisibleNoConnection();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Donativo> filteredModelList = filter(donativos, newText);

        if (filteredModelList.size() < 1) {
            showListEmpty();
        } else {
            showListDonativos();
            donativosAdapter.setFilter(filteredModelList);

        }
        return true;
    }


    @Override
    public void onItemClick(View childView, int position) {
        Donativo donativo = donativos.get(position);
        Intent intent = new Intent(getContext(), DetailSolicitacaoActivity.class);
        intent.putExtra("donativo", donativo);
        startActivity(intent);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }

    /**
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        if (donativosAdapter != null) {
                            donativosAdapter.setFilter(donativos);
                        }
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });
    }

    /**
     * Filtra o donativo com base no nome
     * @param models
     * @param query
     * @return
     */
    private List<Donativo> filter(List<Donativo> models, String query) {
        query = query.toLowerCase();
        final List<Donativo> filteredModelList = new ArrayList<>();
        if (models != null) {
            for (Donativo model : models) {
                final String text = model.getNome().toLowerCase();
                if (text.contains(query)) {
                    filteredModelList.add(model);
                }
            }
        }
        return filteredModelList;
    }

    private void showListEmpty() {
        view.findViewById(R.id.no_internet_fragment).setVisibility(View.GONE);
        view.findViewById(R.id.loadingPanelMainSearchSolicitacoes).setVisibility(View.GONE);
        view.findViewById(R.id.containerViewSearchSolicitacoes).setVisibility(View.GONE);
        view.findViewById(R.id.empty_list).setVisibility(View.VISIBLE);
    }

    private void showListDonativos() {
        view.findViewById(R.id.no_internet_fragment).setVisibility(View.GONE);
        view.findViewById(R.id.loadingPanelMainSearchSolicitacoes).setVisibility(View.GONE);
        view.findViewById(R.id.containerViewSearchSolicitacoes).setVisibility(View.VISIBLE);
        view.findViewById(R.id.empty_list).setVisibility(View.GONE);
    }

    private void executeLoadingSolicitacoesTask() {
        mainSearchSolicitacoesFragmentTask = new MainSearchSolicitacoesFragmentTask(getContext());
        mainSearchSolicitacoesFragmentTask.delegate = new AsyncResponse<List<Donativo>>() {
            @Override
            public void processFinish(List<Donativo> output) {
                if (output.size() < 1) {
                    showListEmpty();
                } else {
                    donativos = output;
                    showListDonativos();
                    donativosAdapter = new DonativosAdapter(donativos, getActivity());
                    recyclerView.setAdapter(donativosAdapter);
                    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), clickListener));
                    searchView.setOnQueryTextListener(MainSearchSolicitacoesFragment.this);
                }
            }
        };
        mainSearchSolicitacoesFragmentTask.execute();
    }

    private void setVisibleNoConnection() {
        view.findViewById(R.id.no_internet_fragment).setVisibility(View.VISIBLE);
        view.findViewById(R.id.loadingPanelMainSearchSolicitacoes).setVisibility(View.GONE);
        view.findViewById(R.id.containerViewSearchSolicitacoes).setVisibility(View.GONE);
        view.findViewById(R.id.empty_list).setVisibility(View.GONE);
    }
}
