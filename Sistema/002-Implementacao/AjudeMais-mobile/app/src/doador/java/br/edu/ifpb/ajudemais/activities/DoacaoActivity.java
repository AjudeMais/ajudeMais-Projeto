package br.edu.ifpb.ajudemais.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.asyncTasks.FindByMyLocationActualTask;
import br.edu.ifpb.ajudemais.domain.Categoria;
import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.domain.Endereco;
import br.edu.ifpb.ajudemais.dto.LatLng;
import br.edu.ifpb.ajudemais.permissionsPolyce.AccessCameraAndGalleryDevicePermission;
import br.edu.ifpb.ajudemais.utils.CustomToast;

import static br.edu.ifpb.ajudemais.permissionsPolyce.AccessCameraAndGalleryDevicePermission.REQUEST_CAMERA;
import static br.edu.ifpb.ajudemais.permissionsPolyce.AccessCameraAndGalleryDevicePermission.SELECT_FILE;


/**
 * <p>
 * <b>{@link DoacaoActivity}</b>
 * </p>
 * <p>
 * Activity para finalizar criação de conta utilizando o facebook
 * <p>
 * <p>
 * </p>
 *
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 */
public class DoacaoActivity extends LocationActivity implements View.OnClickListener, Validator.ValidationListener {

    private static int PROFILE_PIC_COUNT = 0;
    private FindByMyLocationActualTask findByMyLocationActualTask;
    private Donativo donativo;
    private Button btnAddAddress;
    private Button btnKeep;
    private CardView cardView;
    private ImageView img1, img2, img3;
    private HashMap<String, byte[]> donativeImages;
    private AccessCameraAndGalleryDevicePermission permissionSelectImagem;
    private String keyImg;
    private Toolbar mToolbar;
    private Validator validator;
    private TextView tvEndereco;

    @Order(2)
    @NotEmpty(messageResId = R.string.msgNameNotInformed)
    private TextInputEditText edtNome;

