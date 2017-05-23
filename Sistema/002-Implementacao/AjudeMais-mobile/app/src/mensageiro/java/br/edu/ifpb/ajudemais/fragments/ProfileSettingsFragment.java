package br.edu.ifpb.ajudemais.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.activities.EnderecoActivity;
import br.edu.ifpb.ajudemais.adapters.EnderecoAdapter;
import br.edu.ifpb.ajudemais.domain.Mensageiro;
import br.edu.ifpb.ajudemais.listeners.RecyclerItemClickListener;


public class ProfileSettingsFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView tvNome;
    private TextView tvEmail;
    private TextView tvPhone;
    private Button btnCadEndereco;
    private View view;
    private Mensageiro mensageiro;
    private EnderecoAdapter enderecoAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ProfileSettingsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
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

        tvNome = (TextView) getView().findViewById(R.id.tv_edit_nome);
        tvEmail = (TextView) getView().findViewById(R.id.tv_edit_email);
        tvPhone = (TextView) getView().findViewById(R.id.tv_edit_celular);
        btnCadEndereco = (Button) getView().findViewById(R.id.btn_cad_endereco);

        mensageiro = (Mensageiro) getArguments().getSerializable("Mensageiro");

        if (mensageiro != null) {
            tvPhone.setText(mensageiro.getTelefone());
            tvNome.setText(mensageiro.getNome());
            tvEmail.setText(mensageiro.getConta().getEmail());
        }
        enderecoAdapter = new EnderecoAdapter(mensageiro.getEnderecos(), getActivity());
        recyclerView.setAdapter(enderecoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
        btnCadEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EnderecoActivity.class);
                intent.putExtra("Mensageiro", mensageiro);
                startActivity(intent);
            }
        });
    }


    /**
     *
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(View childView, int position) {
        Intent intent = new Intent(getContext(), EnderecoActivity.class);
        intent.putExtra("Mensageiro", mensageiro);
        intent.putExtra("Index", position);
        startActivity(intent);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
