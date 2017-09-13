package com.example.durbinlabs.morsecode;

import android.content.res.Configuration;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import bd.mil.army.crs.utils.Lg;

/**
 * Created by touhid on 9/10/2017.
 * bismillah :)
 */

public class MorseActivity extends AppCompatActivity {

    private static final String TAG = MorseActivity.class.getSimpleName();
    private MediaRecorder mRecorder = null;
    private TextView tvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);

        tvMsg = findViewById(R.id.tvMsg);
        tvMsg.setText("");
        findViewById(R.id.ivMic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecorder == null)
                    start();
                else
                    stop();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // super.onConfigurationChanged(newConfig);
    }

    public void start() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            try {
                mRecorder.prepare();
                mRecorder.start();
                Toast.makeText(this, "Recording audio ...", Toast.LENGTH_SHORT).show();
                runAmpGetter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int seqCounter = 0;
    private void runAmpGetter() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                double amp = getAmplitude();
                Lg.i(TAG, "Amp. at sequence " + seqCounter+" : " + amp);
                // if(amp)
            }
        }, 1000); // FOR 20wpm, we get 3sec/word & so there's a chance of changing the amp. within every second
    }

    public void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            Toast.makeText(this, "Recorder stopped", Toast.LENGTH_SHORT).show();
        }
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return mRecorder.getMaxAmplitude();
        else
            return 0;

    }

}
