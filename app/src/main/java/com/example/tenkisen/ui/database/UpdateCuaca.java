package com.example.tenkisen.ui.database;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tenkisen.MainActivity;
import com.example.tenkisen.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class UpdateCuaca extends AppCompatActivity {

    private EditText idEdtTanggal, idEdtSuhu, idEdtKelembapan, idEdtKeadaanCuaca;
    private String dataTanggal, dataSuhu, dataKelembapan, dataKeadaanCuaca;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cuaca);
        Cuaca cuaca = (Cuaca) getIntent().getSerializableExtra("cuaca");

        db = FirebaseFirestore.getInstance();

        idEdtTanggal = findViewById(R.id.idEdtTanggal);
        idEdtSuhu = findViewById(R.id.idEdtSuhu);
        idEdtKelembapan = findViewById(R.id.idEdtKelembapan);
        idEdtKeadaanCuaca = findViewById(R.id.idEdtKeadaanCuaca);

        Button updateCuacaBtn = findViewById(R.id.updateData);
        Button hapusCuacaBtn = findViewById(R.id.hapusData);

        idEdtTanggal.setText(cuaca.getDataTanggal());
        idEdtSuhu.setText(cuaca.getDataSuhu());
        idEdtKelembapan.setText(cuaca.getDataKelembapan());
        idEdtKeadaanCuaca.setText(cuaca.getDataCuaca());

        // Adding DatePicker and TimePicker
        idEdtTanggal.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    UpdateCuaca.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        final int selectedYear = year1;
                        final int selectedMonth = monthOfYear;
                        final int selectedDay = dayOfMonth;

                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                UpdateCuaca.this,
                                (view1, hourOfDay, minute) -> {
                                    final int selectedHour = hourOfDay;
                                    final int selectedMinute = minute;

                                    String dateTime = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear + " " + selectedHour + ":" + selectedMinute;
                                    idEdtTanggal.setText(dateTime);
                                },
                                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                        timePickerDialog.show();
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        hapusCuacaBtn.setOnClickListener(v -> deleteCuaca(cuaca));

        updateCuacaBtn.setOnClickListener(v -> {
            dataTanggal = idEdtTanggal.getText().toString();
            dataSuhu = idEdtSuhu.getText().toString();
            dataKelembapan = idEdtKelembapan.getText().toString();
            dataKeadaanCuaca = idEdtKeadaanCuaca.getText().toString();

            if (TextUtils.isEmpty(dataTanggal)) {
                idEdtTanggal.setError("Mohon tambahkan tanggal");
            } else if (TextUtils.isEmpty(dataSuhu)) {
                idEdtSuhu.setError("Mohon tambahkan suhu");
            } else if (TextUtils.isEmpty(dataKelembapan)) {
                idEdtKelembapan.setError("Mohon tambahkan kelembapan");
            } else if (TextUtils.isEmpty(dataKeadaanCuaca)) {
                idEdtKeadaanCuaca.setError("Mohon tambahkan keadaan cuaca");
            } else {
                updateCuaca(cuaca, dataTanggal, dataSuhu, dataKelembapan, dataKeadaanCuaca);
            }
        });
    }

    private void deleteCuaca(Cuaca cuaca) {
        db.collection("Cuaca")
                .document(cuaca.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(UpdateCuaca.this, "Data Cuaca telah dihapus dari Database", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UpdateCuaca.this, DatabaseFragment.class); // Navigate back to main activity or list fragment
                    startActivity(i);
                }).addOnFailureListener(e -> Toast.makeText(UpdateCuaca.this, "Gagal menghapus data Cuaca", Toast.LENGTH_SHORT).show());
    }

    private void updateCuaca(Cuaca cuaca, String dataTanggal, String dataSuhu, String dataKelembapan, String dataKeadaanCuaca) {
        Cuaca updateCuaca = new Cuaca(dataTanggal, dataSuhu, dataKelembapan, dataKeadaanCuaca);
        db.collection("Cuaca")
                .document(cuaca.getId())
                .set(updateCuaca)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(UpdateCuaca.this, "Data Cuaca telah diperbaharui", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UpdateCuaca.this, DatabaseFragment.class); // Navigate back to main activity or list fragment
                    startActivity(i);
                }).addOnFailureListener(e -> Toast.makeText(UpdateCuaca.this, "Gagal memperbaharui Data Cuaca", Toast.LENGTH_SHORT).show());
    }
}
