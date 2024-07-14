package com.example.tenkisen.ui.database;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tenkisen.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CuacaDetails extends AppCompatActivity {
    // creating variables for our recycler view,
    // array list, adapter, firebase firestore
    // and our progress bar.
    private RecyclerView cuacaRV;
    private ArrayList<Cuaca> cuacaArrayList;
    private CuacaRVAdapter cuacaRVAdapter;
    private FirebaseFirestore db;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuaca_details);

        // initializing variables.
        cuacaRV = findViewById(R.id.idRVCuaca);
        loadingPB = findViewById(R.id.idProgressBar);

        // initializing our firestore
        db = FirebaseFirestore.getInstance();

        // creating our new array list
        cuacaArrayList = new ArrayList<>();
        cuacaRV.setHasFixedSize(true);
        cuacaRV.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our adapter class.
        cuacaRVAdapter = new CuacaRVAdapter(cuacaArrayList, this);

        // setting adapter to our recycler view.
        cuacaRV.setAdapter(cuacaRVAdapter);

        // method to get data from Firestore.
        getCuacaData();
    }

    private void getCuacaData() {
        // Clear the existing data
        cuacaArrayList.clear();

        // fetching data from Firestore
        db.collection("Cuaca")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // hiding our progress bar.
                            loadingPB.setVisibility(ProgressBar.GONE);

                            // getting the data and adding to array list
                            for (DocumentSnapshot document : task.getResult()) {
                                Cuaca cuaca = document.toObject(Cuaca.class);
                                cuacaArrayList.add(cuaca);
                            }

                            // notifying adapter to update data
                            cuacaRVAdapter.notifyDataSetChanged();
                        } else {
                            // handling errors here
                            loadingPB.setVisibility(ProgressBar.GONE);
                        }
                    }
                });
    }
}
