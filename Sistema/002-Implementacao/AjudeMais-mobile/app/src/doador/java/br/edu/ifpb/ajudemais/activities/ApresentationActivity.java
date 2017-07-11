package br.edu.ifpb.ajudemais.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.asyncTasks.GetCampanhaByIdTask;
import br.edu.ifpb.ajudemais.asyncTasks.GetDonativoByIdTask;
import br.edu.ifpb.ajudemais.asyncTasks.LoginTask;
import br.edu.ifpb.ajudemais.domain.Campanha;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Donativo;

/**
 * <p>
 * <b>ApresentationActivity</b>
 * </p>
 * <p>
 * Activity para controlar tele inicial de carregamento do aplicativo.
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class ApresentationActivity extends BaseActivity {


    private LoginTask loginTask;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentation);

        init();

        ProgressBar mBar = (ProgressBar) findViewById(R.id.progress_presentation);
        mBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        executeLoginTask();
    }

    @Override
    public void init() {
        initProperties();
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.apresentation_menu, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_about || super.onOptionsItemSelected(item);
    }


    /**
     * Executa asycn Task para renovar login doador.
     */
    private void executeLoginTask() {
        loginTask = new LoginTask(this);

        loginTask.delegate = new AsyncResponse<Conta>() {
            @Override
            public void processFinish(Conta conta) {
                if (conta != null) {
                    redirectNotification();

                    Intent intent = new Intent();
                    intent.setClass(ApresentationActivity.this, MainActivity.class);
                    intent.putExtra("Conta", conta);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent();
                    intent.setClass(ApresentationActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            }
        };

        loginTask.execute();
    }


    private void redirectNotification() {

        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            String tipoAs = startingIntent.getStringExtra("tipo");
            String idStr = startingIntent.getStringExtra("id");

            if (tipoAs != null && idStr != null) {
                Long id = Long.parseLong(idStr);

                switch (tipoAs) {
                    case "CAMPANHA":
                        executeLoadingCampanhaByIdTask(id);
                        break;
                    case "DOACAO":
                        executeLoadingDonativoByIdTask(id);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Recupera campanha de notificação.
     *
     * @param campanhaId
     */
    private void executeLoadingCampanhaByIdTask(Long campanhaId) {
        GetCampanhaByIdTask getCampanhaByIdTask = new GetCampanhaByIdTask(getApplicationContext(), campanhaId);
        getCampanhaByIdTask.delegate = new AsyncResponse<Campanha>() {
            @Override
            public void processFinish(Campanha output) {
                Campanha campanha = output;
                Intent intent = new Intent(getBaseContext(), CampanhaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("campanha", campanha);
                startActivity(intent);
            }
        };
        getCampanhaByIdTask.execute();
    }


    /**
     * Recupera donativo da notificação.
     *
     * @param donativoId
     */
    private void executeLoadingDonativoByIdTask(final Long donativoId) {
        GetDonativoByIdTask getDonativoByIdTask = new GetDonativoByIdTask(getApplicationContext(), donativoId);
        getDonativoByIdTask.delegate = new AsyncResponse<Donativo>() {
            @Override
            public void processFinish(Donativo output) {
                Donativo donativo = output;
                Log.e("AJUDEMAIS", donativo.toString());
                Intent intent = new Intent(getApplicationContext(), DonativoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("Donativo", donativo);
                startActivity(intent);
            }
        };
        getDonativoByIdTask.execute();
    }
}

