package com.devdootandfriends.rtcomu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import org.apache.http.client.*;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.mime.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.*;
import android.provider.*;
import android.content.pm.*;
import  android.net.*;
import android.graphics.*;
import org.apache.http.entity.mime.content.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.util.Log;
import com.theartofdev.edmodo.cropper.*;

public class MainActivity extends AppCompatActivity {
    public Button mButton;
    private Button mButton2;

    private int id;

private class ans extends AsyncTask<Void,Void,String>{
  private ProgressDialog mProgressDialog;
    @Override
    protected String doInBackground(Void... params) {
        Double ans=null;
        String ss=null;
        try{

            HttpClient httpclient = new DefaultHttpClient();


            HttpPost httppost = new HttpPost("https://indorp.herokuapp.com/upload");
            MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (State.g !=null) {
                File file = new File(State.g.getPath());
                Log.d("EDIT USER PROFILE", "UPLOAD: file length = " + file.length());
                Log.d("EDIT USER PROFILE", "UPLOAD: file exist = " + file.exists());
                mpEntity.addPart("image", new FileBody(file));
            }
            httppost.setEntity(mpEntity);
            HttpResponse response = httpclient.execute(httppost);
            String result = EntityUtils.toString(response.getEntity());
            return result;

        }
        catch(IOException e){ return "NULLLLLLLLL";}

    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setTitle("Analyzing Image");
        mProgressDialog.setMessage("Analyzing...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
       mButton.setText(s);
        mProgressDialog.dismiss();
    }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
if(resultCode!=RESULT_OK){
    onPostResume();
}
      else  if(requestCode==2){

            if(State.g!=null){
//                 State.uri =data.getData();
                // mButton.setText(g.getPath());
                //       boolean tr=true;
                //setContentView(R.layout.activity_crop);
                //CropImageView cropImageView=(CropImageView)findViewById(R.id.cropImageView);
               // Bitmap bitmap = BitmapFactory.decodeFile(g.getPath());
               // cropImageView.setImageBitmap(bitmap);
                //Bitmap cropped = cropImageView.getCroppedImage();
               // Context mcontext=getApplicationContext();
               // File f=mcontext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
               // g=new File(f,"image.bmp");
                if(State.g!=null){
                    // Uri uri =data.getData();
                    // mButton.setText(g.getPath());
                    //       boolean tr=true;
                    Intent intent=new Intent(MainActivity.this,CropActivity.class);
                    startActivity(intent);
                   /* if(!isNetworkConnected()){
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("No Internet Connecion")
                                .setMessage("Please Check your internet connection.").setCancelable(false)
                                .setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mButton.setBackgroundColor(Color.parseColor("#18d6f0"));
                                mButton.setEnabled(true);
                                mButton2.setBackgroundColor(Color.parseColor("#18d6f0"));
                                mButton2.setEnabled(true);
                                dialog.cancel();
                            }
                        })
                                .show();
//tr=getmtr()
                    }
                    else {

                        new ans().execute();
                    }
                } else{//mButton.setText("NULLLLLLLLLLL");

                }
            }*/}}
      }
        else if (requestCode==1){


           State.uri= data.getData();
            State.g=new File(State.uri.getPath());

            if(State.g!=null){
                // Uri uri =data.getData();
                // mButton.setText(g.getPath());
                //       boolean tr=true;
                Intent intent=new Intent(MainActivity.this,CropActivity.class);
                startActivity(intent);}

        }
    }
    public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id=0;
        mButton=(Button)findViewById(R.id.button);
        mButton.setBackgroundColor(Color.parseColor("#18d6f0"));
        mButton.setEnabled(true);
        mButton2=(Button)findViewById(R.id.button2);

        mButton2.setBackgroundColor(Color.parseColor("#18d6f0"));
        mButton2.setEnabled(true);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton.setBackgroundColor(Color.parseColor("#696969"));
                mButton.setEnabled(false);
                mButton2.setEnabled(false);
                Context mcontext=getApplicationContext();
                File f=mcontext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                PackageManager packageManager = MainActivity.this.getPackageManager();
                if(f!=null&&captureImage.resolveActivity(packageManager) != null){
                    State.g=new File(f,"image_cam.jpg");
                   State.uri = Uri.fromFile(State.g);
                    captureImage.putExtra(MediaStore.EXTRA_OUTPUT, State.uri);
                    startActivityForResult(captureImage, 2);

                    //mButton.setEnabled(true);
                }
                else {
                   // mButton.setText("Null");
                }
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton2.setBackgroundColor(Color.parseColor("#696969"));
                mButton2.setEnabled(false);
                mButton.setEnabled(false);
                Context mcontext=getApplicationContext();
                File f=mcontext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*"); PackageManager packageManager = MainActivity.this.getPackageManager();
                if(f!=null&&photoPickerIntent.resolveActivity(packageManager) != null){
                    State.g=new File(f,"image.jpg");
                     State.uri = Uri.fromFile(State.g);
                   // photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, State.uri);
                    startActivityForResult(photoPickerIntent, 1);

                   // mButton.setEnabled(true);
                }
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mButton.setBackgroundColor(Color.parseColor("#18d6f0"));
        mButton.setEnabled(true);
        mButton2.setBackgroundColor(Color.parseColor("#18d6f0"));
        mButton2.setEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
