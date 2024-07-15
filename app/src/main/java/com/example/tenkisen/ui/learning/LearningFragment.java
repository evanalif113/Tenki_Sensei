package com.example.tenkisen.ui.learning;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tenkisen.databinding.FragmentLearningBinding;

public class LearningFragment extends Fragment {

    private FragmentLearningBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LearningViewModel learningViewModel =
                new ViewModelProvider(this).get(LearningViewModel.class);

        binding = FragmentLearningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button awan = binding.buttonAwan;
        final Button presipitasi = binding.buttonPresipitasi;
        final Button ekstrem = binding.buttonEkstrem;
        final Button angin = binding.buttonAngin;

        // Setting up the onClickListeners for the buttons
        awan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LearnAwan.class);
                startActivity(intent);
            }
        });

        presipitasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LearnPresipitasi.class);
                startActivity(intent);
            }
        });

        ekstrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LearnEkstrem.class);
                startActivity(intent);
            }
        });

        angin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LearnAngin.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
