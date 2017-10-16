package com.example.durbinlabs.mosecode;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/*import bd.mil.army.crs.utils.D;
import bd.mil.army.crs.utils.PermissionManager;
import bd.mil.army.crs.utils.U;*/

/**
 * Created by touhid on 9/10/2017.
 * bismillah :)
 */

public class MorseActivity extends AppCompatActivity {

    private static final String TAG = MorseActivity.class.getSimpleName();
    private MediaRecorder mRecorder = null;
    private TextView tvMsg;
    private int seqCounter = -1999;  // Every 5ms amp-getter will run for 10 seconds - hence 2000 times
    private int[] noiseLevels = new int[2000];
    private double AVG_NOISE_LEVEL = 0.0;

    private View flRecording;
   // private AudioVisualizer audioVisualizer;

    private EditText etMorse, etMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);

      //  audioVisualizer = findViewById(R.id.audioVisualizer);
        flRecording = findViewById(R.id.flRecordingView);
        flRecording.setVisibility(View.GONE);
        flRecording.findViewById(R.id.tvStopRecording).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
        tvMsg = (TextView) findViewById(R.id.tvMsgRecord);
        findViewById(R.id.ivMic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecorder == null)
                    start();
                else
                    stop();
            }
        });

        etMorse = (EditText) findViewById(R.id.etMorse);
        etMsg = (EditText) findViewById(R.id.etMessage);

        findViewById(R.id.tvEncode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                encodeMsg();
            }
        });
        findViewById(R.id.tvDecode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decodeMsg();
            }
        });

        findViewById(R.id.fabShareMorse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = MorseActivity.this;
                String morse = etMorse.getText().toString();
                String msg = etMsg.getText().toString();
                if (msg.length() < 1)
                    Toast.makeText(context, "Message not complete to send", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Message " + morse, Toast.LENGTH_SHORT).show();
                  /* Nazmul U.sendEmail(context, new String[]{},
                            "[" + context.getString(R.string.communication_registry_system) + "] Morse Code Sharing",
                            "Received Morse-Code:\n" + morse + "\n\nDecoded Morse-Message:\n" + msg);
*/            }
        });

        findViewById(R.id.tvDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMorse.append(".");
            }
        });

        findViewById(R.id.tvDash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMorse.append("-");
            }
        });

        findViewById(R.id.tvSpace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMorse.append(" ");
            }
        });
    }

    private void decodeMsg() {
        String msg = etMorse.getText().toString();
        if (msg.length() < 1) {
            String text = "No morse-code entered to decode";
            etMorse.setError(text);
            // Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } else
            etMsg.setText(MorseCodeHelper.morseToAlpha(msg));
    }

    private void encodeMsg() {
        String msg = etMsg.getText().toString();
        if (msg.length() < 1) {
            String text = "No message entered to encode";
            etMsg.setError(text);
            // Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } else
            etMorse.setText(MorseCodeHelper.alphaToMorse(msg));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // super.onConfigurationChanged(newConfig);
    }

    public void start() {
       /* Nazmul if (!PermissionManager.requestSinglePermission(this, Manifest.permission.RECORD_AUDIO,
                101, "Please grant the audio record permission first."))
            return;*/
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            try {
                mRecorder.prepare();
                mRecorder.start();
                flRecording.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Recording audio ...", Toast.LENGTH_SHORT).show();
                // TODO Have a calibrating period(10s?) first, then average the amp. level (5ms interval) as silence level
                // then calc. the continuity counter for every 5ms::
                // 2 continuity for silence level (10ms) -> silence -> print " " (space)
                // 2 continuity for pick level (10ms) -> doh -> print "." (dot)
                // 6 continuity for pick level (30ms) -> dah -> print "-" (dash)
                seqCounter = -1999;
                AVG_NOISE_LEVEL = 0;
                runAmpGetter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void runAmpGetter() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int amp = getAmplitude();
                Log.d(TAG, "Amp. at sequence " + seqCounter + " : " + amp);
                if (amp > AVG_NOISE_LEVEL) {
                    seqCounter++;
                    if (seqCounter < 1)
                        noiseLevels[-1 * seqCounter] = amp;
                } else if (seqCounter > 0) {
                    if(seqCounter<2)
                        etMorse.append(".");
                    else if(seqCounter<3)
                        etMorse.append("-");
                    else if(seqCounter>2)
                        etMorse.append(" ");
                    seqCounter = 0;
                }
                if (seqCounter == 0) {
                    barNoiseLevel();
                    tvMsg.setText("Noise level calibrated.\nPlease play the morse code audio now ..." + AVG_NOISE_LEVEL);
                }
                // if (amp % 25 == 0) // updating the visualizer in every 25ms
                  //  audioVisualizer.addAmplitude(amp);
                if (mRecorder != null)
                    runAmpGetter();
            }
        }, 5); // T=1200/W, where W = 20wpm => T= 60 ... but min interval is 10ms - so calc. in every 5ms
    }

    @Override
    protected void onResume() {
        stop();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        stop();
        super.onDestroy();
    }

    private void barNoiseLevel() {
        double total = 0.0;
        for (double d : noiseLevels)
            total += d;
        AVG_NOISE_LEVEL = total / noiseLevels.length;
    }
    /*if (amp > 900) // max -> DOT
                    tvMsg.append(".");
                else if (amp > 600)// medium -> DASH
                    tvMsg.append("-");
                else if (amp < 601) // silence -> break
                    tvMsg.append(" ");*/

    public void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            flRecording.setVisibility(View.GONE);
            Log.d(TAG, "Recorder stopped\\nShowing result ..." );
       // Log.d.("Recorder stopped\nShowing result ...");
            //etMsg.setText(MorseCodeHelper.morseToAlpha(etMorse.getText().toString()));
          //  etMsg.setText(etMorse.getText().toString());
        }
    }

    public int getAmplitude() {
        if (mRecorder != null)
            return mRecorder.getMaxAmplitude();
        else
            return 0;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    start();
                break;

            default:
                break;
        }
    }
}
