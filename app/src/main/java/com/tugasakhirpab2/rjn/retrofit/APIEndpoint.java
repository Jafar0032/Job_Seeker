package com.tugasakhirpab2.rjn.retrofit;

import com.tugasakhirpab2.rjn.model.DetailKerjaModel;
import com.tugasakhirpab2.rjn.model.KerjaModel;
import com.tugasakhirpab2.rjn.model.SearchKerjaModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIEndpoint {

    // End Point Kerja
    @GET("kerja.php")
    Call<KerjaModel> getKerja();

    // End Point Rekomendasi Kerja
    @GET("rekomendasikerja.php")
    Call<KerjaModel> getRekomendasiKerja();

    // End Point Detail Kerja
    @GET("detail.php")
    Call<DetailKerjaModel> getDetailKerja(@Query("idKerja") String idKerja);

    // End Point Search
    @GET("kerjasearch.php")
    Call<KerjaModel> getSearchKerja(@Query("jobDesk") String jobDesk);

    // End Point Kategori Komputer
    @GET("kategorikomputer.php")
    Call<KerjaModel> getKategoriKomputer(@Query("jobDesk") String jobDesk);

    // End Point Kategori Akuntansi
    @GET("kategoriakuntansi.php")
    Call<KerjaModel> getKategoriAkuntansi(@Query("jobDesk") String jobDesk);

    // End Point Kategori Marketing
    @GET("kategorimarketing.php")
    Call<KerjaModel> getKategoriMarketing(@Query("jobDesk") String jobDesk);

    // End Point Kategori Design
    @GET("kategoridc.php")
    Call<KerjaModel> getKategoriDesignCreative(@Query("jobDesk") String jobDesk);

    // End Point Kategori Hotel
    @GET("kategorihp.php")
    Call<KerjaModel> getKategoriHotelPariwisata(@Query("jobDesk") String jobDesk);

    // End Point Kategori Arsitektur
    @GET("kategoriarsitektur.php")
    Call<KerjaModel> getKategoriArsitektur(@Query("jobDesk") String jobDesk);

    // End Point Kategori Hukum
    @GET("kategorihukum.php")
    Call<KerjaModel> getKategoriHukum(@Query("jobDesk") String jobDesk);

    // End Point Kategori Engineering
    @GET("kategoriengineering.php")
    Call<KerjaModel> getKategoriEngineering(@Query("jobDesk") String jobDesk);



}
