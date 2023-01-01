package com.tugasakhirpab2.rjn.model;

import java.util.List;

public class DetailKerjaModel {

    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public class Result
    {
        private String idKerja;
        private String deskripsi;
        private String skillReq;

        public String getIdKerja() {
            return idKerja;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public String getSkillReq() {
            return skillReq;
        }
    }
}
