package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.TabFragment;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.utils.ImagePicker;

/**
 * <p>
 * <b>MainActivity</b>
 * </p>
 * <p>
 * <p>
 *  Activity inicial para Mensageiro
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class MainActivity extends AbstractActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private FloatingActionButton fab;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        findViewById(R.id.containerView).setVisibility(View.GONE);

        init();
        setUpAccount();
        setUpToggle();
        setupNavDrawer();

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        new LoadingColetasTask().execute();

    }


    /**
     * Set as informações do usuário logado no app
     */
    protected void setUpAccount() {
        View hView = mNavigationView.getHeaderView(0);
        profilePhoto = (ImageView) hView.findViewById(R.id.photoProfile);
        tvUserName = (TextView) hView.findViewById(R.id.tvUserNameProfile);
        tvEmail = (TextView) hView.findViewById(R.id.tvEmailProfile);

        conta = (Conta) getIntent().getSerializableExtra("Conta");
        if (conta != null ) {
            tvUserName.setText(conta.getUsername());
            tvEmail.setText(conta.getEmail());
        }
        Bitmap bitmap = capturePhotoUtils.loadImageFromStorage();

        if (bitmap != null) {
            profilePhoto.setImageBitmap(bitmap);
        }

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(getApplicationContext());
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
            }
        });
    }


    /**
     *Classe auxiliar para acessar Serviços
     */
    private class LoadingColetasTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String message) {
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            findViewById(R.id.containerView).setVisibility(View.VISIBLE);
            Toast.makeText(getApplication(), "CARREGADO", Toast.LENGTH_LONG).show();
        }

    }
}
