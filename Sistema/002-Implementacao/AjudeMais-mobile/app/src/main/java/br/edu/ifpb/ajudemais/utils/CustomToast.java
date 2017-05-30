package br.edu.ifpb.ajudemais.utils;

import android.content.Context;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.mobsandgeeks.saripaar.Validator;

/**
 * <p>
 * <b>{@link CustomToast}</b>
 * </p>
 * <p>
 * <p>
 * Classe auxialiar para insticiar custom Toast com base ba library https://github.com/JohnPersano/SuperToasts
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */


public class CustomToast {

    private static Context context;
    private static CustomToast instance;

    /**
     * Construtor Default
     * @param context
     */
    public CustomToast(Context context) {
       CustomToast.context = context;
    }

    /**
     * Recupera a instância da classe.
     *
     * @param context
     * @return
     */
    public static synchronized CustomToast getInstance(Context context) {
        if (instance == null)
            instance = new CustomToast(context);
        return instance;
    }

    /**
     * Cria Toast simples com somente TEXTO para ser exibindo na parte inferior da tela
     * @param message
     */
    public void createSimpleCustomToast(String message) {
        SuperActivityToast.create(context, new Style())
                .setText(message)
                .setDuration(Style.DURATION_LONG)
                .setFrame(Style.FRAME_LOLLIPOP)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.DARK_GREY))
                .setAnimations(Style.ANIMATIONS_POP).show();
    }
}
