package com.tugasakhirpab2.rjn.retrofit;

import com.tugasakhirpab2.rjn.model.DetailKerjaModel;
import com.tugasakhirpab2.rjn.model.KerjaModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIEndpoint {

    // End Point Kerja
    @GET("kerja.php")
    Call<KerjaModel> getKerja();

    // End Point Detail Kerja
    @GET("detail.php")
    Call<DetailKerjaModel> getDetailKerja(@Query("idKerja") String idKerja);

}
