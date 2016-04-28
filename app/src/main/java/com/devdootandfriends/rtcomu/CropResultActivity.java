// "Therefore those skilled at the unorthodox
// are infinite as heaven and earth,
// inexhaustible as the great rivers.
// When they come to an end,
// they begin again,
// like the days and months;
// they die and are reborn,
// like the four seasons."
//
// - Sun Tsu,
// "The Art of War"

package com.devdootandfriends.rtcomu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.BitmapCroppingWorkerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class CropResultActivity extends Activity {

    /**
     * The image to show in the activity.
     */
    static Bitmap mImage;
    private Button mButton;
private ans mAns;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_crop_result);
mButton=(Button)findViewById(R.id.analyze);
        mButton.setBackgroundColor(Color.parseColor("#18d6f0"));
        mButton.setEnabled(true);
        mImage= BitmapCroppingWorkerTask.mbitmap;
        if (mImage != null) {

            //Toast.makeText(this, "Showing image", Toast.LENGTH_LONG).show();
            Context mcontext=CropResultActivity.this;
            File f=mcontext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            State.g=new File(f,"image.jpg");
            State.uri= Uri.fromFile(State.g);
            OutputStream outStream = null;

            try {
                outStream = new FileOutputStream(State.g);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mImage.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            try {
                outStream.flush();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((ImageView) findViewById(R.id.resultImageView)).setImageBitmap(mImage);

mButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mButton.setBackgroundColor(Color.parseColor("#696969"));
        mButton.setEnabled(false);
        if(State.g!=null){
            // Uri uri =data.getData();
            // mButton.setText(g.getPath());
            //       boolean tr=true;
            if(!isNetworkConnected()){
                new AlertDialog.Builder(CropResultActivity.this)
                        .setTitle("No Internet Connecion")
                        .setMessage("Please Check your internet connection.").setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  mButton.setBackgroundColor(Color.parseColor("#18d6f0"));
                        //  mButton.setEnabled(true);
                        // mButton2.setBackgroundColor(Color.parseColor("#18d6f0"));
                        //mButton2.setEnabled(true);

                        dialog.cancel();
                        onPostResume();
                    }
                }).setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        //@Override

                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                               // mAns.cancel(true);
                               dialog.dismiss();
                                onPostResume();
                                return true;
                            }
                        return false;
                           // return super.onKey(dialog,keyCode, event);

                    }
                })
                        .show();
//tr=getmtr()
            }
            else {

                mAns=new ans();mAns.execute();
            }
        } else{//mButton.setText("NULLLLLLLLLLL");

        }
    }
});
            double ratio = ((int) (10 * mImage.getWidth() / (double) mImage.getHeight())) / 10d;
            int byteCount = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1) {
                byteCount = mImage.getByteCount() / 1024;
            }
            String desc = "(" + mImage.getWidth() + ", " + mImage.getHeight() + "), Ratio: " + ratio + ", Bytes: " + byteCount + "KB";
            //((TextView) findViewById(R.id.resultImageText)).setText(desc);

        } else {
            Toast.makeText(this, "No image is set to show", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mButton.setBackgroundColor(Color.parseColor("#18d6f0"));
        mButton.setEnabled(true);
    }

    private class ans extends AsyncTask<Void,Void,Double> {
        private ProgressDialog mProgressDialog;
        @Override
        protected Double doInBackground(Void... params) {
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

                try {
                    JSONObject res = new JSONObject(result); //Convert String to JSON Object

                    Double token = res.getDouble("result");
                    return  token;

                } catch (JSONException e) {
                    System.out.println(e);
                    return null;
                    //e.printStackTrace();
                }



            }
            catch(IOException e){ return null;}

        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(CropResultActivity.this){
                @Override
                public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mAns.cancel(true);
                    this.dismiss();
                    onPostResume();
                    return true;
                }
                return super.onKeyDown(keyCode, event);
            }};
            mProgressDialog.setTitle("Analyzing Image");
            mProgressDialog.setMessage("Analyzing...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Double s) {
            // mButton.setText(s);
            mProgressDialog.dismiss();
if(s==null)
            State.d=null;
            else State.d=Double.valueOf(s.doubleValue()*100);

            if(State.d.doubleValue()<0)State.d=Double.valueOf(0);
            else  if(State.d.doubleValue()>100)State.d=Double.valueOf(100);
            State.s=String.format("%.2f %%",State.d);
            Intent intent=new Intent(CropResultActivity.this,ResultActivity.class);
            startActivity(intent);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }



    public void onImageViewClicked(View view) {
        //releaseBitmap();
      //  finish();
    }

    private void releaseBitmap() {
        if (mImage != null) {
            mImage.recycle();
            mImage = null;
        }
    }

}
