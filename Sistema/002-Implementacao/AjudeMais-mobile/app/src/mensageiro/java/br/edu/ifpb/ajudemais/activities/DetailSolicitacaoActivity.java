package br.edu.ifpb.ajudemais.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.edu.ifpb.ajudemais.R;

public class DetailSolicitacaoActivity extends AbstractActivity {

    private Toolbar mToolbar;
    private ImageView imgDonativo;
    private TextView nomeDonativo;
    private TextView descricaoDonativo;
    private TextView quantidadeDonativo;
    private Button buttonAceitar;
    private Button buttonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_solicitacao);
        init();
    }

    @Override
    public void init() {
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        mToolbar.setTitle(R.string.detalhesColeta);
        imgDonativo = (ImageView) findViewById(R.id.imgDonativo);
        nomeDonativo = (TextView) findViewById(R.id.nomeDonativo);
        descricaoDonativo = (TextView) findViewById(R.id.descricaoDonativo);
        quantidadeDonativo = (TextView) findViewById(R.id.quantidadeDonativo);
        buttonAceitar = (Button) findViewById(R.id.buttonAceitar);
        buttonCancelar = (Button) findViewById(R.id.buttonCancelar);
    }
}
