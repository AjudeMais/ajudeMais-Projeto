package br.edu.ifpb.ajudemais.asycnTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import java.util.List;

import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.domain.Mensageiro;
import br.edu.ifpb.ajudemais.remoteServices.DonativoRemoteService;
import br.edu.ifpb.ajudemais.remoteServices.MensageiroRemoteService;

/**
 * <p>
 * <b>br.edu.ifpb.ajudemais.asycnTasks</b>
 * </p>
 * <p>
 * <p>
 * AsycnTask carrega donativo por mensageiro.
 * </p>
 *
 */


public class LoadingDonativoByMensageiroTask extends AsyncTask<Void, Void, List<Donativo>>{

    public AsyncResponse<List<Donativo>> delegate = null;
    private DonativoRemoteService donativoRemoteService;
    private String message;
    private List<Donativo> donativos;
    private Context context;
    private String userName;

    public LoadingDonativoByMensageiroTask(Context context, String userName){
        this.context = context;
        this.userName = userName;
    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        donativoRemoteService = new DonativoRemoteService(context);
    }

    /**
     * @param params
     * @return
     */
    @Override
    protected List<Donativo> doInBackground(Void... params) {
        try {
            donativos = donativoRemoteService.findByMensageiro(userName);
        } catch (RestClientException e) {
            message = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donativos;
    }

    /**
     *
     */
    @Override
    protected void onPostExecute(List<Donativo> donativos) {
        if (donativos != null && message == null) {
            delegate.processFinish(donativos);
        }else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
