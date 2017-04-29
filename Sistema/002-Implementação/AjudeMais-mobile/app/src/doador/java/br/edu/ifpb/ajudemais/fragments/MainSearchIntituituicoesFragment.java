package br.edu.ifpb.ajudemais.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.adapters.MainSearchInstituicoesAdapter;
import br.edu.ifpb.ajudemais.domain.InstituicaoCaridade;
import br.edu.ifpb.ajudemais.remoteServices.InstituicaoRemoteService;

/**
 * Created by Franck on 4/27/17.
 */
public class MainSearchIntituituicoesFragment extends Fragment {

    private MainSearchInstituicoesAdapter mainSearchInstituicoesAdapter;
    private static RecyclerView recyclerView;
    private static View view;
    private List<InstituicaoCaridade> instituicoes;


    /**
     *
     */
    public MainSearchIntituituicoesFragment() {
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
        view = inflater.inflate(R.layout.fragment_main_search_inst, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_instituicoes);

        view.findViewById(R.id.loadingPanelMainSearchInst).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerViewSearchInst).setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new MainSearchInstituicoesFragmentTask().execute();
    }

    /**
     *
     */
    private class MainSearchInstituicoesFragmentTask extends AsyncTask<Void, Void, List<InstituicaoCaridade>> {

        private InstituicaoRemoteService instituicaoRemoteService;
        private String message = null;
        private List<InstituicaoCaridade> instituicoesResult;

        public MainSearchInstituicoesFragmentTask() {
            instituicaoRemoteService = new InstituicaoRemoteService(getContext());
        }

        @Override
        protected List<InstituicaoCaridade> doInBackground(Void... voids) {
            try {
                instituicoesResult = instituicaoRemoteService.getInstituicoes();
            } catch (RestClientException e) {
                message = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return instituicoesResult;
        }

        @Override
        protected void onPostExecute(List<InstituicaoCaridade> result) {
            if (result != null) {
                instituicoes = result;
                view.findViewById(R.id.loadingPanelMainSearchInst).setVisibility(View.GONE);
                view.findViewById(R.id.containerViewSearchInst).setVisibility(View.VISIBLE);

                mainSearchInstituicoesAdapter = new MainSearchInstituicoesAdapter(instituicoes, getActivity());
                recyclerView.setAdapter(mainSearchInstituicoesAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            } else {
                showResult(message);
            }
        }

        /**
         * @param result
         */
        private void showResult(String result) {
            if (result != null) {
                Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Aconteceu algum erro no servidor", Toast.LENGTH_LONG).show();
            }
        }
    }
}
