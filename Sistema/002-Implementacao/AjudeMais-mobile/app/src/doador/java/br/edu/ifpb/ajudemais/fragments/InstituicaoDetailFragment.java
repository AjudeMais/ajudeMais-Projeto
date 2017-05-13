package br.edu.ifpb.ajudemais.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.CategoriasAdapter;
import br.edu.ifpb.ajudemais.domain.Categoria;
import br.edu.ifpb.ajudemais.domain.InstituicaoCaridade;
import br.edu.ifpb.ajudemais.remoteServices.CategoriaRemoteService;

/**
 * <p>
 * <b>InstituicaoDetailFragment</b>
 * </p>
 * <p>
 * InstituicaoDetailFragment para lista de instituições
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public class InstituicaoDetailFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView descricaoInstituicao;
    private TextView emailInstituicao;
    private TextView logradouroInstituicao;
    private TextView localidadeInstituicao;
    private CardView cardViewEmail;
    private InstituicaoCaridade instituicaoCaridade;
    private CategoriasAdapter categoriasAdapter;
    private TextView listInstituicoes;
    private View view;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_instituicao_detail, container, false);

        Intent intentInstituicao = getActivity().getIntent();
        instituicaoCaridade = (InstituicaoCaridade) intentInstituicao.getSerializableExtra("instituicao");

        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_list);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        //recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), clickListener));
        view.findViewById(R.id.loadingPanelMainSearchInst).setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);


        new LodingListCategoriasTask().execute();

        return view;
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        descricaoInstituicao = (TextView) getView().findViewById(R.id.tv_instituicao_detail_descricao);
        emailInstituicao = (TextView) getView().findViewById(R.id.tv_instituicao_detail_email);
        logradouroInstituicao = (TextView) getView().findViewById(R.id.tv_instituicao_detail_logradouro);
        localidadeInstituicao = (TextView) getView().findViewById(R.id.tv_instituicao_detail_localidade);

        listInstituicoes = (TextView) getView().findViewById(R.id.tv_list_itens_doaveis);

        if (instituicaoCaridade.getItensDoaveis().size() < 1) {
            listInstituicoes.setVisibility(View.GONE);
        }

        cardViewEmail = (CardView) getView().findViewById(R.id.card_view_intituicao_detail_email);


    }

    /**
     *
     */
    @Override
    public void onStart() {
        super.onStart();


        descricaoInstituicao.setText(instituicaoCaridade.getDescricao());
        emailInstituicao.setText(instituicaoCaridade.getConta().getEmail());
        logradouroInstituicao.setText(instituicaoCaridade.getEndereco().getLogradouro() + ", " +
                instituicaoCaridade.getEndereco().getNumero());
        localidadeInstituicao.setText(
                instituicaoCaridade.getEndereco().getBairro() + ", " +
                        instituicaoCaridade.getEndereco().getLocalidade() + " - " +
                        instituicaoCaridade.getEndereco().getUf());

        cardViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(instituicaoCaridade.getConta().getEmail());
            }
        });


    }

    /**
     * Criar intent apropriada para abrir o app do gmail.
     *
     * @param email
     */
    private void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        final PackageManager pm = getActivity().getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        String className = null;
        for (final ResolveInfo info : matches) {
            if (info.activityInfo.packageName.equals("com.google.android.gm")) {
                className = info.activityInfo.name;

                if (className != null && !className.isEmpty()) {
                    break;
                }
            }
        }
        emailIntent.setClassName("com.google.android.gm", className);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[Ajude Mais App]");
        startActivity(emailIntent);
    }


    /**
     *
     */
    private class LodingListCategoriasTask extends AsyncTask<Void, Void, List<Categoria>> {

        private CategoriaRemoteService categoriaRemoteService;
        private List<Categoria> categorias;
        private String message;

        /**
         *
         */
        @Override
        protected void onPreExecute() {
            categoriaRemoteService = new CategoriaRemoteService(getContext());
        }

        /**
         * @param params
         * @return
         */
        @Override
        protected List<Categoria> doInBackground(Void... params) {
            try {
                categorias = categoriaRemoteService.listCategoriasAtivasToInstituicao(instituicaoCaridade.getId());
                return categorias;

            } catch (RestClientException e) {
                message = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return categorias;
        }

        /**
         * @param categorias
         */
        @Override
        protected void onPostExecute(List<Categoria> categorias) {
           recyclerView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.loadingPanelMainSearchInst).setVisibility(View.GONE);

            if (categorias != null) {
                categoriasAdapter = new CategoriasAdapter(categorias, getActivity());
                recyclerView.setAdapter(categoriasAdapter);
            } else {
                showResult(message);
            }

        }

        /**
         * Mostra mensagens retorna durante a operação da task
         *
         * @param result
         */
        private void showResult(String result) {
            if (result != null) {
                Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Aconteceu algum erro no servidor!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
