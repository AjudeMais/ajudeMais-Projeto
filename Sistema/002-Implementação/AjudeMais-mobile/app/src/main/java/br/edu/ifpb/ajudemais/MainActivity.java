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
    private ImageView profilePhoto;
    private Toolbar mToolbar;

    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private TextView tvUserName;
    private TextView tvEmail;
    private static final int PICK_IMAGE_ID = 234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setSupportActionBar(mToolbar);


        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        View hView = mNavigationView.getHeaderView(0);
        profilePhoto = (ImageView) hView.findViewById(R.id.photoProfile);
        tvUserName = (TextView) hView.findViewById(R.id.tvUserNameProfile);
        tvEmail = (TextView) hView.findViewById(R.id.tvEmailProfile);

        setDataUserLoggedIn();

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(getApplicationContext());
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
            }
        });


    }


    private void setDataUserLoggedIn() {

        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        String userName = sharedPref.getString("userName", null);
        String email = sharedPref.getString("email", null);

        if (userName != null && email != null) {
            tvUserName.setText(userName);
            tvEmail.setText(email);

        }
    }


    /**
     * Inicializa componentes como editText,botões, imageView e etc.
     */
    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE_ID) {
                if (data.getExtras() == null) {
                    Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    profilePhoto.setImageBitmap(bitmap);
                } else {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    profilePhoto.setImageBitmap(photo);
                }
            }
        }
    }


//    private class HttpPostRequestTask extends AsyncTask<Void, Void, Donor> {
//
//        private Context context;
//
//        public HttpPostRequestTask(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        protected Donor doInBackground(Void... params) {
//
//            try {
//                Donor doador = new Donor();
//                // doador.setNome("Zefão salvei agora");
//                doadorRemoteService = new DonorRemoteService();
//                doador = doadorRemoteService.salvarDoador(doador);
//                return doador;
//
//            } catch (HttpStatusCodeException e) {
//                final String message = e.getResponseBodyAsString().replace("[", "").replace("]","");
//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//                    }
//                });
//
//
//            } catch (RestClientException e) {
//                Toast.makeText(getApplication(), "Ocorreu um erro enesperado no sistema aguarde e tente novamente.", Toast.LENGTH_LONG).show();
//            } catch (Exception e) {
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Donor doador) {
//            Log.e("DOADOR", String.valueOf(doador));
//
//            super.onPostExecute(doador);
//        }
//
//
//    }
}
