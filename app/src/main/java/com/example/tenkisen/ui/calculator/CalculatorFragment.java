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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalculatorViewModel calculatorViewModel =
                new ViewModelProvider(this).get(CalculatorViewModel.class);

        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get references to UI elements
        final EditText editTextTemperature = binding.secondInput;
        final EditText editTextHumidity = binding.firstInput;
        final Button buttonCalculate = binding.calculateButton;
        final TextView textViewResult = binding.resultText;
        final TextView textViewDewPoint = binding.resultText2;

        // Set OnClickListener for the calculate button
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempStr = editTextTemperature.getText().toString();
                String humidityStr = editTextHumidity.getText().toString();

                if (!tempStr.isEmpty() && !humidityStr.isEmpty()) {
                    double temperature = Double.parseDouble(tempStr);
                    double humidity = Double.parseDouble(humidityStr);

                    // Calculate dew point
                    double dewPoint = calculateDewPoint(temperature, humidity);

                    // Calculate feels-like temperature
                    double feelsLike = calculateFeelsLike(temperature, humidity);

                    // Display the results
                    textViewResult.setText("Suhu dirasakan: " + String.format("%.2f", feelsLike) + "°C");
                    textViewDewPoint.setText("Titik Embun: " + String.format("%.2f", dewPoint) + "°C");
                } else {
                    textViewResult.setText("Harap masukkan nilai suhu dan kelembapan.");
                    textViewDewPoint.setText("");
                }
            }
        });

        return root;
    }

    private double calculateDewPoint(double temperature, double humidity) {
        double a = 17.27;
        double b = 237.7;
        double alpha = ((a * temperature) / (b + temperature)) + Math.log(humidity / 100);
        return (b * alpha) / (a - alpha);
    }

    private double calculateFeelsLike(double temperature, double humidity) {
        // Simplified formula for calculating the heat index ("feels-like" temperature)
        return 0.8*temperature + ((humidity*temperature)/500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
