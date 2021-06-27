package com.example.sesion_galeria;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ObtenerFoto extends AppCompatActivity {
    ImageView imagen;
    Button b1,b2,b3;
    FileOutputStream outputStream;

// creamos un "launcher" que reemplazara el metodo starActitityForResult, el cual esta depreciado
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            //new ActivityResultCallback<ActivityResult>() es lo mismo que result ->
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri)
                        // update the preview image in the layout
                        imagen.setImageURI(selectedImageUri);
                    //cuando la imagen se obtenga, se le presentara al usuario la opcion de guardarla
                    b3.setVisibility(View.VISIBLE);
                }
            });

    ActivityResultLauncher<Intent> launcher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            //new ActivityResultCallback<ActivityResult>() es lo mismo que result ->
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imagen.setImageBitmap(imageBitmap);
                    b3.setVisibility(View.VISIBLE);
                }
            });
//////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_foto);
        this.ini_control();
    }

    public void ini_control(){
        imagen=findViewById(R.id.vista);
        b1=findViewById(R.id.Archivo);
        b2=findViewById(R.id.Camara);
        b3=findViewById(R.id.Guardar);
    }

    // los siguientes metodos nos ayudaran a manejar los permisos correspondientes en tiempo de ejecucion
    public void obtenerPorArchivoPermiso(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(ObtenerFoto.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                this.obtenerPorArchivo();
            }
            else
                ActivityCompat.requestPermissions(ObtenerFoto.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }
        else
            this.obtenerPorArchivo();
    }

    public void obtenerPorCamaraPermiso(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(ObtenerFoto.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                this.obtenerPorCamara();
            }
            else
                ActivityCompat.requestPermissions(ObtenerFoto.this,new String[]{Manifest.permission.CAMERA},3);
        }
        else
            this.obtenerPorCamara();
    }

    public void guardarPermiso(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(ObtenerFoto.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                this.guardarFoto();
            }
            else
                ActivityCompat.requestPermissions(ObtenerFoto.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else
            this.guardarFoto();
    }

    ///////////////////////////////////////////////////////////////////////////

    private void obtenerPorCamara() {
        Intent i = new Intent().setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        if(i.resolveActivity(getPackageManager())!=null){
            launcher2.launch(i);
        }
    }

    private void obtenerPorArchivo() {
        Intent i = new Intent().setAction(Intent.ACTION_GET_CONTENT);//este intent sera para abrir los archivos
        i.setType("image/*");
        launcher.launch(i);
    }

    public void guardarFoto() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imagen.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();

        File filepath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // guardaremos la imagen en la direccion /sdcard/Android/data/com.example.temporal_imagen/files/Pictures/MisImagenes
        File dir = new File(filepath.getAbsolutePath() + "/MisImagenes");

        dir.mkdirs();

        // generamos el nombre del archivo
        String filename = String.format("%d.png",System.currentTimeMillis());
        // esto nos ayudara a nombrar nuestros archivos a un formato de fecha y tiempo especifico
        File outFile = new File(dir,filename); //direccion,archivo.extension
        try{
            outputStream = new FileOutputStream(outFile);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        Toast.makeText(this.getApplicationContext(),"Se guardó la imagen",Toast.LENGTH_LONG).show();
        try { outputStream.flush();}
        catch (IOException e) { e.printStackTrace();}
        try { outputStream.close();}
        catch (IOException e) { e.printStackTrace();}
    }
}