package br.edu.ifpb.ajudemais.mensageiro;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import br.edu.ifpb.ajudemais.ajudemais_mobile.R;

/**
 * Created by rafaelfeitosa on 31/03/17.
 */

public class BuildExample extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout myLayout =
                (RelativeLayout) findViewById(R.id.activity_main);
        myLayout.setBackgroundColor(Color.RED);
    }

}
