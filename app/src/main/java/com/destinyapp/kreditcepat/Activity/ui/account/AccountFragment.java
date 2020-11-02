package com.destinyapp.kreditcepat.Activity.ui.account;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.destinyapp.kreditcepat.API.ApiRequest;
import com.destinyapp.kreditcepat.API.RetroServer;
import com.destinyapp.kreditcepat.Activity.About.AboutActivity;
import com.destinyapp.kreditcepat.Activity.HomeActivity;
import com.destinyapp.kreditcepat.Activity.LoginActivity;
import com.destinyapp.kreditcepat.Activity.PeminjamanActivity;
import com.destinyapp.kreditcepat.Activity.PermintaanActivity;
import com.destinyapp.kreditcepat.Activity.RegisterActivity;
import com.destinyapp.kreditcepat.Model.Method;
import com.destinyapp.kreditcepat.Model.ResponseModel;
import com.destinyapp.kreditcepat.R;
import com.destinyapp.kreditcepat.SharedPreferance.DB_Helper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
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


public class AccountFragment extends Fragment {
    DB_Helper dbHelper;
    String id,email,nama,telpon,alamat,nik,level;
    LinearLayout masuk,keluar,daftar,tentang,user,admin,pembayaran,history,keuangan,pelunasan,permintaan,peminjaman;
    Date dt;

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
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        masuk=view.findViewById(R.id.linearMasuk);
        keluar=view.findViewById(R.id.linearKeluar);
        daftar=view.findViewById(R.id.linearDaftar);
        tentang=view.findViewById(R.id.linearTentang);
        user=view.findViewById(R.id.linearLaporanDataUser);
        admin=view.findViewById(R.id.linearLaporanDataAdmin);
        pembayaran=view.findViewById(R.id.linearPermintaan);
        history=view.findViewById(R.id.linearLaporanHistory);
        keuangan=view.findViewById(R.id.linearLaporanKeuangan);
        pelunasan=view.findViewById(R.id.linearPelunasan);
        permintaan=view.findViewById(R.id.linearPembayaran);
        peminjaman=view.findViewById(R.id.linearPeminjaman);
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
        if (nama!=null){
            masuk.setVisibility(View.GONE);
            daftar.setVisibility(View.GONE);
            keluar.setVisibility(View.VISIBLE);
            user.setVisibility(View.GONE);
            admin.setVisibility(View.GONE);
            pembayaran.setVisibility(View.GONE);
            history.setVisibility(View.GONE);
            keuangan.setVisibility(View.GONE);
            permintaan.setVisibility(View.GONE);
            pelunasan.setVisibility(View.GONE);
            if (level.equals("Admin")){
                user.setVisibility(View.VISIBLE);
                admin.setVisibility(View.VISIBLE);
                pembayaran.setVisibility(View.VISIBLE);
                history.setVisibility(View.VISIBLE);
                keuangan.setVisibility(View.VISIBLE);
                permintaan.setVisibility(View.VISIBLE);
                peminjaman.setVisibility(View.VISIBLE);
            }else{
                pelunasan.setVisibility(View.VISIBLE);
            }
        }else{
            masuk.setVisibility(View.VISIBLE);
            daftar.setVisibility(View.VISIBLE);
            keluar.setVisibility(View.GONE);
            user.setVisibility(View.GONE);
            admin.setVisibility(View.GONE);
            pembayaran.setVisibility(View.GONE);
            history.setVisibility(View.GONE);
            keuangan.setVisibility(View.GONE);
            pelunasan.setVisibility(View.GONE);
            permintaan.setVisibility(View.GONE);
            peminjaman.setVisibility(View.GONE);
        }
        permintaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PermintaanActivity.class);
                startActivity(intent);
            }
        });
        peminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PeminjamanActivity.class);
                startActivity(intent);
            }
        });
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        pelunasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                Toast.makeText(getActivity(), "Silahkan Pinjam dulu dananya", Toast.LENGTH_SHORT).show();
                            }else{
                                dialog.show();
                                tvTagihan.setVisibility(View.GONE);
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
        });
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer SuaraMe = MediaPlayer.create(getActivity(),R.raw.out);
                SuaraMe.start();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Anda Yakin ingin Logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                dbHelper = new DB_Helper(getActivity());
                                dbHelper.userLogout(getActivity());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        //Set your icon here
                        .setTitle("Perhatian !!!")
                        .setIcon(R.drawable.ic_close_black_24dp);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        final Method method = new Method();
        dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(method.PDFUser(nama,"Jakarta, "+method.Tanggal(String.valueOf(dt)))));
                startActivity(browserIntent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(method.PDFAdmin(nama,"Jakarta, "+method.Tanggal(String.valueOf(dt)))));
                startActivity(browserIntent);
            }
        });
        keuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(method.PDFLunas(nama,"Jakarta, "+method.Tanggal(String.valueOf(dt)))));
                startActivity(browserIntent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(method.PDFBelumLunas(nama,"Jakarta, "+method.Tanggal(String.valueOf(dt)))));
                startActivity(browserIntent);
            }
        });
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
