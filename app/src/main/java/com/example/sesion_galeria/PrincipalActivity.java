package com.example.sesion_galeria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void obtenerFoto(View view) {
        Intent i = new Intent(this.getApplicationContext(),ObtenerFoto.class);
        startActivity(i);
    }

    public void verFotos(View view) {
        Intent i = new Intent(this.getApplicationContext(),VerFotos.class);
        startActivity(i);
    }
}