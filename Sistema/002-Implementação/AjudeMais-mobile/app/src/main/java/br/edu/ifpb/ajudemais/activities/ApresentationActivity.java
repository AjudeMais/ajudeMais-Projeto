package br.edu.ifpb.ajudemais.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.JwtToken;
import br.edu.ifpb.ajudemais.remoteServices.AuthRemoteService;

public class ApresentationActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentation);
        sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);

        new LoginTask().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.apresentation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_about || super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void updateTokenAccess(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("accessToken", token);
        editor.apply();
    }


    /**
     * @param result
     */
    private void showResult(String result) {
        if (result != null) {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "I got null, something happened!", Toast.LENGTH_LONG).show();
        }
    }

    private class LoginTask extends AsyncTask<Void, Void, JwtToken> {

        private String message = null;
        private Conta conta;
        private JwtToken jwtToken;
        private AuthRemoteService authRemoteService;
        private String userName;
        private String password;

        public LoginTask() {
        }

        @Override
        protected void onPreExecute() {
            authRemoteService = new AuthRemoteService();
            userName = sharedPref.getString("userName", null);
            password = sharedPref.getString("password", null);
        }

        @Override
        protected JwtToken doInBackground(Void... params) {

            if (userName != null && password != null) {
                try {

                    conta = new Conta(userName, password);
                    jwtToken = authRemoteService.createAuthenticationToken(conta);

                    conta = authRemoteService.getUsuario(jwtToken.getToken());

                    return jwtToken;

                } catch (HttpStatusCodeException e) {
                    message = e.getResponseBodyAsString().replace("[", "").replace("]", "");
                    e.printStackTrace();
                } catch (RestClientException e) {
                    message = "Ocorreu um problema, tente novamente mais tarde";
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(JwtToken jwtToken) {

            if (jwtToken != null) {
                updateTokenAccess(jwtToken.getToken());
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
