package com.tugasakhirpab2.rjn.retrofit;

import com.tugasakhirpab2.rjn.model.DetailKerjaModel;
import com.tugasakhirpab2.rjn.model.KategoriModel;
import com.tugasakhirpab2.rjn.model.KerjaModel;
import com.tugasakhirpab2.rjn.model.PerusahaanModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIEndpoint {

    // End Point Perusahaan
<<<<<<< Updated upstream
//    @GET("perusahaan.php")
//    Call<>
=======
    @GET("perusahaan.php")
    Call<PerusahaanModel> getPerusahaan();

    // End Point Kategori
    @GET("kategori.php")
    Call<KategoriModel> getKategori();

    // End Point Kerja
    @GET("kerja.php")
    Call<KerjaModel> getKerja();

    // End Point Detail Kerja
    @GET("detail.php")
    Call<DetailKerjaModel> getDetailKerja(@Query("idKerja") String idKerja);
>>>>>>> Stashed changes

}
