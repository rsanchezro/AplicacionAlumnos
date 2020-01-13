package com.roberto.aplicacionalumnos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
//Clase que vincula los datos con la vista
class MiAdaptadorAlumnos extends ArrayAdapter<Alumno> {
    Context actividad;
    int recurso_vista;
    ArrayList<Alumno> datos_alumnos;
    public MiAdaptadorAlumnos(@NonNull Context context, int resource, @NonNull ArrayList<Alumno> objects) {
        super(context, resource, objects);
        this.actividad=context;
        this.recurso_vista=resource;
        this.datos_alumnos=objects;

    }

    /* Necesario sobrescribir este metodo para asociar los datos de cada elemento del array a cada
    componente visual
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View vista, @NonNull ViewGroup parent) {
        Log.i("Informacion","Se construye el elemento: "+position);
        if(vista!=null)
        {

            Log.i("Informacion", "Se reutiliza para crear el elemento:"+position+"se destruye el elemento: "+((TextView)(vista.findViewById(R.id.nombre))).getText());
        }
        else {
            //Solo creo instancia de vista si no existe instancia, las primeras veces, luego la invocación a getView incluye una referencia al elemento de la vista que
            // desaparece de pantalla, por lo que lo podemos reutilizar sin instanciarlo
            //Lo primero que tengo que hacer es obtener la vista que permite dibujar cada elemento
            //   View vista=LayoutInflater.from(this.actividad).inflate(this.recurso_vista,parent,false);
            vista = ((Listado_alumnos) actividad).getLayoutInflater().inflate(this.recurso_vista, parent, false);

        }
      //Voy a marcar los elementos seleccionados

        vista.setBackgroundColor(Color.WHITE);
        if(((Listado_alumnos)actividad).actionmodeactivado)
        {
            //Si esta el modo contextual activado
            //Si el elemento que estoy pintando esta en la lista de elementos seleccionados le cambio el color de fondo
            if(((Listado_alumnos) actividad).alumnosseleccionados.contains(datos_alumnos.get(position)))
            {
                vista.setBackgroundResource(android.R.color.darker_gray);
            }

        }
        //Ahora tendré que obtener los elementos del Layout de cada elemento a taves del objeto vista
        //y enlazarlos con los valores del elemento de los datos de la posición position
        TextView matricula=(TextView)vista.findViewById(R.id.matricula);
        TextView nombre=(TextView)vista.findViewById(R.id.nombre);
        ImageView imagen=(ImageView)vista.findViewById(R.id.imagenAlumno);

        matricula.setText(this.datos_alumnos.get(position).getNummatricula()+"");
        nombre.setText(this.datos_alumnos.get(position).getNombre());
        //Cargo la imagen con glide

        return vista;
    }

    public void eliminarElementos(ArrayList<Alumno> listaelementosaeleminar)
    {
        for(Alumno a:listaelementosaeleminar)
        {
            this.datos_alumnos.remove(a);
        }
        notifyDataSetChanged();

    }
}
