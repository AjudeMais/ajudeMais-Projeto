package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.InstituicaoCaridade;
import br.edu.ifpb.ajudemais.fragments.InstituicaoDetailFragment;

/**
 *
 */
public class InstituicaoActivity extends AbstractActivity {

    private InstituicaoCaridade instituicao;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicao);

        setUpToolbar();

        instituicao = (InstituicaoCaridade) getIntent().getSerializableExtra("instituicao");

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(instituicao.getNome());

        fab = (FloatingActionButton) findViewById(R.id.fabInstituicaoTel);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + instituicao.getTelefone()));
                try {
                    startActivity(callIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Não foi possível realizar a ligação", Toast.LENGTH_LONG).show();
                }
            }
        });

        InstituicaoDetailFragment fragment = new InstituicaoDetailFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.instituicao_detail_fragment, fragment);
        fragmentTransaction.commit();

    }
}
