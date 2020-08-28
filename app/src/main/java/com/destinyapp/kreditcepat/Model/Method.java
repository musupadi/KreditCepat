package com.destinyapp.kreditcepat.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Method {
    public String PDFUser(String Admin,String Tanggal){
        String link = "http://kreditcepat.destinyconsultant.tech/api/Pdf?admin="+Admin+"&tanggal="+Tanggal+"";
        return link;
    }
    public String PDFAdmin(String Admin,String Tanggal){
        String link = "http://kreditcepat.destinyconsultant.tech/api/Pdf/Admin?admin="+Admin+"&tanggal="+Tanggal+"";
        return link;
    }
    public String PDFLunas(String Admin,String Tanggal){
        String link = "http://kreditcepat.destinyconsultant.tech/api/Pdf/Lunas?admin="+Admin+"&tanggal="+Tanggal+"";
        return link;
    }
    public String PDFBelumLunas(String Admin,String Tanggal){
        String link = "http://kreditcepat.destinyconsultant.tech/api/Pdf/BelumLunas?admin="+Admin+"&tanggal="+Tanggal+"";
        return link;
    }

    public String MagicRP(double nilai){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        BigDecimal bd1 = new BigDecimal(nilai).setScale(0, RoundingMode.HALF_UP);
        String Format = formatRupiah.format(bd1);
        String MAGIC1 = Format.replace("Rp","Rp.");
        return MAGIC1;
    }
    public String MagicChange(String magic){
        String MAGIC1 = magic.replace("Rp","");
        String MAGIC2 = MAGIC1.replace(",","");
        return MAGIC2.replace(".","");
    }
    public static String dayName(String inputDate, String format){
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
    }
    public String getToday(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String thisDay = dateFormat.format(date);
        String today = dayName(thisDay,"dd/MM/yyyy");
        String HariIni = "Senin";
        if(today.equals("Monday")){
            HariIni = "Senin";
        }else if(today.equals("Tuesday")){
            HariIni = "Selasa";
        }else if(today.equals("Wednesday")){
            HariIni = "Rabu";
        }else if(today.equals("Thursday")){
            HariIni = "Kamis";
        }else if(today.equals("Friday")){
            HariIni = "Jumat";
        }else if(today.equals("Saturday")){
            HariIni = "Sabtu";
        }else if(today.equals("Sunday")){
            HariIni = "Minggu";
        }
        return HariIni;
    }
    public String Tanggals(String tanggal){
        String tgl = tanggal.substring(8,10);
        String bulan = tanggal.substring(4,7);
        String tahun = tanggal.substring(30,34);
        String Bulan = "01";
        if (bulan.equals("Jan")){
            Bulan = "01";
        }else if(bulan.equals("Feb")){
            Bulan = "02";
        }else if(bulan.equals("Mar")){
            Bulan = "03";
        }else if(bulan.equals("Apr")){
            Bulan = "04";
        }else if(bulan.equals("May")){
            Bulan = "05";
        }else if(bulan.equals("Jun")){
            Bulan = "06";
        }else if(bulan.equals("Jul")){
            Bulan = "07";
        }else if(bulan.equals("Aug")){
            Bulan = "08";
        }else if(bulan.equals("Sep")){
            Bulan = "09";
        }else if(bulan.equals("Oct")){
            Bulan = "10";
        }else if(bulan.equals("Nov")){
            Bulan = "11";
        }else if(bulan.equals("Dec")){
            Bulan = "12";
        }
        return tahun+"-"+Bulan+"-"+tgl;
    }
    public String Tanggal(String tanggal){
        String tgl = tanggal.substring(8,10);
        String bulan = tanggal.substring(4,7);
        String tahun = tanggal.substring(30,34);
        String Bulan = "Januari";
        if (bulan.equals("Jan")){
            Bulan = "Januari";
        }else if(bulan.equals("Feb")){
            Bulan = "Februari";
        }else if(bulan.equals("Mar")){
            Bulan = "Maret";
        }else if(bulan.equals("Apr")){
            Bulan = "April";
        }else if(bulan.equals("May")){
            Bulan = "Mei";
        }else if(bulan.equals("Jun")){
            Bulan = "Juni";
        }else if(bulan.equals("Jul")){
            Bulan = "Juli";
        }else if(bulan.equals("Aug")){
            Bulan = "Agustus";
        }else if(bulan.equals("Sep")){
            Bulan = "September";
        }else if(bulan.equals("Oct")){
            Bulan = "Oktober";
        }else if(bulan.equals("Nov")){
            Bulan = "November";
        }else if(bulan.equals("Dec")){
            Bulan = "Desember";
        }
        return tgl+" "+Bulan+" "+tahun;
    }
    public String Dates(String tanggal){
        String tgl = tanggal.substring(8,10);
        String bulan = tanggal.substring(4,7);
        String tahun = tanggal.substring(30,34);
        String Bulan = "Januari";
        if (bulan.equals("Jan")){
            Bulan = "01";
        }else if(bulan.equals("Feb")){
            Bulan = "02";
        }else if(bulan.equals("Mar")){
            Bulan = "03";
        }else if(bulan.equals("Apr")){
            Bulan = "04";
        }else if(bulan.equals("May")){
            Bulan = "05";
        }else if(bulan.equals("Jun")){
            Bulan = "06";
        }else if(bulan.equals("Jul")){
            Bulan = "07";
        }else if(bulan.equals("Aug")){
            Bulan = "08";
        }else if(bulan.equals("Sep")){
            Bulan = "09";
        }else if(bulan.equals("Oct")){
            Bulan = "10";
        }else if(bulan.equals("Nov")){
            Bulan = "11";
        }else if(bulan.equals("Dec")){
            Bulan = "12";
        }
        return tahun+"-"+Bulan+"-"+tgl;
    }
    public String getTanggalPembayaran(String Tanggal){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, Integer.parseInt(Tanggal));
        dt = c.getTime();
        return Dates(String.valueOf(dt));
    }
    public String getTodays(){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();
        return Dates(String.valueOf(dt));
    }
}
