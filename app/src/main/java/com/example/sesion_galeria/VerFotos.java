package com.example.sesion_galeria;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import com.example.sesion_galeria.Adaptores.AdaptadorImagenes;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class VerFotos extends AppCompatActivity {
    ListView lstViewFotos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_fotos);
        InitializeControls();

        File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/MisImagenes");
        File[] file = folder.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {

                return filename.contains(".png");
            }
        });
        for (int i = 0; i < file.length; i++) {
            System.out.println(file[i].getAbsolutePath());
        }

        this.loadListAdapter(file);
    }

    public void InitializeControls() {
        lstViewFotos = (ListView) findViewById(R.id.listaFotos);
    }

    private void loadListAdapter(File[] files) {
        List<String[]> imagenes = this.loadImageGalleryItems(files);
        AdaptadorImagenes ai = new AdaptadorImagenes(this, imagenes);
        lstViewFotos.setAdapter(ai);
    }

    private List<String[]> loadImageGalleryItems(File[] files) {
        List<String[]> imagenes = new ArrayList<>();//filas
        String[] imgs;// = new String[3];//columnas
        // i = 0 | imgs primera imagen
        // i = 1 | imgs [1i,2i]
        // i = 2 | imgs [1i,2i,3i]
        // i = 3 | imgs [4i]
        for (int i = 0; i < files.length; i += 3) {
            System.out.println("img load");
            imgs = new String[3];
            try {
                imgs[0] = files[i].getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                imgs[1] = files[i + 1].getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                imgs[2] = files[i + 2].getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("i%3" + i);
            imagenes.add(imgs);
        }
        System.out.println(imagenes);
        return imagenes;
    }
}