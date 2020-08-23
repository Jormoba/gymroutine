package com.jormoba.gymroutine;

import com.google.firebase.database.DatabaseReference;

public class Utiles {

    public static void addEjercicio(DatabaseReference databaseReference, Ejercicio ejercicio){

        databaseReference.setValue(ejercicio);
    }

    public static void removeEjercicio(DatabaseReference databaseReference){

        databaseReference.removeValue();

    }
}
