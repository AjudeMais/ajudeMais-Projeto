package br.edu.ifpb.ajudemais.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * <p>
 * <b>br.edu.ifpb.ajudemais.utils</b>
 * </p>
 * <p>
 * <p>
 * Utilidade para salvar uma image local
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>**/

public class CapturePhotoUtils {

    public void saveToInternalStorage(Bitmap image){
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("AJUDEMAIS",
                    "Error creating media file, check storage permissions: ");
            return;
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("AJUDEMAIS", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("AJUDEMAIS", "Error accessing file: " + e.getMessage());
        }

    }

    public boolean deleteImageProfile(){
        File file =new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files", "profilePhoto.png");

       return file.delete();

    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String mImageName="profilePhoto.png";
        return new File(mediaStorageDir.getPath() + File.separator + mImageName);
    }

    public Bitmap loadImageFromStorage()
    {

        try {
            File file =new File(Environment.getExternalStorageDirectory()
                    + "/Android/data/"
                    + getApplicationContext().getPackageName()
                    + "/Files", "profilePhoto.png");
            Bitmap profilePhoto = BitmapFactory.decodeStream(new FileInputStream(file));
            return  profilePhoto;
        }

        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;

    }
}
