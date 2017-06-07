package br.edu.ifpb.ajudemais.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.CategoriasAdapter;
import br.edu.ifpb.ajudemais.domain.Campanha;

/**
 * <p>
 * <b>CampanhaDetailFragment</b>
 * </p>
 * <p>
 * Fragmento de tela para visualização de detalhes de uma campanha.
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public class CampanhaDetailFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView descricaoCampanha;
    private TextView nomeInstituicao;
    private TextView termino;
    private Campanha campanha;
    private CategoriasAdapter categoriasAdapter;
    private TextView labeListInstituicoes;
    private View view;

    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_campanha_detail, container, false);

        setHasOptionsMenu(true);
        return view;
    }


    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intentCampanha = getActivity().getIntent();
        campanha = (Campanha) intentCampanha.getSerializableExtra("campanha");

        descricaoCampanha = (TextView) getView().findViewById(R.id.tv_campanha_detail_descricao);
        nomeInstituicao = (TextView) getView().findViewById(R.id.tv_campanha_detail_inst_name);
        termino = (TextView) getView().findViewById(R.id.tv_campanha_detail_term);
        labeListInstituicoes = (TextView) getView().findViewById(R.id.tv_campanha_list_itens_doaveis);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_campanha_list);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        if (campanha.getItensDoaveis() == null || campanha.getItensDoaveis().size() < 1) {
            labeListInstituicoes.setVisibility(View.GONE);
        }

        categoriasAdapter = new CategoriasAdapter(campanha.getItensDoaveis(), getActivity());
        recyclerView.setAdapter(categoriasAdapter);
    }

    /**
     *
     */
    @Override
    public void onStart() {
        super.onStart();
        descricaoCampanha.setText(campanha.getDescricao());
        nomeInstituicao.setText(campanha.getInstituicaoCaridade().getNome());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(campanha.getDataFim());
        termino.setText(date);
    }
}
