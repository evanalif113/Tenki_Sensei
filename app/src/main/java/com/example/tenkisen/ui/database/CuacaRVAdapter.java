package com.example.tenkisen.ui.database;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenkisen.R;

import java.util.ArrayList;

public class CuacaRVAdapter extends RecyclerView.Adapter<CuacaRVAdapter.ViewHolder> {

    private ArrayList<Cuaca> cuacaArrayList;
    private Context context;

    public CuacaRVAdapter(ArrayList<Cuaca> cuacaArrayList, Context context) {
        this.cuacaArrayList = cuacaArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CuacaRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cuaca_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CuacaRVAdapter.ViewHolder holder, int position) {
        Cuaca cuaca = cuacaArrayList.get(position);
        holder.dataTanggalTV.setText(cuaca.getDataTanggal());
        holder.dataSuhuTV.setText(cuaca.getDataSuhu());
        holder.dataKelembapanTV.setText(cuaca.getDataKelembapan());
        holder.dataCuacaTV.setText(cuaca.getDataCuaca());
    }

    @Override
    public int getItemCount() {
        return cuacaArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView dataTanggalTV;
        private final TextView dataSuhuTV;
        private final TextView dataKelembapanTV;
        private final TextView dataCuacaTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataTanggalTV = itemView.findViewById(R.id.idTVDataTanggal);
            dataSuhuTV = itemView.findViewById(R.id.idTVDataSuhu);
            dataKelembapanTV = itemView.findViewById(R.id.idTVDataKelembapan);
            dataCuacaTV = itemView.findViewById(R.id.idTVDataCuaca);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cuaca cuaca = cuacaArrayList.get(getAdapterPosition());

                    Intent i = new Intent(context, UpdateCuaca.class);

                    i.putExtra("cuaca", cuaca);

                    context.startActivity(i);
                }
            });
        }
    }
}
