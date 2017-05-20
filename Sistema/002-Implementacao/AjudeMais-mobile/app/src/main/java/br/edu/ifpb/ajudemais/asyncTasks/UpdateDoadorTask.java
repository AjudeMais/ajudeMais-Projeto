package br.edu.ifpb.ajudemais.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.domain.Imagem;
import br.edu.ifpb.ajudemais.remoteServices.DoadorRemoteService;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;
import br.edu.ifpb.ajudemais.utils.ProgressDialog;

/**
 * <p>
 * <b>br.edu.ifpb.ajudemais.asyncTasks</b>
 * </p>
 * <p>
 * <p>
 * Entidade que representa um foto.
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */


public class UpdateDoadorTask extends AsyncTask<Void, Void, Doador> {

    /**
     *
     */
    public AsyncResponse<Doador> delegate = null;

    private String message = null;
    private AndroidUtil androidUtil;
    private ProgressDialog progressDialog;
    private DoadorRemoteService doadorRemoteService;
    private Context context;
    private Doador doador;


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
    public UpdateDoadorTask(Context context, Doador doador) {
        this.context = context;
        this.doador = doador;
        this.progressDialog = new ProgressDialog(context);
        this.doadorRemoteService = new DoadorRemoteService(context);
    }

    /**
     * @param params
     * @return
     */
    @Override
    protected Doador doInBackground(Void... params) {
        try {

            if (androidUtil.isOnline()) {
                doador = doadorRemoteService.updateDoador(doador);
            } else {
            }
        } catch (RestClientException e) {
            message = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doador;
    }

    /**
     *
     * @param doador
     */
    @Override
    protected void onPostExecute(Doador doador) {
        progressDialog.dismissProgressDialog();
        if (message != null) {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show();
        }else {
            delegate.processFinish(doador);
            Toast.makeText(context, context.getString(R.string.updatedImage), Toast.LENGTH_LONG).show();
        }
    }
}
