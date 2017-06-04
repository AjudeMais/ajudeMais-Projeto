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

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.CategoriasAdapter;
import br.edu.ifpb.ajudemais.adapters.DonativosAdapter;
import br.edu.ifpb.ajudemais.domain.Campanha;
import br.edu.ifpb.ajudemais.domain.Donativo;

/**
 * <p>
 * <b>{@link DonativoDetailFragment}</b>
 * </p>
 * <p>
 * Fragmento de detalhes para donativo.
 * <p>
 * <p>
 * </p>
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

public class DonativoDetailFragment extends Fragment {

    private View view;
    private Donativo donativo;
    private TextView descricaoDonativo;
    private TextView nomeInstituicao;
    private RecyclerView recyclerView;
    private DonativosAdapter donativosAdapter;

    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_donativo_detail, container, false);

        Intent intentCampanha = getActivity().getIntent();
        donativo = (Donativo) intentCampanha.getSerializableExtra("Donativo");
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

//        descricaoDonativo = (TextView) getView().findViewById(R.id.tv_campanha_detail_descricao);
//        nomeInstituicao = (TextView) getView().findViewById(R.id.tv_campanha_detail_inst_name);
//
//        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_list_donativos);
//        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layout);

    }
}

