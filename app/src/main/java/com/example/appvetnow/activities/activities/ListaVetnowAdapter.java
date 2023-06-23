package com.example.appvetnow.activities.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.appvetnow.activities.entidades.Vetnow;

public class ListaVetnowAdapter extends BaseAdapter {
    private final List<Vetnow> lista;
    private final Context context;

    public ListaVetnowAdapter(Context context, List<Vetnow> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        Vetnow objeto = lista.get(position);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(objeto.getNome());
        return view;
    }
}
