package br.edu.ifpb.ajudemais.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.activities.CreateAccountHelperActivity;
import br.edu.ifpb.ajudemais.activities.ProfileSettingsActivity;
import br.edu.ifpb.ajudemais.asycnTasks.LoadingImageFbTask;
import br.edu.ifpb.ajudemais.asycnTasks.UpdateDoadorTask;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.asyncTasks.UpdateEstadoDonativoTask;
import br.edu.ifpb.ajudemais.asyncTasks.UploadImageTask;
import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.domain.Imagem;
import br.edu.ifpb.ajudemais.utils.AndroidUtil;
import br.edu.ifpb.ajudemais.utils.CapturePhotoUtils;
import br.edu.ifpb.ajudemais.utils.CustomToast;

/**
 * Created by amsv on 26/04/17.
 */

public class FacebookAccount {


    private static Doador doador;

    /**
     * Método responsável por obter dados de acesso de um usuário do facebook.
     * Dados como: Nome, e-mail e username
     */
    public static void userFacebookData(final Context context, LoginResult loginResult, final Activity activity) {
        doador = new Doador();


        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (response != null) {
                            try {
                                doador.setNome(Profile.getCurrentProfile().getName());
                                doador.setFacebookID(Profile.getCurrentProfile().getId());
                                doador.setCampanhas(null);
                                doador.setConta(new Conta());
                                doador.getConta().setEmail(object.optString("email"));
                                doador.getConta().setUsername(Profile.getCurrentProfile().getName());
                                doador.getConta().setSenha(Profile.getCurrentProfile().getId());
                                doador.getConta().setGrupos(Collections.singletonList("ROLE_DOADOR"));
                                doador.getConta().setAtivo(true);
                                doador.setFoto(new Imagem());
                                doador.getFoto().setNome(Profile.getCurrentProfile().getProfilePictureUri(500,500).toString());
                                executeLoadingImageProfileTask(context, doador.getFoto().getNome());
                                if (doador != null) {
                                    goToFacebookAccountHelperActivity(activity, doador);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private static void goToFacebookAccountHelperActivity(Activity activity, Doador doador) {
        Intent intent = new Intent(activity, CreateAccountHelperActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Doador", doador);
        activity.startActivity(intent);
    }

    private static void executeLoadingImageProfileTask(final Context context, String url){
        LoadingImageFbTask loadingImageFbTask = new LoadingImageFbTask(context, url);
        loadingImageFbTask.delegate = new AsyncResponse<Bitmap>() {
            @Override
            public void processFinish(Bitmap output) {
                CapturePhotoUtils capturePhotoUtils =new CapturePhotoUtils(context);
                capturePhotoUtils.saveToInternalStorage(output);

            }
        };

        loadingImageFbTask.execute();
    }
}
