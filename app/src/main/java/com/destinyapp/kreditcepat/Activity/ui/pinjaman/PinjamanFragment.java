package com.destinyapp.kreditcepat.Activity.ui.pinjaman;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.destinyapp.kreditcepat.API.ApiRequest;
import com.destinyapp.kreditcepat.API.RetroServer;
import com.destinyapp.kreditcepat.Activity.RegisterActivity;
import com.destinyapp.kreditcepat.Model.Method;
import com.destinyapp.kreditcepat.Model.ResponseModel;
import com.destinyapp.kreditcepat.R;
import com.destinyapp.kreditcepat.SharedPreferance.DB_Helper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinjamanFragment extends Fragment {
    Spinner Pinjaman,JangkaWaktu;
    Method method;
    TextView Tanggal,Jumlah,Komisi,Total;
    DB_Helper dbHelper;
    String id,email,nama,telpon,alamat,nik,level;
    LinearLayout bayar;


    //Dellaroy Logic
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private Uri mMediaUri;
    private static final int CAMERA_PIC_REQUEST = 1111;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    private String mediaPath;

    private Button btnCapturePicture;

    private String mImageFileLocation = "";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ProgressDialog pDialog;
    String postBukti= "";


    //ONCLICK
    Boolean Bukti = false;


    Dialog dialog;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Button submit,upload,close;
    ImageView bukti;
    TextView tvBukti,tvTagihan;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pinjaman, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Pinjaman=view.findViewById(R.id.spinnerJumlahPinjaman);
        JangkaWaktu=view.findViewById(R.id.spinnerWaktu);
        Tanggal=view.findViewById(R.id.tvWaktuPembayaran);
        Jumlah=view.findViewById(R.id.tvJumlahPinjaman);
        Komisi=view.findViewById(R.id.tvKomisi);
        Total=view.findViewById(R.id.tvTotalPembayaran);
        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_pembayaran);
        submit=dialog.findViewById(R.id.btnSubmit);
        upload=dialog.findViewById(R.id.btnUpload);
        close=dialog.findViewById(R.id.btnClose);
        bukti=dialog.findViewById(R.id.ivImageBukti);
        tvBukti=dialog.findViewById(R.id.tvBukti);
        tvTagihan=dialog.findViewById(R.id.tvTagihan);
        dbHelper = new DB_Helper(getActivity());
        Cursor cursor = dbHelper.checkSession();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                id = cursor.getString(0);
                email = cursor.getString(1);
                nama = cursor.getString(2);
                telpon = cursor.getString(3);
                alamat = cursor.getString(4);
                nik = cursor.getString(5);
                level = cursor.getString(6);
            }
        }
        bayar = view.findViewById(R.id.linearBayar);
        if (email == null){
            bayar.setBackgroundResource(R.drawable.button_rounded_disable);
            bayar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            bayar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (level.equals("Admin")){
                        Toast.makeText(getActivity(), "Admin Tidak Dapat Meminjam", Toast.LENGTH_SHORT).show();
                    }else{
                        final ProgressDialog pd = new ProgressDialog(getActivity());
                        pd.setMessage("Sedang Mengecheck Transaksi");
                        pd.setCancelable(false);
                        pd.show();
                        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                        Call<ResponseModel> checker = api.CheckPinjaman(id);
                        checker.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                try {
                                    if (response.body().getStatus().equals("failed")){
                                        MendapatkanDana();
                                    }else{
                                       CheckPembayaran();
                                    }
                                }catch (Exception e){
                                    Toast.makeText(getActivity(), "Terjadi kesalahan pada = "+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                                pd.hide();
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                                pd.hide();
                            }
                        });

                    }

                }
            });
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setMessage("Sedang Menyimpan data ke Server");
                pd.setCancelable(false);
                pd.show();
                File fileBukti = new File(postBukti);
                RequestBody fileReqBodyBukti = RequestBody.create(MediaType.parse("image/*"), fileBukti);
                MultipartBody.Part partBukti = MultipartBody.Part.createFormData("bukti", fileBukti.getName(), fileReqBodyBukti);
                ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                Call<ResponseModel> Upload = api.UploadBukti(
                        RequestBody.create(MediaType.parse("text/plain"),id),
                        partBukti
                );
                Upload.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        pd.hide();
                        try {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Terjadi kesalahan = "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        pd.hide();
                        Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        method=new Method();
        Pinjaman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Logic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        JangkaWaktu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Logic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void CheckPembayaran(){
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Sedang Mencoba Mengecheck Bukti");
        pd.setCancelable(false);
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getData = api.CheckPembayaran(id);
        getData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("failed")){
                        dialog.show();
                        tvTagihan.setVisibility(View.VISIBLE);
                        upload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bukti = true;
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                            }
                        });
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.hide();
                            }
                        });
                    }else{
                        Toast.makeText(getActivity(), "Bukti Pembayaran belum di Check oleh harap tunggu beberapa saat lagi", Toast.LENGTH_SHORT).show();
                    }
                    pd.hide();
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Terjadi Kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                pd.hide();
            }
        });
    }
    private void MendapatkanDana(){
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Sedang Mencoba Menulis Permintaan Dana");
        pd.setCancelable(false);
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetData = api.Transaksi(
                id,
                Komisi.getText().toString(),
                Jumlah.getText().toString(),
                method.getTodays(),
                method.getTanggalPembayaran(JangkaWaktu.getSelectedItem().toString()),
                Total.getText().toString(),
                "PERMINTAAN"
        );
        GetData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    Toast.makeText(getActivity(), "Data akan di verifikasi oleh Admin", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Terjadi Kesalahan "+e.toString(), Toast.LENGTH_SHORT).show();
                }
                pd.hide();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                pd.hide();
            }
        });
    }
    private double ifelses(String data){
        Double nilai = 1000000.0;
        if (data.equals("Rp.1.000.000")){
            nilai = 1000000.0;
        }else if(data.equals("Rp.2.000.000")){
            nilai = 2000000.0;
        }else if(data.equals("Rp.3.000.000")){
            nilai = 3000000.0;
        }else if(data.equals("Rp.4.000.000")){
            nilai = 4000000.0;
        }else if(data.equals("Rp.5.000.000")){
            nilai = 5000000.0;
        }else if(data.equals("Rp.6.000.000")){
            nilai = 6000000.0;
        }else if(data.equals("Rp.7.000.000")){
            nilai = 7000000.0;
        }else if(data.equals("Rp.8.000.000")){
            nilai = 8000000.0;
        }
        return nilai;
    }
