package com.example.tenkisen.ui.learning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}