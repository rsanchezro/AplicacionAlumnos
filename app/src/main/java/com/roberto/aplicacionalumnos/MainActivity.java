package com.roberto.aplicacionalumnos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void listado_alumnos_GBD(View view) {
        Intent i=new Intent(this,Listado_alumnos.class);
        i.putExtra("curso","GBD");
        startActivity(i);

    }
}
