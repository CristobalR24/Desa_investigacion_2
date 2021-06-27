package com.example.sesion_galeria.Adaptores;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sesion_galeria.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorImagenes extends ArrayAdapter<String[]> {

    private List<String[]> imagenes = new ArrayList<>();

    public AdaptadorImagenes(Context context, List<String[]> datos) {
        super(context, R.layout.listview_layout_template, datos);
        imagenes = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listview_layout_template, null);

        ImageView lblfoto1 = (ImageView) item.findViewById(R.id.imagen_1);

        try {
            lblfoto1.setImageURI(Uri.parse(imagenes.get(position)[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView lblfoto2 = (ImageView) item.findViewById(R.id.imagen_2);
        try {
            lblfoto2.setImageURI(Uri.parse(imagenes.get(position)[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView lblfoto3 = (ImageView) item.findViewById(R.id.imagen_3);
        try {
            lblfoto3.setImageURI(Uri.parse(imagenes.get(position)[2]));
            System.out.println("dentro de adapter" + imagenes.get(position)[0] + imagenes.get(position)[1] + imagenes.get(position)[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (item);
    }
}
