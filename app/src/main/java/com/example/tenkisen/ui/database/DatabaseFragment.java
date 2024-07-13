package com.example.tenkisen.ui.database;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tenkisen.databinding.FragmentDatabaseBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class DatabaseFragment extends Fragment {

    private FragmentDatabaseBinding binding;

    @Override
    public View onCreateView(@NonNull
                                 LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseViewModel notificationsViewModel =
                new ViewModelProvider(this).get(DatabaseViewModel.class);

        binding = FragmentDatabaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // creating a variable
        // for firebasefirestore.
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        // EditText for date picker
        final EditText dataTanggal = binding.idEdtTanggal;
        final EditText dataSuhu = binding.idEdtSuhu;
        final EditText dataKelembapan = binding.idEdtKelembapan;
        final EditText keadaanCuaca = binding.idEdtKeadaanCuaca;
        final Button TambahData = binding.tambahData;
        final Button LihatData = binding.lihatData;

        // Set click listener to show DatePickerDialog
        dataTanggal.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                        dataTanggal.setText(date);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Tombol tambah data
        TambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Tanggal = dataTanggal.getText().toString();
                String Suhu = dataSuhu.getText().toString();
                String Kelembapan = dataKelembapan.getText().toString();
                String Cuaca = keadaanCuaca.getText().toString();
            }
        });

        // Tombol lihat data
        LihatData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return root;
    }
}
