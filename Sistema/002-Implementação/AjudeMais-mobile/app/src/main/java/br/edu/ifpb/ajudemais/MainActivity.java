package br.edu.ifpb.ajudemais;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ifpb.ajudemais.util.ImagePicker;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ImageView fotoPerfilUsuario;

    private Toolbar mToolbar;

    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private TextView tvNomeUsuario;
    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.string.abrir,
                R.string.fechar);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        View hView =  mNavigationView.getHeaderView(0);
        fotoPerfilUsuario = (ImageView) hView.findViewById(R.id.fotoPerfilUsuario);
        tvNomeUsuario = (TextView) hView.findViewById(R.id.tvNomeUsuarioPerfil);
        tvEmail = (TextView) hView.findViewById(R.id.tvEmailUsuarioPerfil);

        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        String nomeUsuario = sharedPref.getString("nomeUsuario", null);
        String emailUsuario = sharedPref.getString("emailUsuario", null);

        if (nomeUsuario != null && emailUsuario != null) {
            tvNomeUsuario.setText(nomeUsuario);
            tvEmail.setText(emailUsuario);

        }

        fotoPerfilUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(getApplicationContext());
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }



    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != RESULT_CANCELED){
            if (requestCode == PICK_IMAGE_ID) {
                if (data.getExtras() == null){
                    Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    fotoPerfilUsuario.setImageBitmap(bitmap);
                }else{
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    fotoPerfilUsuario.setImageBitmap(photo);
                }
            }
        }
    }
}
