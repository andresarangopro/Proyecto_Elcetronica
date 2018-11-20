package com.example.hp.appelectr.Recyclers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hp.appelectr.Models.Habitacion;
import com.example.hp.appelectr.R;
import com.example.hp.appelectr.Util;

import java.util.List;

import static com.example.hp.appelectr.Activitys.NavigationActivity.managementBluetooth;

public class RAHabitaciones extends RecyclerView.Adapter<RAHabitaciones.ViewHabitaciones> {

    public static class ViewHabitaciones extends RecyclerView.ViewHolder{
        TextView tvPersonasNone,tvTemperatura;
        ImageButton btnLigth;
        View thisView;
        public ViewHabitaciones(View itemView) {
            super(itemView);
            tvPersonasNone = itemView.findViewById(R.id.tvPersonaNone);
            tvTemperatura = itemView.findViewById(R.id.tvTemperatura);
            btnLigth = itemView.findViewById(R.id.btnLigth);
            thisView = itemView;
        }
    }

    private List<Habitacion> listHabitaciones;
    private Context mContext;


    public RAHabitaciones(Context context, List<Habitacion> listHabitaciones){
        this.mContext = context;
        this.listHabitaciones = listHabitaciones;
    }

    @NonNull
    @Override
    public ViewHabitaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rv_habitaciones,parent,false);
        return new ViewHabitaciones(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHabitaciones holder, final int position) {
        final Habitacion habitacion = listHabitaciones.get(position);
        if(listHabitaciones.get(position).getTipo() == 0){
            holder.btnLigth.setBackgroundResource((habitacion.getEstadoLuz() == 0)?R.drawable.fan_off:R.drawable.fan_on);
        }else{
            holder.tvTemperatura.setText("TEMPERATURA: "+habitacion.getTemperatura()+"");
            holder.tvPersonasNone.setText("PERSONA: "+ Util.calcTimeToKnowIfPerson(habitacion.getLastTimeListenM()));
            holder.btnLigth.setBackgroundResource((habitacion.getEstadoLuz() == 0)?R.drawable.ligth_off:R.drawable.ligth_on);
        }
        holder.btnLigth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((listHabitaciones.get(position).getEstadoLuz() == 0)) {
                    listHabitaciones.get(position).setEstadoLuz(1);
                } else {
                    listHabitaciones.get(position).setEstadoLuz(0);
                }
                if(listHabitaciones.get(position).getTipo() == 0){
                    holder.btnLigth.setBackgroundResource((habitacion.getEstadoLuz() == 0)?R.drawable.fan_off:R.drawable.fan_on);
                }else{
                    holder.btnLigth.setBackgroundResource((habitacion.getEstadoLuz() == 0)?R.drawable.ligth_off:R.drawable.ligth_on);
                }

                managementBluetooth.myConexionBT.write(position+"");
            }
        });
    }

    @Override
    public int getItemCount() { return listHabitaciones.size();}


}
