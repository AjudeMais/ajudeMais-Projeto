package br.edu.ifpb.ajudemais.activities;

/**
 * Created by rafaelfeitosa on 12/04/17.
 * Créditos:
 * @author Roy Clarkson
 * @author Pierre-Yves Ricau
 */
public interface AsyncActivity {

    /**
     * Chama Loading
     */
    public void showLoadingProgressDialog();

    /**
     * Cria Loading modal
     * @param message
     */
    public void showProgressDialog(CharSequence message);

    /**
     * Fecha Progress Dialog
     */
    public void dismissProgressDialog();
}
