package com.example.tenkisen.ui.database;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tenkisen.databinding.FragmentDatabaseBinding;

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

        // EditText for date picker
        EditText edtTanggal = binding.idEdtTanggal;

        // Set click listener to show DatePickerDialog
        edtTanggal.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                        edtTanggal.setText(date);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
