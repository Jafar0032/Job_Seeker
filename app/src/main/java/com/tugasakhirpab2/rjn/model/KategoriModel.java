package com.tugasakhirpab2.rjn.model;

import java.util.List;

public class KategoriModel {

    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public class Result
    {
        private String idKategori;
        private String namaKategori;

        public String getIdKategori() {
            return idKategori;
        }

        public String getNamaKategori() {
            return namaKategori;
        }
    }
}
