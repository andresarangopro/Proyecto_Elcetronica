package com.example.hp.appelectr.Recyclers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hp.appelectr.Models.Alarma;
import com.example.hp.appelectr.Models.Habitacion;
import com.example.hp.appelectr.R;
import com.example.hp.appelectr.Util;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.hp.appelectr.Activitys.NavigationActivity.managementBluetooth;

public class RAAlarmas extends RecyclerView.Adapter<RAAlarmas.ViewHabitaciones> {

    public static class ViewHabitaciones extends RecyclerView.ViewHolder{
        TextView tvAlarma;
        View thisView;
        public ViewHabitaciones(View itemView) {
            super(itemView);
            tvAlarma = itemView.findViewById(R.id.tvFechaAlarm);
            thisView = itemView;
        }
    }

    private List<Long> listAlarmas;
    private Context mContext;


    public RAAlarmas(Context context, List<Long> listAlarmas){
        this.mContext = context;
        this.listAlarmas = listAlarmas;
    }

    @NonNull
    @Override
    public ViewHabitaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rv_alarmas,parent,false);
        return new ViewHabitaciones(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHabitaciones holder, final int position) {
        long time = listAlarmas.get(position);
        holder.tvAlarma.setText(getDate((time+18000)*1000));//getDate(alarma.getFechaActivacionAlarma().get(position)));

    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return date;
    }

    @Override
    public int getItemCount() { return listAlarmas.size();}


}
