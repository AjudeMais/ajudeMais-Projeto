package br.edu.ifpb.ajudemais.utils;

import android.content.Context;

import br.edu.ifpb.ajudemais.R;

/**
 * <p>
 * <b>br.edu.ifpb.ajudemais.utils</b>
 * </p>
 * <p>
 * <p>
 * Entidade que representa um foto.
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */


public class ProgressDialog {

    private Context context;
    private android.app.ProgressDialog progressDialog;


    public ProgressDialog(Context context){
        this.context = context;
    }

    public void showProgressDialog() {
        if (this.progressDialog == null) {
            this.progressDialog = new android.app.ProgressDialog(context);
            this.progressDialog.setIndeterminate(true);
        }

        this.progressDialog.setMessage(context.getResources().getString(R.string.msgDialogLoading));
        this.progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }


}
