package br.edu.ifpb.ajudemais;

/**
 * Created by rafaelfeitosa on 12/04/17.
 * Créditos:
 * @author Roy Clarkson
 * @author Pierre-Yves Ricau
 */

public interface AsyncActivity {

    public void showLoadingProgressDialog();

    public void showProgressDialog(CharSequence message);

    public void dismissProgressDialog();
}