//    private String ifelsetanggal(String data){
//        String ifelsestanggal = data;
//        if (data.equals("1")){
//            ifelsestanggal = "01";
//        }else if(data.equals("2")){
//            ifelsestanggal = "02";
//        }else if(data.equals("3")){
//            ifelsestanggal = "03";
//        }else if(data.equals("4")){
//            ifelsestanggal = "05";
//        }else if(data.equals("5")){
//            ifelsestanggal = "05";
//        }else if(data.equals("6")){
//            ifelsestanggal = "06";
//        }else if(data.equals("7")){
//            ifelsestanggal = "07";
//        }else if(data.equals("8")){
//            ifelsestanggal = "08";
//        }else if(data.equals("9")){
//            ifelsestanggal = "09";
//        }
//        return ifelsestanggal;
//    }
    private void Logic(){
        Double bunga=ifelses(Pinjaman.getSelectedItem().toString())*0.008*Double.parseDouble(JangkaWaktu.getSelectedItem().toString());
        Jumlah.setText(Pinjaman.getSelectedItem().toString());
        Komisi.setText(method.MagicRP(bunga));
        Total.setText(method.MagicRP(ifelses(Pinjaman.getSelectedItem().toString())+bunga));
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, Integer.parseInt(JangkaWaktu.getSelectedItem().toString()));
        dt = c.getTime();
        Tanggal.setText(method.Tanggal(String.valueOf(dt)));
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }
    File createImageFile() throws IOException {
        Logger.getAnonymousLogger().info("Generating the image - method started");

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        // Here we specify the environment location and the exact path where we want to save the so-created file
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app");
        Logger.getAnonymousLogger().info("Storage directory set");

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir();

        // Here we create the file using a prefix, a suffix and a directory
        File image = new File(storageDirectory, imageFileName + ".jpg");
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set");

        mImageFileLocation = image.getAbsolutePath();
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
            if (data != null) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                // Set the Image in ImageView for Previewing the Media

//                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();
               if(Bukti){
                    postBukti = mediaPath;
                    String filename=postBukti.substring(postBukti.lastIndexOf("/")+1);
                    bukti.setVisibility(View.VISIBLE);
                    tvBukti.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    bukti.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    tvBukti.setText(filename);
                    Bukti=false;
                }
            }
        }
    }
}
