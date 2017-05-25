package br.edu.ifpb.ajudemais.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.InstituicaoCaridade;
import br.edu.ifpb.ajudemais.fragments.InstituicaoDetailFragment;

/**
 * <p>
 * <b>InstituicaoActivity</b>
 * </p>
 * <p>
 * Activity para controlar tela Instituicoes
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public class InstituicaoActivity extends AppCompatActivity {

    private InstituicaoCaridade instituicao;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;
    private Toolbar mToolbar;
    private static final int REQUEST_CALL_PHONE = 1;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicao);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        instituicao = (InstituicaoCaridade) getIntent().getSerializableExtra("instituicao");

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(instituicao.getNome());

        fab = (FloatingActionButton) findViewById(R.id.fabInstituicaoTel);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermissions();

                if (Build.VERSION.SDK_INT < 23) {
                    callPhone();
                }
            }
        });

        if (!isDestroyed()) {
            InstituicaoDetailFragment fragment = new InstituicaoDetailFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.instituicao_detail_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private void callPhone() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + instituicao.getTelefone()));
        try {
            startActivity(callIntent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Não foi possível realizar a ligação", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Checa se o device pertence ao SDK versão 23 para exibir permissão
     */
    protected void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(InstituicaoActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                requestPermission();

            }
        }
    }

    /**
     * Requisita permissão para realizar um ligação no device.
     */
    private void requestPermission() {
        int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
        } else {
            customDialog();
        }
    }

    /**
     * @param
     */
    public void customDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityCompat.requestPermissions(InstituicaoActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CALL_PHONE) {
            callPhone();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
