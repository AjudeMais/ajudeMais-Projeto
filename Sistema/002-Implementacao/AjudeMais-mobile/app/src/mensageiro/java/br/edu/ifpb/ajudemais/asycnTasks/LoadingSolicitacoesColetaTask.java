package br.edu.ifpb.ajudemais.asycnTasks;

import android.os.AsyncTask;

import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.remoteServices.DonativoRemoteService;

/**
 * Created by amsv on 04/07/17.
 */

public class LoadingSolicitacoesColetaTask extends AsyncTask<Void, Void, Donativo> {

    private DonativoRemoteService donativoRemoteService;

    @Override
    protected Donativo doInBackground(Void... params) {
        return null;
    }
}
