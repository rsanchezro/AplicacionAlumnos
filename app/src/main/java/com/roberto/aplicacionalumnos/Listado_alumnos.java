package com.roberto.aplicacionalumnos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Listado_alumnos extends AppCompatActivity {


    //componente visual que representa la Lista de alumnos
    ListView vista_listaalumnos;
   public boolean actionmodeactivado=false;
    //Lista de datos de los alumnos
     static ArrayList<Alumno>  datos_listaalumnos;
     ArrayList<Alumno> alumnosseleccionados=new ArrayList<>();
     //Adaptador que introduce los datos en la vista
    MiAdaptadorAlumnos adaptadorAlumnos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alumnos);
        vista_listaalumnos=(ListView)findViewById(R.id.listaalumnos);

        TypedArray arrayalumnos=null;
        //Esto se hace para en caso de que se rote la pantalla no se inicialicen los datos, solo
        //si se destruye la app se reinicializan
        if(datos_listaalumnos==null)

        {
            datos_listaalumnos=new ArrayList<Alumno>();

            //AQUI CARGO LA LISTA DE ALUMNOS PARA ELLO AVERIGUO
            // QUE LISTA DE ALUMNOS DEBO CARGAR RECOGIENDO INFORMACIÓN DEL INTENT
        switch (getIntent().getStringExtra("curso"))
        {
            case "GBD":
                arrayalumnos=getResources().obtainTypedArray(R.array.array_datos_alumnos_GBD);
                break;
            case "IAW":
                //Asocio al recurso de alumnos de IAW
                break;
            case "PMDM":
                //Asocio el recurso de alumnos de PMDM
                break;

        }
            if(arrayalumnos!=null)
            {
                rellenarLista_alumnos(arrayalumnos);
            }
        }
        adaptadorAlumnos=new MiAdaptadorAlumnos(this,R.layout.alumno_layout,this.datos_listaalumnos);
        vista_listaalumnos.setAdapter(adaptadorAlumnos);
        //Establecemos el modo de selección para el listView, en este caso se pueden seleccionar más de un elemento
        vista_listaalumnos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        //Establecemos una instancia de una interface que se implementa
        // y cuyos métodos se invocan cuando se actua sobre los elementos del ListView

        vista_listaalumnos.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            private Menu m;
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                //Este metodo se invoca cuando se cambia el estado de un elemento de la lista (seleccionado o no seleccionado)
                //Añado o elimino elementos al arraylist de alumnos seleccionados
                //Si el numero de alumnos seleccionados es superior a 1 oculto el editor


                Log.i("Informacion","Se ha pulsado en un elemento de la lista");
                if(alumnosseleccionados.contains(datos_listaalumnos.get(position)))
                {//Si el arrayList de alumnos seleccionados ya contiene ese alumno lo elimino
                    alumnosseleccionados.remove(datos_listaalumnos.get(position));

                }
                else{
                    alumnosseleccionados.add(datos_listaalumnos.get(position));
                }
                //Con esto consigo que se oculte el elemento de menu editar si hay más de un
                //elemento seleccionado
                m.findItem(R.id.editar).setVisible(alumnosseleccionados.size()>1?false:true);
                //Una vez seleccionado el elemento obligo a que se repinte, porque si no no cambia
                //el color de fondo
                adaptadorAlumnos.notifyDataSetChanged();
               mode.setTitle(alumnosseleccionados.size()+" Items seleccionados...");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                //Este método se ejecuta cuando se hace una pulsación larga sobre cualquier elemento del ListView (solo una vez)
                Log.i("OtraInf","Se ha producido el mecanismo de creacion del Action Mode");
                //Obtengo el color de fondo de uno de los elementos de la lista
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_lista_alumnos, menu);
                m=menu;
                actionmodeactivado=true;
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.borrar:
                        //Codigo para eliminar el elemento seleccionado de la ListView
                          adaptadorAlumnos.eliminarElementos(alumnosseleccionados);
                          //Vacio los elementos seleccionados

                            //Cierro la Barra de Accion Contextual (CAB-Contextual Action Bar)
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    case R.id.editar:
                        return false;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

                for(Alumno a:alumnosseleccionados)
                {
                    int posicion=datos_listaalumnos.indexOf(a);
                    if(posicion!=-1)
                    {

                    }
                }
                //Cuando salgo del modo contextual limpio el Array de elementos seleccionados
                alumnosseleccionados.clear();
                actionmodeactivado=false;


            }
        });



    }


    private void rellenarLista_alumnos(TypedArray a)
    {
        int id;
        for(int i=0;i<a.length();i++)
        {
            id=a.getResourceId(i,0);
            if(id!=0)
            {
                TypedArray al=getResources().obtainTypedArray(id);

                this.datos_listaalumnos.add(new Alumno(al.getInt(0,0),al.getString((int)1),al.getString((int)2)));


            }
        }
    }
}
