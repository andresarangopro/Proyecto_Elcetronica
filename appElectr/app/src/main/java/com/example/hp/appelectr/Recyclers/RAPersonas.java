package com.example.hp.appelectr.Recyclers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hp.appelectr.Models.Habitacion;
import com.example.hp.appelectr.Models.Persona;
import com.example.hp.appelectr.R;
import com.example.hp.appelectr.Util;

import java.util.List;

import static com.example.hp.appelectr.Activitys.NavigationActivity.managementBluetooth;
import static com.example.hp.appelectr.Util.getPromHoraEntradaS;
import static com.example.hp.appelectr.Util.promHoraEntrada;
import static com.example.hp.appelectr.Util.promMinutesEntrada;

public class RAPersonas extends RecyclerView.Adapter<RAPersonas.ViewPersonas> {

    public static class ViewPersonas extends RecyclerView.ViewHolder{
        TextView tvNombrePersona,tvPromHora,tvKeyPersona;
        View thisView;
        public ViewPersonas(View itemView) {
            super(itemView);
            tvNombrePersona = itemView.findViewById(R.id.tvNombrePersona);
            tvPromHora = itemView.findViewById(R.id.tvPromHora);
             tvKeyPersona = itemView.findViewById(R.id.tvKeyPersona);
            thisView = itemView;
        }
    }

    private List<Persona> listPersonas;
    private Context mContext;


    public RAPersonas(Context context, List<Persona> listPersonas){
        this.mContext = context;
        this.listPersonas = listPersonas;
    }

    @NonNull
    @Override
    public ViewPersonas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rv_personas,parent,false);
        return new ViewPersonas(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewPersonas holder, final int position) {
        final Persona persona = listPersonas.get(position);
        String promHour = getPromHoraEntradaS(persona.getFechaEntradConHora());
        persona.setPromHoraEntrada(promHour);
        Log.d("TAGPROMH", promHour);
        holder.tvNombrePersona.setText("NOMBRE: "+persona.getNombre());
        holder.tvPromHora.setText("PROMEDIO HORA ENTRADA: "+ persona.getPromHoraEntrada());
        holder.tvKeyPersona.setText("KEY: "+persona.getKeyHouse());
    }

    @Override
    public int getItemCount() { return listPersonas.size();}


}
