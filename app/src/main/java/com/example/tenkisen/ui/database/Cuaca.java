package com.example.tenkisen.ui.database;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

// we have to implement our modal class
// with serializable so that we can pass
// our object class to new activity on
// our item click of recycler view.
public class Cuaca implements Serializable {

    // getter method for our id
    public String getId() {
        return id;
    }

    // setter method for our id
    public void setId(String id) {
        this.id = id;
    }

    // we are using exclude because
    // we are not saving our id
    @Exclude
    private String id;

    // variables for storing our data.
    private String
            dataTanggal,
            dataSuhu,
            dataKelembapan,
            dataCuaca;

    public Cuaca() {
        // empty constructor required for Firebase.
    }

    // Constructor for all variables.
    public Cuaca(String dataTanggal, String dataSuhu, String dataKelembapan, String dataCuaca) {
        this.dataTanggal = dataTanggal;
        this.dataSuhu = dataSuhu;
        this.dataKelembapan = dataKelembapan;
        this.dataCuaca = dataCuaca;
    }

    // getter methods for all variables.
    public String getDataTanggal() {
        return dataTanggal;
    }

    public String getDataSuhu() {
        return dataSuhu;
    }

    public String getDataKelembapan() {
        return dataKelembapan;
    }

    public String getDataCuaca() {
        return dataCuaca;
    }

    // setter method for all variables.
    public void setDataTanggal(String dataTanggal) {
        this.dataTanggal = dataTanggal;
    }
    public void setDataSuhu(String dataSuhu) {
        this.dataSuhu = dataSuhu;
    }
    public void setDataKelembapan(String dataKelembapan) {
        this.dataKelembapan = dataKelembapan;
    }
    public void setDataCuaca(String dataCuaca) {
        this.dataCuaca = dataCuaca;
    }
}

