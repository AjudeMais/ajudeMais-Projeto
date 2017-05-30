package br.edu.ifpb.ajudemais.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.domain.Mensageiro;
import br.edu.ifpb.ajudemais.remoteServices.MensageiroRemoteService;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;
import br.edu.ifpb.ajudemais.utils.ProgressDialog;

/**
 * Created by amsv on 21/05/17.
 */

public class UpdateMensageiroTask extends AsyncTask<Void, Void, Mensageiro> {

    /**
     *
     */
    public AsyncResponse<Mensageiro> delegate = null;

    private String message = null;
    private ProgressDialog progressDialog;
    private MensageiroRemoteService mensageiroRemoteService;
    private Context context;
    private Mensageiro mensageiro;


    /**
     *
     */
    @Override
    protected void onPreExecute() {
        progressDialog.showProgressDialog();
        super.onPreExecute();

    }


    /**
     * @param context
     */
    public UpdateMensageiroTask(Context context, Mensageiro mensageiro) {
        this.context = context;
        this.mensageiro = mensageiro;
        this.progressDialog = new ProgressDialog(context);
        this.mensageiroRemoteService = new MensageiroRemoteService(context);
    }

    /**
     * @param params
     * @return
     */
    @Override
    protected Mensageiro doInBackground(Void... params) {
        try {
            mensageiro = mensageiroRemoteService.updateMensageiro(mensageiro);
        } catch (RestClientException e) {
            message = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensageiro;
    }

    /**
     * @param mensageiro
     */
    @Override
    protected void onPostExecute(Mensageiro mensageiro) {
        progressDialog.dismissProgressDialog();
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else {
            delegate.processFinish(mensageiro);
        }
    }
}
