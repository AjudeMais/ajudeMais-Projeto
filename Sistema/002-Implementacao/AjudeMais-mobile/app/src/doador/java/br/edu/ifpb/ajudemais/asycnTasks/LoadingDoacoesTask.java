package br.edu.ifpb.ajudemais.asycnTasks;

import android.content.Context;
import android.os.AsyncTask;

import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.remoteServices.DoadorRemoteService;
import br.edu.ifpb.ajudemais.remoteServices.DonativoRemoteService;
import br.edu.ifpb.ajudemais.utils.ProgressDialog;

/**
 * <p>
 * <b>{@link LoadingDoacoesTask}</b>
 * </p>
 * <p>
 * <p>
 * Asycn para carregar donativos de um doador.
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */


public class LoadingDoacoesTask extends AsyncTask<Void, Void, Donativo> {

    public AsyncResponse<Donativo> delegate = null;
    private String message = null;
    private Doador doador;
    private Donativo donativo;
    private Context context;
    private String username;
    private ProgressDialog progressDialog;
    private DonativoRemoteService donativoRemoteService;

    public LoadingDoacoesTask(Context context, String nameDoador){
        this.context = context;
        
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Donativo doInBackground(Void... params) {
        return null;
    }
}
