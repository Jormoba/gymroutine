package com.jormoba.gymroutine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEjercicio extends AppCompatActivity {

    DatabaseReference databaseReference;

    EditText et_nombre, et_series, et_repeticiones, et_peso, et_comentarios;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ejercicio);

        Bundle extras = getIntent().getExtras();
        String usuario, dia;

        if (extras != null) {
            usuario = extras.getString("usuario");
            dia = extras.getString("dia");
        } else{
            usuario = "Test";
            dia = "lunes";
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child(usuario).child(dia);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_series = (EditText) findViewById(R.id.et_series);
        et_repeticiones = (EditText) findViewById(R.id.et_repeticiones);
        et_peso = (EditText) findViewById(R.id.et_peso);
        et_comentarios = (EditText) findViewById(R.id.et_comentarios);
        addButton = (Button) findViewById(R.id.button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEjercicio();
            }
        });
    }

    private void addEjercicio(){

        String e_ID = databaseReference.push().getKey();
        String nombre = et_nombre.getText().toString();
        int series = Integer.parseInt(et_series.getText().toString().trim());
        int repeticiones = Integer.parseInt(et_repeticiones.getText().toString().trim());
        int peso = Integer.parseInt(et_peso.getText().toString().trim());
        String comentarios = et_comentarios.getText().toString();
        boolean completado = false;

        Ejercicio ejercicio = new Ejercicio(e_ID, nombre, series, repeticiones, peso, comentarios, completado);
        databaseReference = databaseReference.child(e_ID);

        Utiles.addEjercicio(databaseReference, ejercicio);

        Toast.makeText(this, "Has añadido " + nombre, Toast.LENGTH_SHORT).show();

        finish();
    }
}