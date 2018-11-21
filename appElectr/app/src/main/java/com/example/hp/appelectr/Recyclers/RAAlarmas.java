package com.example.hp.appelectr.Recyclers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.appelectr.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.hp.appelectr.Util.getDate;

public class RAAlarmas extends RecyclerView.Adapter<RAAlarmas.ViewAlarmas> {

    public static class ViewAlarmas extends RecyclerView.ViewHolder{
        TextView tvAlarma;
        View thisView;
        public ViewAlarmas(View itemView) {
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
    public ViewAlarmas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rv_alarmas,parent,false);
        return new ViewAlarmas(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewAlarmas holder, final int position) {
        long time = listAlarmas.get(position);
        holder.tvAlarma.setText(getDate((time+18000)*1000));//getDate(alarma.getFechaActivacionAlarma().get(position)));

    }

    @Override
    public int getItemCount() { return listAlarmas.size();}


}
