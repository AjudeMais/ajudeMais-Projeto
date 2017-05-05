package br.edu.ifpb.ajudemais.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ifpb.ajudemais.R;

/**
 * <p>
 * <b>MainSearchCampanhasFragment</b>
 * </p>
 * <p>
 *     MainSearchCampanhasFragment para pesquisa de formas de doar
 * <p>
 *
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public class MainSearchCampanhasFragment extends Fragment{

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_search_campanha,null);
    }
}
