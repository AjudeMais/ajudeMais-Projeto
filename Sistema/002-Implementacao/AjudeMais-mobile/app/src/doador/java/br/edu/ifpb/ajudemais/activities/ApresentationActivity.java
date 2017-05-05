package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.remoteServices.AuthRemoteService;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;

/**
 * <p>
 * <b>ApresentationActivity</b>
 * </p>
 * <p>
 *     Activity para controlar tele inicial de carregamento do aplicativo.
 * <p>
 *
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

public class ApresentationActivity extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentation);

        ProgressBar mBar= (ProgressBar) findViewById(R.id.progress_presentation);
        mBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        new LoginTask(this).execute();

    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.apresentation_menu, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_about || super.onOptionsItemSelected(item);
    }

    /**
     *
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * @param result
     */
    private void showResult(String result) {
        if (result != null) {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Aconteceu algum erro no servidor!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     */
    private class LoginTask extends AsyncTask<Void, Void, Conta> {

        private String message = null;
        private Conta conta;
        private AuthRemoteService authRemoteService;
        private Context context;

        public LoginTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            authRemoteService = new AuthRemoteService(context);
        }

        @Override
        protected Conta doInBackground(Void... params) {
            try {
                if (authRemoteService.logged()) {

                        conta = SharedPrefManager.getInstance(context).getUser();
                        return conta;
                }
            } catch (RestClientException e) {
                message = e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         *
         * @param conta
         */
        @Override
        protected void onPostExecute(Conta conta) {

            if (conta != null) {
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

                if (message != null) {
                    showResult(message);
                }
            }

        }
    }
}
