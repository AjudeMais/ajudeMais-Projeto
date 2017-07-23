package br.edu.ifpb.ajudemais.asycnTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.utils.CustomToast;

/**
 * <p>
 * <b>{@link LoadingImageFbTask}</b>
 * </p>
 * <p>
 * <p>
 * Asycn Task para carregar imagem.
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class LoadingImageFbTask extends AsyncTask<Void, Void, Bitmap> {

    public AsyncResponse<Bitmap> delegate = null;
    private String message = null;
    private Context context;
    private String url;

    public LoadingImageFbTask(Context context, String url){
        this.context = context;
        this.url = url;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Bitmap doInBackground(Void... voids) {

        try {
            InputStream input = new java.net.URL(url).openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            delegate.processFinish(result);
        }

        if (message != null) {
            CustomToast.getInstance(context).createSuperToastSimpleCustomSuperToast(message);
        }
    }
}
