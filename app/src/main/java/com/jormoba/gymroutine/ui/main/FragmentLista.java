package com.jormoba.gymroutine.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jormoba.gymroutine.Adaptador;
import com.jormoba.gymroutine.Ejercicio;
import com.jormoba.gymroutine.R;
import com.jormoba.gymroutine.Utiles;

import java.util.ArrayList;

public class FragmentLista extends Fragment {

    String dia;
    String usuario;
    
    public FragmentLista() {
        // Required empty public constructor
    }

    public FragmentLista(String dia) {
        this.dia = dia;
    }

    private ListView listView;
    private Adaptador adaptador;
    private DatabaseReference databaseReference, ref_user, ref_dia;

    ArrayList<Ejercicio> listItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lunes, container, false);
        listView = (ListView) view.findViewById(R.id.listView);

        listItems = new ArrayList<>();

        //Qué usuario soy?
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        usuario = sharedPreferences.getString("cliente_string", "Test");

        //Referencia base
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Qué usuario somos?
        ref_user = databaseReference.child(usuario);

        //Definimos en qué fragment (día) estamos
        ref_dia = ref_user.child(dia);

        ref_dia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                adaptador = new Adaptador(getActivity(), getArrayItems(snapshot));
                listView.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Si hay algún error
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Ejercicio ejercicio = (Ejercicio) listItems.get(i);

                final String id = ejercicio.getE_ID();
                final String nombre = ejercicio.getNombre();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(nombre).setMessage("¿Has terminado el ejercicio?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        terminarEjercicio(ejercicio, false);
                    }
                });
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        terminarEjercicio(ejercicio, true);
                    }
                });

                builder.create().show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Ejercicio ejercicio = (Ejercicio) listItems.get(i);

                final String id = ejercicio.getE_ID();
                final String nombre = ejercicio.getNombre();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Eliminar elemento").setMessage("¿Quieres eliminar \"" + nombre + "\"?");
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Utiles.removeEjercicio(ref_dia.child(id));
                        Toast.makeText(getContext(), "Has borrado \"" + nombre + "\"", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();

                return false;
            }
        });

        return view;
    }

    private ArrayList<Ejercicio> getArrayItems(DataSnapshot snapshot){

        listItems.clear();

        for (DataSnapshot lunesSnapshot: snapshot.getChildren()){

            Ejercicio ejercicio = lunesSnapshot.getValue(Ejercicio.class);
            listItems.add(ejercicio);
        }

        return listItems;
    }

    private void terminarEjercicio(Ejercicio ejercicio, boolean completado){

        ejercicio.setCompletado(completado);

        DatabaseReference databaseReference = ref_dia.child(ejercicio.getE_ID());
        databaseReference.setValue(ejercicio);
    }
}