package com.devdootandfriends.rtcomu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.BitmapCroppingWorkerTask;

public class ResultActivity extends AppCompatActivity {
private TextView mTextView2;
    private TextView mTextView;
    private Button mButton;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    mTextView2=(TextView)findViewById(R.id.textView2);
        mImageView=(ImageView)findViewById(R.id.imageView);
        mTextView2.setText(State.s);
        mTextView=(TextView)findViewById(R.id.textView);
        mButton=(Button)findViewById(R.id.button3);
        if(State.d==null){
            mButton.setBackgroundColor(Color.parseColor("#18d6f0"));
            mTextView.setText("Internal Server Error");
            mTextView2.setText("");
            mButton.setEnabled(true);
            mButton.setVisibility(View.VISIBLE);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButton.setEnabled(false);
                    mButton.setBackgroundColor(Color.parseColor("#696969"));
                    finish();
                }
            });
        }

       else if(State.d.doubleValue()>50){
            mImageView.setImageBitmap( BitmapCroppingWorkerTask.mbitmap);
            mTextView.setTextColor(Color.parseColor("#ff0000"));
            mTextView2.setTextColor(Color.parseColor("#ff0000"));
        }
        else{
            mImageView.setImageBitmap( BitmapCroppingWorkerTask.mbitmap);
            mTextView.setTextColor(Color.parseColor("#006400"));
            mTextView2.setTextColor(Color.parseColor("#006400"));
        }
    }

@Override
    public void onBackPressed() {
        //super.onBackPressed();
        //Intent intent =new Intent(ResultActivity.this,MainActivity.class);
        //startActivity(intent);
    NavUtils.navigateUpFromSameTask(this);
    }
}