    @Order(1)
    @NotEmpty(messageResId = R.string.msgDescriptionNotInformed)
    private TextInputEditText edtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docao);
        initGoogleAPIClient();
        init();
    }

    @Override
    public void init() {
        super.init();
        permissionSelectImagem = new AccessCameraAndGalleryDevicePermission(this);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        btnAddAddress = (Button) findViewById(R.id.btnAddAddress);
        btnKeep = (Button) findViewById(R.id.btnKeepAgendamento);
        edtDescription = (TextInputEditText) findViewById(R.id.edtDescription);
        edtNome = (TextInputEditText) findViewById(R.id.edtNome);
        tvEndereco = (TextView) findViewById(R.id.tvEnderecoColeta);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        validator = new Validator(DoacaoActivity.this);
        validator.setValidationListener(this);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);

        btnAddAddress.setOnClickListener(this);
        btnKeep.setOnClickListener(this);


        donativo = (Donativo) getIntent().getSerializableExtra("Donativo");
        donativeImages = (HashMap<String, byte[]>) getIntent().getSerializableExtra("Images");
        if (donativo == null) {
            donativo = new Donativo();
        } else {
            setValueDonativoInForm();
        }

        if (donativeImages == null) {
            donativeImages = new HashMap<>();
        } else if (donativeImages.size() > 0) {
            loadingImages();
        }

        if (getIntent().hasExtra("Endereco")) {
            Endereco endereco = (Endereco) getIntent().getSerializableExtra("Endereco");
            donativo.setEndereco(endereco);
            setAtrAddressIntoCard(endereco);
        }

        if (getIntent().hasExtra("Categoria")) {
            Categoria categoria = (Categoria) getIntent().getSerializableExtra("Categoria");
            donativo.setCategoria(categoria);
        }
    }

    /**
     * Seta endereço no cardview
     */
    private void setAtrAddressIntoCard(Endereco endereco) {
        btnAddAddress.setVisibility(View.GONE);
        cardView = (CardView) findViewById(R.id.card_address);
        cardView.setVisibility(View.VISIBLE);
        tvEndereco.setVisibility(View.VISIBLE);
        ((TextView) cardView.findViewById(R.id.tv_logradouro_name)).setText(endereco.getLogradouro());
        ((TextView) cardView.findViewById(R.id.tv_bairro)).setText(endereco.getBairro());
        ((TextView) cardView.findViewById(R.id.tv_number)).setText(endereco.getNumero());
        ((TextView) cardView.findViewById(R.id.tv_cep_name)).setText(endereco.getCep());
        ((TextView) cardView.findViewById(R.id.tv_city)).setText(endereco.getLocalidade());
        ((TextView) cardView.findViewById(R.id.tv_uf_name)).setText(endereco.getUf());
        cardView.setOnClickListener(this);

    }

    /**
     * Dialog para seleção da opção para Editar ou  remover endereço.
     */
    private void openDialogEditOrRemoveddress() {
        final CharSequence[] items = {getString(R.string.tv_edit), this.getString(R.string.removeAddress), getString(R.string.cancelar)};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.selectOption));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals(getString(R.string.tv_edit))) {
                    setValueInDonativo();
                    Intent intent = new Intent(DoacaoActivity.this, AddEnderecoActivity.class);
                    intent.putExtra("Donativo", donativo);
                    startActivity(intent);
                } else if (items[item].equals(getString(R.string.removeAddress))) {
                    cardView.setVisibility(View.GONE);
                    btnAddAddress.setVisibility(View.VISIBLE);
                    donativo.setEndereco(null);
                } else if (items[item].equals(getString(R.string.cancelar))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    /**
     * Seta propriedades e executa Task para get location
     */
    private void runTaskLocation() {
        startLocationUpdates();
        if (mLastLocation == null) {
            mLastLocation = getLocation();
        }

        if (mLastLocation != null) {
            findByMyLocationActualTask = new FindByMyLocationActualTask(DoacaoActivity.this, new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            findByMyLocationActualTask.delegate = new AsyncResponse<Endereco>() {
                @Override
                public void processFinish(Endereco output) {
                    setValueInDonativo();
                    Intent intent = new Intent(DoacaoActivity.this, AddEnderecoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Endereco", output);
                    intent.putExtra("Donativo", donativo);
                    intent.putExtra("Images", donativeImages);

                    startActivity(intent);
                }
            };
            findByMyLocationActualTask.execute();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddAddress) {
            checkPermissions();
            if (mLastLocation != null) {
                runTaskLocation();
            }
        } else if (v.getId() == R.id.img1) {
            keyImg = "img1";
            if (donativeImages.get(keyImg) != null) {
                openDialogSelectEditImage();
            } else
                permissionSelectImagem.openDialogSelectImage();
        } else if (v.getId() == R.id.img2) {
            keyImg = "img2";
            if (donativeImages.get(keyImg) != null) {
                openDialogSelectEditImage();
            } else
                permissionSelectImagem.openDialogSelectImage();
        } else if (v.getId() == R.id.img3) {
            keyImg = "img3";
            if (donativeImages.get(keyImg) != null) {
                openDialogSelectEditImage();
            } else
                permissionSelectImagem.openDialogSelectImage();
        } else if (v.getId() == R.id.btnKeepAgendamento) {
            validator.validate();
        } else if (v.getId() == R.id.card_address) {
            openDialogEditOrRemoveddress();
        }
    }

    /**
     * Dialog para seleção de camera ou galeria para selecionar Imagem.
     */
    public void openDialogSelectEditImage() {
        final CharSequence[] items = {this.getString(R.string.TakePhoto), this.getString(R.string.gallery), this.getString(R.string.remove), this.getString(R.string.cancelar)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getString(R.string.selectPhoto));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals(getString(R.string.TakePhoto))) {
                    PROFILE_PIC_COUNT = 1;
                    if (permissionSelectImagem.isCameraPermissionGranted()) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }
                } else if (items[item].equals(getString(R.string.gallery))) {
                    PROFILE_PIC_COUNT = 1;
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_FILE);

                } else if (items[item].equals(getString(R.string.remove))) {
                    donativeImages.remove(keyImg);
                    selectImageClicked().setImageDrawable(getDrawable(R.drawable.add));

                } else if (items[item].equals(getString(R.string.cancelar))) {
                    PROFILE_PIC_COUNT = 0;
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    /**
     * Implementação para controlar operações na action bar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @param outState
     */
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Donativo", donativo);
        outState.putSerializable("images", donativeImages);
    }

    protected void onRestoreInstanceState(Bundle savedState) {
        donativeImages = (HashMap<String, byte[]>) savedState.getSerializable("images");
        donativo = (Donativo) savedState.getSerializable("Donativo");
        loadingImages();

    }

    /**
     * Carrega imagens salvas;
     */
    private void loadingImages() {
        if (donativeImages != null) {
            if (donativeImages.get("img1") != null) {
                img1.setImageBitmap(androidUtil.convertBytesInBitmap(donativeImages.get("img1")));
            } else if (donativeImages.get("img2") != null) {
                img2.setImageBitmap(androidUtil.convertBytesInBitmap(donativeImages.get("img2")));
            } else {
                img3.setImageBitmap(androidUtil.convertBytesInBitmap(donativeImages.get("img3")));
            }
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap photo = null;

        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");

        } else if (requestCode == SELECT_FILE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage;
            selectedImage = data.getData();
            photo = capturePhotoUtils.getImageResized(this, selectedImage);
        }

        if (photo != null) {

            selectImageClicked().setImageBitmap(photo);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            donativeImages.put(keyImg, imageBytes);
        }
    }

    /**
     * Set os valores do formulário no donativo.
     */
    public void setValueInDonativo() {
        if (edtNome.getText().toString().trim().length() > 0) {
            donativo.setNome(edtNome.getText().toString().trim());
        }

        if (edtDescription.getText().toString().trim().length() > 0) {
            donativo.setDescricao(edtDescription.getText().toString().trim());
        }
    }


    /**
     * Set os valores de donativo no formulário.
     */
    public void setValueDonativoInForm() {
        if (donativo.getNome() != null) {
            edtNome.setText(donativo.getNome());
        }

        if (donativo.getDescricao() != null) {
            edtDescription.setText(donativo.getDescricao());
        }
    }

    /**
     * Retorna a ImageView Clicada de acordo com keyImg
     *
     * @return
     */
    private ImageView selectImageClicked() {
        if (keyImg != null) {
            if (keyImg.equals("img1")) {
                return img1;
            } else if (keyImg.equals("img2")) {
                return img2;
            } else {
                return img3;
            }
        }

        return null;
    }

    @Override
    public void onValidationSucceeded() {
        setValueInDonativo();
        Intent intent = new Intent(DoacaoActivity.this, AgendamentoDoacaoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Donativo", donativo);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
                view.requestFocus();
            } else {
                CustomToast.getInstance(DoacaoActivity.this).createSuperToastSimpleCustomSuperToast(message);
            }
        }
    }
}