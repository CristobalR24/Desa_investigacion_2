package com.example.sesion_galeria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b1,b2;
    EditText e1,e2;
    String contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ini_control();
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        contra = prefs.getString("contrasena", null);

        if(contra==null){ //la primera vez la contraseña no tendrá ningun valor
            Toast.makeText(this.getApplicationContext(),"contenido: "+contra,Toast.LENGTH_LONG).show();
            e1.setVisibility(View.VISIBLE);
            b1.setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(this.getApplicationContext(),"contenido: "+contra,Toast.LENGTH_LONG).show();
            e2.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
        }

    }

    public void ini_control(){
        e1=findViewById(R.id.primeravez1);
        b1=findViewById(R.id.primeravez2);// se muestra una vez cuando instala app

        e2=findViewById(R.id.segundavez1);
        b2=findViewById(R.id.segundavez2);
    }


    public void Registro(View view) {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("contrasena",e1.getText().toString());
        editor.commit();

        Toast.makeText(this.getApplicationContext(),"contenido: "+contra,Toast.LENGTH_LONG).show();
        Intent i = new Intent(this.getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    public void Validacion(View view) {
        String aux;
        aux=e2.getText().toString();

        if(aux.equals(contra)){
            Intent i = new Intent(this.getApplicationContext(),PrincipalActivity.class);
            startActivity(i);
        }
        else{
            Toast.makeText(this.getApplicationContext(),"Codigo incorrecto",Toast.LENGTH_LONG).show();
        }

    }
}