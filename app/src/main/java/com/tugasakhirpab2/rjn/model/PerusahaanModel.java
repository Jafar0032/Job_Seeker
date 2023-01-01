package com.tugasakhirpab2.rjn.model;

import java.util.List;

import javax.xml.transform.Result;

public class PerusahaanModel {

    private List<Result> result;

    public List<Result> getResult()
    {
        return result;
    }

    public class Result
    {
        private String idPerusahaan;
        private String namaPerusahaan;
        private String logoPerusahaan;

        public String getIdPerusahaan() {
            return idPerusahaan;
        }

        public String getNamaPerusahaan() {
            return namaPerusahaan;
        }

        public String getLogoPerusahaan() {
            return logoPerusahaan;
        }
    }
}
