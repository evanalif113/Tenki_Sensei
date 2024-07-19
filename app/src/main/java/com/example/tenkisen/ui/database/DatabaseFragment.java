package com.example.tenkisen.ui.database;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tenkisen.databinding.FragmentDatabaseBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatabaseFragment extends Fragment {

    private FragmentDatabaseBinding binding;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DatabaseViewModel notificationsViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);

        binding = FragmentDatabaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // EditText fields
        final EditText dataTanggal = binding.idEdtTanggal;
        final EditText dataSuhu = binding.idEdtSuhu;
        final EditText dataKelembapan = binding.idEdtKelembapan;
        final EditText dataCuaca = binding.idEdtKeadaanCuaca;
        final Button tambahData = binding.tambahData;
        final Button lihatData = binding.lihatData;

        // Set click listener to show custom DateTimePickerDialog
        dataTanggal.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        final int selectedYear = year1;
                        final int selectedMonth = monthOfYear;
                        final int selectedDay = dayOfMonth;

                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                getContext(),
                                (view1, hourOfDay, minute) -> {
                                    final int selectedHour = hourOfDay;
                                    final int selectedMinute = minute;

                                    String dateTime = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear + " " + selectedHour + ":" + selectedMinute;
                                    dataTanggal.setText(dateTime);
                                },
                                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                        timePickerDialog.show();
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Add data button click listener
        tambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Tanggal = dataTanggal.getText().toString();
                String Suhu = dataSuhu.getText().toString();
                String Kelembapan = dataKelembapan.getText().toString();
                String Cuaca = dataCuaca.getText().toString();

                if (TextUtils.isEmpty(Tanggal)) {
                    dataTanggal.setError("Mohon tambahkan tanggal");
                } else if (TextUtils.isEmpty(Suhu)) {
                    dataSuhu.setError("Mohon tambahkan suhu");
                } else if (TextUtils.isEmpty(Kelembapan)) {
                    dataKelembapan.setError("Mohon tambahkan kelembapan");
                } else if (TextUtils.isEmpty(Cuaca)) {
                    dataCuaca.setError("Mohon tambahkan keadaan cuaca");
                } else {
                    addDataToFirestore(Tanggal, Suhu, Kelembapan, Cuaca);
                }
            }
        });

        // View data button click listener
        lihatData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CuacaDetails.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public static class Data {
        private String dataTanggal;
        private String dataSuhu;
        private String dataKelembapan;
        private String dataCuaca;

        public Data() {
            // empty constructor required for Firebase
        }

        public Data(String dataTanggal, String dataSuhu, String dataKelembapan, String dataCuaca) {
            this.dataTanggal = dataTanggal;
            this.dataSuhu = dataSuhu;
            this.dataKelembapan = dataKelembapan;
            this.dataCuaca = dataCuaca;
        }

        public String getDataTanggal() {
            return dataTanggal;
        }

        public void setDataTanggal(String dataTanggal) {
            this.dataTanggal = dataTanggal;
        }

        public String getDataSuhu() {
            return dataSuhu;
        }

        public void setDataSuhu(String dataSuhu) {
            this.dataSuhu = dataSuhu;
        }

        public String getDataKelembapan() {
            return dataKelembapan;
        }

        public void setDataKelembapan(String dataKelembapan) {
            this.dataKelembapan = dataKelembapan;
        }

        public String getDataCuaca() {
            return dataCuaca;
        }

        public void setDataCuaca(String dataCuaca) {
            this.dataCuaca = dataCuaca;
        }
    }

    private void addDataToFirestore(String dataTanggal, String dataSuhu, String dataKelembapan, String dataCuaca) {
        CollectionReference dbCuaca = db.collection("Cuaca");

        // Create custom ID using date and time to ensure uniqueness
        String customId = createCustomId(dataTanggal);

        Data data = new Data(dataTanggal, dataSuhu, dataKelembapan, dataCuaca);

        dbCuaca.document(customId)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "Data anda telah ditambahkan dalam Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (getContext() != null) {
                            Toast.makeText(getContext(), "Gagal menambahkan Data \n" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String createCustomId(String dataTanggal) {
        // Convert date to a format suitable for a unique ID
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        String dateId = simpleDateFormat.format(Calendar.getInstance().getTime());

        // Combine with other fields if needed
        return dateId;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
