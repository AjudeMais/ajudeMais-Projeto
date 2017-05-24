package br.edu.ifpb.ajudemais.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.springframework.web.client.RestClientException;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.domain.Imagem;
import br.edu.ifpb.ajudemais.domain.Mensageiro;
import br.edu.ifpb.ajudemais.remoteServices.DoadorRemoteService;
import br.edu.ifpb.ajudemais.remoteServices.ImagemStorageRemoteService;
import br.edu.ifpb.ajudemais.remoteServices.MensageiroRemoteService;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;
import br.edu.ifpb.ajudemais.utils.ProgressDialog;

/**
 * <p>
 * <b>{@link UploadImageTask}</b>
 * </p>
 * <p>
 * <p>
 * Async Task para realização do upload de foto.
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class UploadImageTask extends AsyncTask<Void, Void, Imagem> {

    /**
     *
     */
    public AsyncResponse<Imagem> delegate = null;

    private ImagemStorageRemoteService imagemStorageRemoteService;
    private String message = null;
    private AndroidUtil androidUtil;
    private Imagem imagem;
    private ProgressDialog progressDialog;
    private byte[] array;
    private Context context;
    private Doador doador;
    private DoadorRemoteService doadorRemoteService;
    private Mensageiro mensageiro;
    private MensageiroRemoteService mensageiroRemoteService;


    public UploadImageTask(Context context, byte[] array, Doador doador, Mensageiro mensageiro) {
        this.array = array;
        this.doador = doador;
        this.mensageiro = mensageiro;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        androidUtil = new AndroidUtil(context);
        imagemStorageRemoteService = new ImagemStorageRemoteService(context);
        mensageiroRemoteService = new MensageiroRemoteService(context);
        doadorRemoteService = new DoadorRemoteService(context);

    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
        progressDialog.showProgressDialog();
        super.onPreExecute();

    }


    /**
     * @param params
     * @return
     */
    @Override
    protected Imagem doInBackground(Void... params) {
        try {

            if (androidUtil.isOnline()) {
                imagem = imagemStorageRemoteService.uploadImage(array);

                if (doador != null) {
                    if (doador.getFoto() != null) {
                        doador.getFoto().setNome(imagem.getNome());
                    } else {
                        doador.setFoto(imagem);
                    }
                    doador = doadorRemoteService.updateDoador(doador);
                    imagem = doador.getFoto();


                } else if (mensageiro != null) {
                    if (mensageiro.getFoto() != null) {
                        mensageiro.getFoto().setNome(imagem.getNome());
                    } else {
                        mensageiro.setFoto(imagem);
                    }
                    mensageiro = mensageiroRemoteService.updateMensageiro(mensageiro);
                    imagem = mensageiro.getFoto();
                }

            }
        } catch (RestClientException e) {
            message = e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagem;
    }


    @Override
    protected void onPostExecute(Imagem imagem) {
        progressDialog.dismissProgressDialog();

        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, context.getString(R.string.updatedImage), Toast.LENGTH_LONG).show();
            delegate.processFinish(imagem);
        }
    }


}
