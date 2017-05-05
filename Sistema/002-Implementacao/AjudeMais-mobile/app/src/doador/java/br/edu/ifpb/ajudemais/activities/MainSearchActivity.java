package br.edu.ifpb.ajudemais.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.TabFragmentMainSearch;


/**
 * <p>
 * <b>MainSearchActivity</b>
 * </p>
 * <p>
 *     Activity para controlar tela de consultas para doação.
 * <p>
 *
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>

 */
public class MainSearchActivity extends AbstractAsyncActivity {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerViewMainSearch, new TabFragmentMainSearch()).commit();

    }

}
