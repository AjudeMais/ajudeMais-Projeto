import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.ifpb.ajudemais.R;

/**
 * Created by rafaelfeitosa on 02/04/17.
 */

public class Main_tab01 extends Fragment {

    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tab01,null);

        mTextView = (TextView) view.findViewById(R.id.textoExamplo);
        mTextView.setText("CAMPANHAS DO DOADOR AQUI");

        return view;
    }


}
