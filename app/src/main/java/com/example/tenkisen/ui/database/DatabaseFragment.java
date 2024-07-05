package com.example.tenkisen.ui.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tenkisen.databinding.FragmentDatabaseBinding;

public class DatabaseFragment extends Fragment {

    private FragmentDatabaseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DatabaseViewModel notificationsViewModel =
                new ViewModelProvider(this).get(DatabaseViewModel.class);

        binding = FragmentDatabaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}