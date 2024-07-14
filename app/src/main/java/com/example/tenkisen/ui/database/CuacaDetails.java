package com.example.tenkisen.ui.database;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenkisen.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CuacaDetails extends AppCompatActivity {
    // creating variables for our recycler view,
    // array list, adapter, firebase firestore
    // and our progress bar.
    private RecyclerView cuacaRV;
    private ArrayList<Cuaca> cuacaArrayList;
    private CuacaRVAdapter cuacaRVAdapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuaca_details);

        //
        cuacaRV = findViewById(R.id.idRVCuaca);
        loadingPB = findViewById(R.id.idProgressBar);

        db = FirebaseFirestore.getInstance();

        cuacaArrayList = new ArrayList<>();
        cuacaRV.setHasFixedSize(true);
        cuacaRV.setLayoutManager(new LinearLayoutManager(this));

        cuacaRVAdapter = new CuacaRVAdapter(cuacaArrayList, this);

        cuacaRV.setAdapter(cuacaRVAdapter);

    }
}
