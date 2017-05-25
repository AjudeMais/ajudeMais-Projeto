package br.edu.ifpb.ajudemais.refactoring;

//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.support.annotation.NonNull;
//import android.support.design.widget.NavigationView;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.widget.Toolbar;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.facebook.AccessToken;
//import com.facebook.Profile;
//import com.facebook.login.LoginManager;
//
//import br.edu.ifpb.ajudemais.R;
//import br.edu.ifpb.ajudemais.activities.LoginActivity;
//import br.edu.ifpb.ajudemais.activities.ProfileSettingsActivity;
//import br.edu.ifpb.ajudemais.domain.Conta;
//import br.edu.ifpb.ajudemais.permissionsPolyce.WriteStoreDevicePermission;
//import br.edu.ifpb.ajudemais.storage.SharedPrefManager;
//import br.edu.ifpb.ajudemais.util.FacebookAccount;

/**
 * <p>
 * <b>{@link DrawerMenuDoadorActivity}</b>
 * </p>
 * <p>
 * <p>
 * Activity Fornece alguns métodos padrões
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */


public class DrawerMenuDoadorActivity{
//        extends LocationActivity implements NavigationView.OnNavigationItemSelectedListener {

//    protected DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mToggle;
//    protected NavigationView mNavigationView;
//    private Toolbar mToolbar;
//
//    protected TextView tvUserName;
//    protected TextView tvEmail;
//    protected ImageView profilePhoto;
//    private RelativeLayout componentHeader;
//
//    protected Conta conta;
//    protected Bitmap bitmap;
//
//    private WriteStoreDevicePermission writeStoreDevicePermission;
//
//
//    @Override
//    public void init() {
//        super.init();
//
//        writeStoreDevicePermission = new WriteStoreDevicePermission(this);
//
//        mToolbar = (Toolbar) findViewById(R.id.nav_action);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
//        mNavigationView = (NavigationView) findViewById(R.id.menuNav);
//
//        View hView = mNavigationView.getHeaderView(0);
//        profilePhoto = (ImageView) hView.findViewById(R.id.photoProfile);
//        componentHeader = (RelativeLayout) hView.findViewById(R.id.background_header);
//        tvUserName = (TextView) hView.findViewById(R.id.tvUserNameProfile);
//        tvEmail = (TextView) hView.findViewById(R.id.tvEmailProfile);
//
//        setUpAccount();
//        setUpToggle();
//        setupNavDrawer();
//    }
//
//    /**
//     * Set Configuração para Navegation Drawer
//     */
//    protected void setupNavDrawer() {
//        final ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            if (mNavigationView != null && mDrawerLayout != null) {
//
//                mNavigationView.setNavigationItemSelectedListener(
//                        new NavigationView.OnNavigationItemSelectedListener() {
//                            @Override
//                            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                                menuItem.setChecked(true);
//                                mDrawerLayout.closeDrawers();
//                                onNavDrawerItemSelected(menuItem);
//                                return true;
//                            }
//                        });
//            }
//        }
//    }
//
//
//    /**
//     * Set as informações do usuário logado no app
//     */
//    protected void setUpAccount() {
//
//        conta = (Conta) getIntent().getSerializableExtra("Conta");
//
//        if (conta == null) {
//            conta = SharedPrefManager.getInstance(this).getUser();
//        }
//        if (conta != null) {
//            tvUserName.setText(Profile.getCurrentProfile() != null ? Profile.getCurrentProfile().getName() : conta.getUsername());
//            tvEmail.setText(conta.getEmail());
//        }
//
//        if (AccessToken.getCurrentAccessToken() != null && AccessToken.getCurrentAccessToken().getUserId() != null) {
//            bitmap = FacebookAccount.getProfilePictureUser();
//        } else {
//            if (getIntent().hasExtra("ImageByteArray") && getIntent().getByteArrayExtra("ImageByteArray") != null) {
//
//                if (writeStoreDevicePermission.isStoragePermissionGranted()) {
//                    bitmap = androidUtil.convertBytesInBitmap(getIntent().getByteArrayExtra("ImageByteArray"));
//                    capturePhotoUtils.saveToInternalStorage(bitmap);
//                }
//
//            } else {
//                bitmap = capturePhotoUtils.loadImageFromStorage();
//            }
//        }
//
//        if (bitmap != null) {
//            profilePhoto.setImageBitmap(bitmap);
//        }
//
//        callActivityEditProfile();
//    }
//
//
//    /**
//     * Chama a activity edit profile ao clicar no header no menu drawer.
//     */
//    private void callActivityEditProfile() {
//        componentHeader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(DrawerMenuDoadorActivity.this, ProfileSettingsActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        });
//
//    }
//
//    /**
//     * @param menuItem
//     */
//    private void onNavDrawerItemSelected(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//
//            case R.id.nav_config:
//
//                break;
//
//            case R.id.nav_notificacoes:
//                break;
//            case R.id.nav_sair:
//                if (AccessToken.getCurrentAccessToken() != null) {
//                    LoginManager.getInstance().logOut();
//                } else {
//                    SharedPrefManager.getInstance(this).clearSharedPrefs();
//                }
//                goToLoginScreen();
//                capturePhotoUtils.deleteImageProfile();
//                break;
//
//        }
//    }
//
//
//    /**
//     * Redireciona APP para a Activity de Login
//     */
//    private void goToLoginScreen() {
//        Intent intent = new Intent();
//        intent.setClass(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
//    }
//
//
//    /**
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        return mToggle.onOptionsItemSelected(item);
//
//    }
//
//    /**
//     * Set Action Bar na tela de exibição.
//     */
//    protected void setUpToggle() {
//        setSupportActionBar(mToolbar);
//
//
//        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
//
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
//
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//    }
//
//    /**
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        return false;
//    }
//
//
//    /**
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case ACCESS_FINE_LOCATION_INTENT_ID: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    if (mGoogleApiClient == null) {
//                        initGoogleAPIClient();
//                    }
//                    showTurnOnGpsDialog();
//
//                } else {
//                    Toast.makeText(DrawerMenuDoadorActivity.this, getString(R.string.permissionDenied), Toast.LENGTH_SHORT).show();
//                }
//                break;
//            }
//            case WriteStoreDevicePermission.REQUEST_CODE_STORE_PERMISSION: {
//                if (getIntent().hasExtra("ImageByteArray") && getIntent().getByteArrayExtra("ImageByteArray") != null) {
//                    bitmap = androidUtil.convertBytesInBitmap(getIntent().getByteArrayExtra("ImageByteArray"));
//                    capturePhotoUtils.saveToInternalStorage(bitmap);
//                    profilePhoto.setImageBitmap(bitmap);
//                }
//                break;
//            }
//        }
//    }
}
