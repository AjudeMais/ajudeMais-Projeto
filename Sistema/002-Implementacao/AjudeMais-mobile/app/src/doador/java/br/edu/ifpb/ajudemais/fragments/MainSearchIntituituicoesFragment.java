package br.edu.ifpb.ajudemais.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import org.springframework.web.client.RestClientException;

import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.activities.InstituicaoActivity;
import br.edu.ifpb.ajudemais.adapters.InstituicoesAdapter;
import br.edu.ifpb.ajudemais.domain.InstituicaoCaridade;
import br.edu.ifpb.ajudemais.dto.LatLng;
import br.edu.ifpb.ajudemais.listeners.RecyclerItemClickListener;
import br.edu.ifpb.ajudemais.remoteServices.InstituicaoRemoteService;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;

/**
 * <p>
 * <b>MainSearchIntituituicoesFragment</b>
 * </p>
 * <p>
 *     MainSearchIntituituicoesFragment para pesquisa de formas de doar
 * <p>
 *
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public class MainSearchIntituituicoesFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {

    private InstituicoesAdapter instituicoesAdapter;
    private static RecyclerView recyclerView;
    private static View view;
    private GoogleMap map;
    private List<InstituicaoCaridade> instituicoes;
    private LatLng latLng;

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

    /**
     *
     */
    @Override
    public void onStart() {
        super.onStart();
        new MainSearchInstituicoesFragmentTask(this).execute();
    }

    /**
     *
     * @param childView View of the item that was clicked.
     * @param position  Position of the item that was clicked.
     */
    @Override
    public void onItemClick(View childView, int position) {
        InstituicaoCaridade instituicaoCaridade = instituicoes.get(position);

        Intent intent = new Intent(getContext(), InstituicaoActivity.class);
        intent.putExtra("instituicao", instituicaoCaridade);
        startActivity(intent);

    }

    /**
     *
     * @param childView View of the item that was long pressed.
     * @param position  Position of the item that was long pressed.
     */
    @Override
    public void onItemLongPress(View childView, int position) {

    }

    /**
     *
     */
    private class MainSearchInstituicoesFragmentTask extends AsyncTask<Void, Void, List<InstituicaoCaridade>> {

        private InstituicaoRemoteService instituicaoRemoteService;
        private String message = null;
        private List<InstituicaoCaridade> instituicoesResult;
        private RecyclerItemClickListener.OnItemClickListener clickListener;
        private SharedPrefManager sharedPrefManager;

        public MainSearchInstituicoesFragmentTask(RecyclerItemClickListener.OnItemClickListener clickListener) {
            instituicaoRemoteService = new InstituicaoRemoteService(getContext());
            this.clickListener = clickListener;
            sharedPrefManager = new SharedPrefManager(getContext());
        }

        /**
         *
         * @param voids
         * @return
         */
        @Override
        protected List<InstituicaoCaridade> doInBackground(Void... voids) {
            try {
                latLng = sharedPrefManager.getLocation();

                if(latLng != null) {
                    instituicoesResult = instituicaoRemoteService.postInstituicoesForLocation(latLng);

                }else {
                    instituicoesResult = instituicaoRemoteService.getInstituicoes();
                }
            } catch (RestClientException e) {
                message = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return instituicoesResult;
        }

        /**
         *
         * @param result
         */
        @Override
        protected void onPostExecute(List<InstituicaoCaridade> result) {
            if (result != null) {

                instituicoes = result;
                view.findViewById(R.id.loadingPanelMainSearchInst).setVisibility(View.GONE);
                view.findViewById(R.id.containerViewSearchInst).setVisibility(View.VISIBLE);

                instituicoesAdapter = new InstituicoesAdapter(instituicoes, getActivity());
                recyclerView.setAdapter(instituicoesAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), clickListener));

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
                Toast.makeText(getContext(), "Aconteceu algum erro no servidor!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
