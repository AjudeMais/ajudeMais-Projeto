package br.edu.ifpb.ajudemais.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ifpb.ajudemais.R;

/**
 * Created by Franck on 4/27/17.
 */

public class MainSearchCampanhasFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_search_campanha,null);
    }
}
