package br.edu.ifpb.ajudemais.remoteServices;

import android.content.Context;

import br.edu.ifpb.ajudemais.domain.Imagem;

/**
 * <p>
 * <b>br.edu.ifpb.ajudemais.remoteServices</b>
 * </p>
 * <p>
 * <p>
 * Entidade que representa um foto.
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */


public class ImagemStorageRemoteService extends AbstractRemoteService{

    /**
     * construtor
     *
     * @param context
     */
    public ImagemStorageRemoteService(Context context) {
        super(context);
    }

    public Imagem uploadImage(){
        return null;
    }
}
