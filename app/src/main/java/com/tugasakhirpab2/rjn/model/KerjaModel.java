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
        private String idKategori;
        private String idPerusahaan;
        private String jobDesk;
        private String gaji;
        private String fulltime;

        public String getIdKerja() {
            return idKerja;
        }

        public String getIdKategori() {
            return idKategori;
        }

        public String getIdPerusahaan() {
            return idPerusahaan;
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
