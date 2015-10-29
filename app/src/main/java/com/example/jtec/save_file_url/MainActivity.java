package com.example.jtec.save_file_url;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void btd_download_img(View view) throws IOException {
        String filedow = "http://192.168.0.15/a/sach/bai_1/img/1.jpg";
        String file = downloadFile(filedow,"img");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file);
    }
    public void btd_download_mp3(View view) throws IOException {
        String filedow = "http://192.168.0.15/a/sach/bai_1/1.mp3";
        String file = downloadFile(filedow,"mp3");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file);
    }

    public void btd_download_html(View view) throws IOException {
        String filedow = "http://192.168.0.15/a/sach/bai_1/bai1.html";
        String file = downloadFile(filedow,"html");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file);
    }
    public void btd_download_js(View view) throws IOException {
        String filedow = "http://192.168.0.15/a/sach/js/jquery-1.10.2.min.js";
        String file = downloadFile(filedow,"js");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file);
    }
    public void btd_download_css(View view) throws IOException {
        String filedow = "http://192.168.0.15/a/sach/css/bootstrap.css";
        String file = downloadFile(filedow,"css");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file);
    }

        private String saveDir = "/sdcard/";
        private String directory_parrent = "a1";
        private String directory = "";
        private String type_file = "";
        private String fileName = "";
        String saveFilePath = "";

    public String downloadFile(String dwnload_file_path, String type) throws IOException {
        //check thu muc ext hay khong
        File f1 = new File(saveDir + directory_parrent);
        if (!f1.exists()) {
            f1.mkdir();
        }
        //check folder chuong
        directory = directory_parrent+"/chuong_1";
        File f = new File(saveDir + directory);
        if (!f.exists()) {
            f.mkdir();
        }
        //gan file
        fileName = dwnload_file_path.substring(dwnload_file_path.lastIndexOf("/") + 1, dwnload_file_path.length());
        File saveFilePath1 = new File(saveDir + directory + File.separator + fileName);

        saveFilePath = saveDir + directory + File.separator + fileName;
        //check folder la img hay khong
        if (type=="img"){
            type_file="/img/";
            File f_img = new File(saveDir + directory + type_file);
            if (!f_img.exists()) {
                f_img.mkdir();
            }
            saveFilePath1 = new File(saveDir + directory + type_file + fileName);
            saveFilePath = saveDir + directory +type_file+ fileName;
        }

        if (saveFilePath1.exists()) {
            saveFilePath = saveFilePath1.toString();
            Toast.makeText(getBaseContext(), "File Ext", Toast.LENGTH_SHORT).show();
            return saveFilePath1.toString();
        } else {

            try {
                int BUFFER_SIZE = 4096;
                URL url = new URL(dwnload_file_path);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                int responseCode = httpConn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    String disposition = httpConn.getHeaderField("Content-Disposition");
                    if (disposition != null) {
                        // extracts file name from header field
                        int index = disposition.indexOf("filename=");
                        if (index > 0) {
                            fileName = disposition.substring(index + 10, disposition.length() - 1);
                        }
                    } else {
                        // extracts file name from URL
                        fileName = dwnload_file_path.substring(dwnload_file_path.lastIndexOf("/") + 1, dwnload_file_path.length());
                    }
                    // opens input stream from the HTTP connection
                    InputStream inputStream = httpConn.getInputStream();


                    // opens an output stream to save into file
                    FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                    int bytesRead = -1;
                    byte[] buffer = new byte[BUFFER_SIZE];
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.close();
                    inputStream.close();
                    Toast.makeText(getBaseContext(), "File downloaded", Toast.LENGTH_SHORT).show();
                    //textView.setText("File xml downloaded");
                } else {
                    Toast.makeText(getBaseContext(), "No file to download. Server replied HTTP code" + responseCode, Toast.LENGTH_SHORT).show();
                    // textView.setText("No file .xml downloaded");
                }
                httpConn.disconnect();


            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Error:" + e, Toast.LENGTH_SHORT).show();
            }
            return saveFilePath;
        }

    }


}
