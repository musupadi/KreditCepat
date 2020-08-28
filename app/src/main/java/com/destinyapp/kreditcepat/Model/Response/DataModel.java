package com.destinyapp.kreditcepat.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {
    //User
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("nama")
    @Expose
    public String nama;

    @SerializedName("alamat")
    @Expose
    public String alamat;

    @SerializedName("telpon")
    @Expose
    public String telpon;

    @SerializedName("nik")
    @Expose
    public String nik;

    @SerializedName("level")
    @Expose
    public String level;

    //Transaksi
    @SerializedName("id_transaksi")
    @Expose
    public String id_transaksi;

    @SerializedName("tanggal_transaksi")
    @Expose
    public String tanggal_Transaksi;

    @SerializedName("tanggal_pembayaran")
    @Expose
    public String tanggal_pembayaran;

    @SerializedName("jumlah_pinjaman")
    @Expose
    public String jumlah_pinjaman;

    @SerializedName("komisi")
    @Expose
    public String komisi;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("masa_tenggang")
    @Expose
    public String masa_tenggang;

    //
    @SerializedName("bukti")
    @Expose
    public String bukti;

    @SerializedName("id_user")
    @Expose
    public String id_user;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getTanggal_Transaksi() {
        return tanggal_Transaksi;
    }

    public void setTanggal_Transaksi(String tanggal_Transaksi) {
        this.tanggal_Transaksi = tanggal_Transaksi;
    }

    public String getTanggal_pembayaran() {
        return tanggal_pembayaran;
    }

    public void setTanggal_pembayaran(String tanggal_pembayaran) {
        this.tanggal_pembayaran = tanggal_pembayaran;
    }

    public String getJumlah_pinjaman() {
        return jumlah_pinjaman;
    }

    public void setJumlah_pinjaman(String jumlah_pinjaman) {
        this.jumlah_pinjaman = jumlah_pinjaman;
    }

    public String getKomisi() {
        return komisi;
    }

    public void setKomisi(String komisi) {
        this.komisi = komisi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMasa_tenggang() {
        return masa_tenggang;
    }

    public void setMasa_tenggang(String masa_tenggang) {
        this.masa_tenggang = masa_tenggang;
    }

    public String getBukti() {
        return bukti;
    }

    public void setBukti(String bukti) {
        this.bukti = bukti;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
