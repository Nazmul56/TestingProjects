/*
 * Copyright 2014 Pierre Chabardes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.durbinlabs.floatingwindowexample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class FloatingWindow extends Service {

    WindowManager wm;
    LinearLayout ll;
    View view;

    private static final String VIDEO_CODEC_VP9 = "VP9";
    private static final String AUDIO_CODEC_OPUS = "opus";
    // Local preview screen position before call is connected.
    private static final int LOCAL_X_CONNECTING = 0;
    private static final int LOCAL_Y_CONNECTING = 0;
    private static final int LOCAL_WIDTH_CONNECTING = 100;
    private static final int LOCAL_HEIGHT_CONNECTING = 100;
    // Local preview screen position after call is connected.
    private static final int LOCAL_X_CONNECTED = 72;
    private static final int LOCAL_Y_CONNECTED = 62;
    private static final int LOCAL_WIDTH_CONNECTED = 25;
    private static final int LOCAL_HEIGHT_CONNECTED = 25;

    // Remote video screen position
    private static final int REMOTE_X = 0;
    private static final int REMOTE_Y = 0;
    private static final int REMOTE_WIDTH = 100;
    private static final int REMOTE_HEIGHT = 100;

    //private VideoRendererGui.ScalingType scalingType = VideoRendererGui.ScalingType.SCALE_ASPECT_FILL;
    private String mSocketAddress;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

      /*  ll = new LinearLayout(this);
        ll.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams layoutParameteres = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 400);
        ll.setBackgroundColor(Color.argb(255,255,255,255));
        ll.setLayoutParams(layoutParameteres);
*/
        final LayoutParams parameters = new LayoutParams(
                500, 500, LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        parameters.gravity = Gravity.TOP | Gravity.LEFT;
        parameters.x = 0;
        parameters.y = 0;

        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.window, null);


       Button b = (Button) view.findViewById(R.id.bt_stop);

        wm.addView(view, parameters);

        // TODO OnTouch Listener
        view.setOnTouchListener(new View.OnTouchListener() {
            LayoutParams updatedParameters = parameters;
            double x;
            double y;
            double pressedX;
            double pressedY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        x = updatedParameters.x;
                        y = updatedParameters.y;

                        pressedX = event.getRawX();
                        pressedY = event.getRawY();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        updatedParameters.x = (int) (x + (event.getRawX() - pressedX));
                        updatedParameters.y = (int) (y + (event.getRawY() - pressedY));

                        wm.updateViewLayout(view, updatedParameters);

                    default:
                        break;
                }

                return false;
            }
        });

       b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(view);
                stopSelf();
                //System.exit(0);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

}