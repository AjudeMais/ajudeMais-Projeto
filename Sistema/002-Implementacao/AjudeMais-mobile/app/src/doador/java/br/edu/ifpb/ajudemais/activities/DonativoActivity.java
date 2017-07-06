package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.asyncTasks.GetImageTask;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.fragments.DonativoDetailFragment;
import br.edu.ifpb.ajudemais.permissionsPolyce.CallPhoneDevicePermission;

import static br.edu.ifpb.ajudemais.permissionsPolyce.CallPhoneDevicePermission.MY_PERMISSIONS_REQUEST_CALL_PHONE_PERMISSION;

/**
 * <p>
 * <b>{@link DonativoActivity}</b>
 * </p>
 * <p>
 * Activity para genreciar detalhes do donativo.
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a> and
 */
public class DonativoActivity extends BaseActivity implements View.OnClickListener {

    private Donativo donativo;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;
    private Toolbar mToolbar;
    private GetImageTask getImageTask;
    private ImageView imageHeader;
    private ProgressBar progressBar;
    private CallPhoneDevicePermission callPhoneDevicePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donativo);
        init();
        if (!isDestroyed()) {
            DonativoDetailFragment fragment = new DonativoDetailFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.donativo_detail_fragment, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void init() {
        initProperties();
        callPhoneDevicePermission = new CallPhoneDevicePermission(this);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        donativo = (Donativo) getIntent().getSerializableExtra("Donativo");
        progressBar = (ProgressBar) findViewById(R.id.progress_presentation);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(donativo.getNome());
        imageHeader = (ImageView) findViewById(R.id.header_logo);

        if (donativo.getFotosDonativo() != null && donativo.getFotosDonativo().size() > 0) {
            executeLoadingPhotoTask(donativo.getFotosDonativo().get(0).getNome());
        }else {
            progressBar.setVisibility(View.GONE);
            imageHeader.setVisibility(View.VISIBLE);
        }

        fab = (FloatingActionButton) findViewById(R.id.fabCampanhaShare);
        fab.setOnClickListener(this);

        setActionCollapsingTootlbar();
    }

    private void setActionCollapsingTootlbar(){
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    imageHeader.setVisibility(View.GONE);
                } else if(isShow) {
                    imageHeader.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
    }

    /**
     * Executa task para carrega foto do donativo
     *
     * @param imageName
     */
    private void executeLoadingPhotoTask(final String imageName) {
        getImageTask = new GetImageTask(this, imageName);
        getImageTask.setProgressAtivo(false);
        getImageTask.delegate = new AsyncResponse<byte[]>() {
            @Override
            public void processFinish(byte[] output) {
                Bitmap bitmap = androidUtil.convertBytesInBitmap(output);
                imageHeader.setVisibility(View.VISIBLE);
                imageHeader.setImageBitmap(bitmap);
                progressBar.setVisibility(View.GONE);
            }
        };

        getImageTask.execute();
    }

    /**
     * Implementação para controlar operações na action bar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(DonativoActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE_PERMISSION : {
                if (callPhoneDevicePermission.isCallPhonePermissionGranted()){
                    callPhoneDevicePermission.callPhone(donativo.getMensageiro().getTelefone());
                }
                break;
            }
        }
    }

}
