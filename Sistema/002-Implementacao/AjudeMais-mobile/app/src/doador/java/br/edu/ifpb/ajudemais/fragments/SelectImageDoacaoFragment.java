package br.edu.ifpb.ajudemais.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.edu.ifpb.ajudemais.R;

/**
 * <p>
 * <b>{@link SelectImageDoacaoFragment}</b>
 * </p>
 * <p>
 * Fragmento para Selecionar  e visualizar fotos do donativo
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>

 */
public class SelectImageDoacaoFragment extends Fragment {

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

        view = inflater.inflate(R.layout.fragment_select_image_doacao, container, false);

        setHasOptionsMenu(true);

        return view;
    }

}
