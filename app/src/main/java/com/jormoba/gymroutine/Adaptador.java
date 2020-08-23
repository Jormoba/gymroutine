package com.jormoba.gymroutine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private Context context;
    private ArrayList<Ejercicio> listItems;

    public Adaptador(Context context, ArrayList<Ejercicio> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        Ejercicio ejercicio = (Ejercicio) getItem(i);

        convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

        TextView tv_nombre = (TextView) convertView.findViewById(R.id.tv_nombre);
        TextView tv_repeticiones = (TextView) convertView.findViewById(R.id.tv_repeticiones);
        TextView tv_series = (TextView) convertView.findViewById(R.id.tv_series);
        TextView tv_peso = (TextView) convertView.findViewById(R.id.tv_peso);
        TextView tv_comentarios = (TextView) convertView.findViewById(R.id.tv_comentarios);
        CheckBox completado = (CheckBox) convertView.findViewById(R.id.checkBox);
        completado.setVisibility(View.GONE);

        tv_nombre.setText(ejercicio.getNombre());
        tv_repeticiones.setText(String.valueOf("Repeticiones: " + ejercicio.getRepeticiones()));
        tv_series.setText(String.valueOf("Series: " + ejercicio.getSeries()));
        tv_peso.setText(String.valueOf("Peso: " + ejercicio.getPeso()) + " Kg");
        tv_comentarios.setText(ejercicio.getComentarios());
        if(ejercicio.isCompletado()){
            completado.setVisibility(View.VISIBLE);
            completado.setChecked(true);
        }

        return convertView;
    }
}
