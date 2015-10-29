package com.example.jtec.save_file_url;

import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= (TextView)findViewById(R.id.textView);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button nut_download_menu = (Button)findViewById(R.id.button7);
        nut_download_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    nut_download_menu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button nut_delete = (Button)findViewById(R.id.button8);
        nut_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nut_delete_allfile();
                textView.setText(webaddress);
            }
        });


        textView.setText(webaddress);
    }

    public void nut_delete_allfile(){
        File dir = new File(Environment.getExternalStorageDirectory()+"/"+directory_parrent);
        Toast.makeText(getApplicationContext(), "bat dau xoa "+dir, Toast.LENGTH_SHORT).show();
        DeleteRecursive(dir);
        Toast.makeText(getApplicationContext(), "Da xoa het ", Toast.LENGTH_SHORT).show();
    }
    void DeleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);

        fileOrDirectory.delete();
    }

    String local="http://192.168.0.15/a/sach";
    String server="http://developer.j-tec.com.vn/projects/android/sach";
    String webaddress=local;
    public void btd_download_img(View view) throws IOException {
        String filedow = webaddress+"/bai_1/img/1.jpg";
        String file = downloadFile(filedow,"img");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file);
    }
    public void btd_download_mp3(View view) throws IOException {
        String filedow = webaddress+"/bai_1/1.mp3";
        String file = downloadFile(filedow,"mp3");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file);
    }

    public void btd_download_html(View view) throws IOException {
        String filedow = webaddress+"/bai_1/bai1.html";
        String file = downloadFile(filedow,"html");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file);
    }
    public void btd_download_js(View view) throws IOException {
        String filedow = webaddress+"/js/jquery-1.10.2.min.js";
        String file = downloadFile(filedow,"js");
        String file2 = downloadFile(webaddress+"/js/jquery.mobile-1.4.5.min.js","js");
        String file3 = downloadFile(webaddress+"/js/script_custom.js","js");

        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file+"---"+file2+"---"+file3);
    }
    public void btd_download_css(View view) throws IOException {
        String filedow = webaddress+"/css/css_test.css";
        String file = downloadFile(filedow,"css");
        String file1 = downloadFile(webaddress+"/css/style_custom.css","css");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file+"----"+file1);
    }
    public void btd_download_hinhanh(View view) throws IOException {
        String filedow = webaddress+"/hinhanh/8.jpg";
        String file = downloadFile(filedow,"hinhanh");
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(file);
    }

    public void nut_download_menu() throws IOException {
        String filedow = webaddress+"/menu.html";
        String file = downloadFile(filedow,"menu");
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

        //get name
        //http://192.168.0.15/a/sach/bai_1/bai1.html
        String[] parts = dwnload_file_path.split("/");
        String part1 = parts[parts.length-2]; // bai_1
        String part2 = parts[parts.length-3]; // sach
        //Toast.makeText(MainActivity.this, part1, Toast.LENGTH_SHORT).show();


        if (type!="menu"){
            //check folder chuong
            directory = directory_parrent+"/"+part1;
            if (type=="img"){ //neu la thu muc img cua bai thi them folder bai_1 truoc sau do moi them thu muc . img
                directory = directory_parrent+"/"+part2;
            }
            File f = new File(saveDir + directory);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        //gan file
        fileName = dwnload_file_path.substring(dwnload_file_path.lastIndexOf("/") + 1, dwnload_file_path.length());
        File saveFilePath1 = new File(saveDir + directory + File.separator + fileName);

        saveFilePath = saveDir + directory + File.separator + fileName;
        //check folder la img hay khong
        if (type=="img"){
            type_file="/"+part1+"/";
            //"http://192.168.0.15/a/sach/bai_1/img/1.jpg";
            directory = directory_parrent+"/"+part2;
            File f_img = new File(saveDir + directory + type_file);
            if (!f_img.exists()) {
                f_img.mkdir();
            }
            saveFilePath1 = new File(saveDir + directory + type_file + fileName);
            saveFilePath = saveDir + directory +type_file+ fileName;
        }
        if (type=="hinhanh"){
            type_file="/hinhanh/";
            File f_img = new File(saveDir + directory_parrent + type_file);
            if (!f_img.exists()) {
                f_img.mkdir();
            }
            saveFilePath1 = new File(saveDir + directory_parrent + type_file + fileName);
            saveFilePath = saveDir + directory_parrent +type_file+ fileName;
        }
        if (type=="menu"){
            type_file="/";

            File f_img = new File(saveDir + directory_parrent + type_file);
            if (!f_img.exists()) {
                f_img.mkdir();
            }
            saveFilePath1 = new File(saveDir + directory_parrent + type_file + fileName);
            saveFilePath = saveDir + directory_parrent +type_file+ fileName;
        }
        if (type=="css"){
            type_file="/css/";
            File f_img = new File(saveDir + directory_parrent + type_file);
            if (!f_img.exists()) {
                f_img.mkdir();
            }
            saveFilePath1 = new File(saveDir + directory_parrent + type_file + fileName);
            saveFilePath = saveDir + directory_parrent +type_file+ fileName;
        }
        if (type=="js"){
            type_file="/js/";
            File f_img = new File(saveDir  + directory_parrent + type_file);
            if (!f_img.exists()) {
                f_img.mkdir();
            }
            saveFilePath1 = new File(saveDir   + directory_parrent +type_file + fileName);
            saveFilePath = saveDir   + directory_parrent + type_file+ fileName;
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
