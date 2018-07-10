package com.example.azzumwaqar.hoxtonsalahproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class SalaatListAdapter extends ArrayAdapter<Salaat> {


    public SalaatListAdapter(@NonNull Context context, @NonNull List<Salaat> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;

        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        Salaat currentSalaat = getItem(position);

        TextView salaatName = listView.findViewById(R.id.SalaatNameTextView);
        TextView jamaatTime = listView.findViewById(R.id.salaatJamaatTimeTextView);
        TextView beginTime = listView.findViewById(R.id.salaatBeginTimeTextView);

        salaatName.setText(currentSalaat.getSalaatName());
        jamaatTime.setText(currentSalaat.getSalatJamat());
        beginTime.setText(currentSalaat.getSalatBegin());

        return listView;
    }
}
