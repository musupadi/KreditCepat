package com.destinyapp.kreditcepat.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.destinyapp.kreditcepat.R;

public class ProductActivity extends AppCompatActivity {
    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        String data = "<html>\n" +
                "    <p><strong>Jenis pinjaman</strong>:&nbsp;Pinjaman TUNAI yang dicairkan langsung ke rekening bank Anda</p>\n" +
                "    <p><strong>Jangka waktu</strong>:&nbsp;30 hari dengan kemungkinan untuk memperpanjang selama beberapa bulan</p>\n" +
                "    <p><strong>Agunan</strong>:&nbsp;Tidak dibutuhkan</p>\n" +
                "    <p><strong>Jumlah pinjaman minimum</strong>:&nbsp;Rp. 300.000</p>\n" +
                "    <p><strong>Jumlah pinjaman maksimum</strong>:&nbsp;Jumlah maksimum untuk nasabah baru adalah Rp. 1.500.000, tetapi untuk nasabah yang telah terdaftar, jumlah maksimum bisa lebih tinggi</p>\n" +
                "    <p><strong>Perpanjangan</strong>:&nbsp;Anda dapat menunda jatuh tempo pinjaman selama 7, 14 atau 30 hari dengan membayar biaya perpanjangan. Dalam beberapa kasus, klien tidak dapat membayar kembali pinjaman pada tanggal jatuh tempo, maka perpanjangan dapat dilakukan dalam beberapa kali.</p>\n" +
                "    <p><strong>Total Biaya. Nilai pinjaman terendah (tenor 30 hari):</strong></p>\n" +
                "    <p>Persentase total biaya per hari: 0,8%</p>\n" +
                "    <p><br></p>\n" +
                "    <p>Nilai uang yang diterima penerima pinjaman: Rp. 8.000.000</p>\n" +
                "    <p>Nilai uang yang harus dikembalikan: Rp. 9.920.000</p>\n" +
                "    <p>Total biaya: Rp. 1.920.000</p>\n" +
                "    <p>Persentase total biaya per hari: 0,8%</p>\n" +
                "    <p>Bagaimana cara menyampaikan pertanyaan, keluhan, kritik, dan saran?</p>\n" +
                "    <p><img data-cke-saved-src=\"https://kreditcepat.co.id/pointer.png\" src=\"https://kreditcepat.co.id/pointer.png\" alt=\"arrow\">&nbsp;Mekanisme pelayanan pengaduan konsumen</p>\n" +
                "    <p>1.Ajukan pengaduan yang berkaitan dengan pertanyaan, keluhan, kritik, dan saran ke kontak:</p>\n" +
                "    <ul><li><a data-cke-saved-href=\"tel:02180679560\" href=\"tel:02180679560\">02180679560</a></li><li><a data-cke-saved-href=\"mailto:\" href=\"mailto:\">tanya@kreditcepat.co.id</a></li></ul>\n" +
                "    <p>2.Tim layanan konsumen akan memberikan respon paling lama 24 jam</p>\n" +
                "    <p>3.Tim layanan akan mengirimkan formulir yang wajib diisi oleh klien dengan data – data yang valid (data yang terdaftar di sistem penyelenggara)</p>\n" +
                "    <p>4.Tim layanan konsumen akan mulai memverifikasi setelah data – data yang dikirimkan sudah lengkap dan benar</p>\n" +
                "    <p>5.Dalam waktu paling lama 20 hari kerja, tim layanan konsumen akan memberikan solusi yang sudah sesuai dengan analisa dan aturan yang berlaku, untuk dikonfirmasikan kembali oleh klien dalam kurun waktu paling lama 5 (lima) hari kerja</p>\n" +
                "    <p><img data-cke-saved-src=\"https://kreditcepat.co.id/pointer.png\" src=\"https://kreditcepat.co.id/pointer.png\" alt=\"arrow\">&nbsp;Tim layanan konsumen kami akan melayani di waktu operasional kantor:</p>\n" +
                "    <p>Senin – Jumat: 07.00 – 19.00 WIB</p>\n" +
                "    <p>Sabtu – Minggu: 08.00 – 17.00 WIB</p>\n" +
                "    <h2>Siapa yang bisa mengajukan pinjaman?</h2>\n" +
                "    <p>Anda bisa menerima pinjaman kami jika:</p>\n" +
                "    <ul><li><img data-cke-saved-src=\"https://kreditcepat.co.id/pointer.png\" src=\"https://kreditcepat.co.id/pointer.png\" alt=\"arrow\">Berdomisili dan berkerja di Indonesia</li><li><img data-cke-saved-src=\"https://kreditcepat.co.id/pointer.png\" src=\"https://kreditcepat.co.id/pointer.png\" alt=\"arrow\">Harus berumur 21 sampai 55 tahun</li><li><img data-cke-saved-src=\"https://kreditcepat.co.id/pointer.png\" src=\"https://kreditcepat.co.id/pointer.png\" alt=\"arrow\">Memiliki E-KTP</li><li><img data-cke-saved-src=\"https://kreditcepat.co.id/pointer.png\" src=\"https://kreditcepat.co.id/pointer.png\" alt=\"arrow\">Memiliki dan dapat membuktikan pendapatan bulanan (slip gaji)</li></ul>\n" +
                "</html>";
        web = findViewById(R.id.webView);
        web.loadData(data,"text/html","UTF-8");
    }
}
