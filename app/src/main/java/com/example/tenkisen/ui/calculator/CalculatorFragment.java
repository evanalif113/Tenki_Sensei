package com.example.tenkisen.ui.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tenkisen.databinding.FragmentCalculatorBinding;

public class CalculatorFragment extends Fragment {

    private FragmentCalculatorBinding binding;

    @Override
    public View onCreateView(@NonNull
                                 LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get references to UI elements in Kalkulator Suhu
        final EditText editTextDryBulb = binding.dryBulbInput;
        final EditText editTextWetBulb = binding.wetBulbInput;
        final Button buttonCalculate = binding.calculateButton;
        final TextView textViewHumidity = binding.HumidityResult;
        final TextView textViewFeelslike = binding.FeelsResult;
        final TextView textViewDewpoint = binding.DewpointResult;

        // Set OnClickListener for the calculate button
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dryBulbStr = editTextDryBulb.getText().toString();
                String wetBulbStr = editTextWetBulb.getText().toString();

                if (!dryBulbStr.isEmpty() && !wetBulbStr.isEmpty()) {
                    double dryBulb = Double.parseDouble(dryBulbStr);
                    double wetBulb = Double.parseDouble(wetBulbStr);

                    // Calculate dew point
                    double dewPoint = calculateDewPoint(dryBulb, wetBulb);

                    // Calculate feels-like temperature
                    double feelsLike = calculateFeelsLike(dryBulb, wetBulb);

                    // Calculate humidity
                    double humidity = calculateHumidity(dryBulb, wetBulb);

                    // Display the results
                    textViewFeelslike.setText("Suhu dirasakan: " + String.format("%.2f", feelsLike) + "°C");
                    textViewDewpoint.setText("Titik Embun: " + String.format("%.2f", dewPoint) + "°C");
                    textViewHumidity.setText("Kelembapan: " + String.format("%.2f", humidity) + "%");
                } else {
                    textViewFeelslike.setText("Harap masukkan nilai suhu bola kering dan suhu bola basah.");
                    textViewDewpoint.setText("");
                    textViewHumidity.setText("");
                }
            }
        });

        return root;
    }

    private double calculateDewPoint(double dryBulb, double wetBulb) {
        // Rumus approximasi untuk menghitung titik embun
        double a = 17.27;
        double b = 237.7;
        double alpha = ((a * wetBulb) / (b + wetBulb)) + Math.log(relativeHumidity(dryBulb, wetBulb) / 100.0);
        return (b * alpha) / (a - alpha);
    }

    private double calculateHumidity(double dryBulb, double wetBulb) {
        // Rumus approximasi untuk menghitung kelembapan relatif
        double dryBulbTempK = dryBulb + 273.15;
        double wetBulbTempK = wetBulb + 273.15;
        double e = 6.11 * Math.exp((17.67 * dryBulb) / (dryBulb + 243.5));
        double e1 = 6.11 * Math.exp((17.67 * wetBulb) / (wetBulb + 243.5));
        double rh = (e1 - (0.00066 * 1013 * (dryBulb - wetBulb))) / e * 100;
        return rh;
    }

    private double relativeHumidity(double dryBulb, double wetBulb) {
        // Rumus approximasi untuk menghitung kelembapan relatif dari suhu bola kering dan bola basah
        double dryBulbTempK = dryBulb + 273.15;
        double wetBulbTempK = wetBulb + 273.15;
        double e = 6.11 * Math.exp((17.67 * dryBulb) / (dryBulb + 243.5));
        double e1 = 6.11 * Math.exp((17.67 * wetBulb) / (wetBulb + 243.5));
        return ((e1 - (0.00066 * 1013 * (dryBulb - wetBulb))) / e) * 100;
    }
    private double calculateFeelsLike(double dryBulb, double wetBulb) {
        // Rumus approximasi untuk menghitung suhu dirasakan
        // Perhatikan ini adalah rumus sederhana, untuk aplikasi riil gunakan rumus yang lebih akurat
        return 0.8*dryBulb+((calculateHumidity(dryBulb, wetBulb)*dryBulb)/500);
    }
}
