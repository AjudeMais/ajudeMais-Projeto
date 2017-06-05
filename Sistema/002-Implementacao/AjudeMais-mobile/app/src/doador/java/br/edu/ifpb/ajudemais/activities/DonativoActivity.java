package br.edu.ifpb.ajudemais.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.asyncTasks.GetImageTask;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.fragments.DonativoDetailFragment;

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
        }

        fab = (FloatingActionButton) findViewById(R.id.fabCampanhaShare);
        fab.setOnClickListener(this);
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
}
