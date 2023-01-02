package com.tugasakhirpab2.rjn.model;

import java.util.List;

public class KerjaModel {

    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public class Result
    {
        private String idKerja;
        private String namaPerusahaan;
        private String logoPerusahaan;
        private String alamat;
        private String namaKategori;
        private String jobDesk;
        private String gaji;
        private String fulltime;

        public String getIdKerja() {
            return idKerja;
        }

        public String getNamaPerusahaan() {
            return namaPerusahaan;
        }

        public String getLogoPerusahaan() {
            return logoPerusahaan;
        }

        public String getAlamat() {
            return alamat;
        }

        public String getNamaKategori() {
            return namaKategori;
        }

        public String getJobDesk() {
            return jobDesk;
        }

        public String getGaji() {
            return gaji;
        }

        public String getFulltime() {
            return fulltime;
        }
    }
}
