package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.os.Bundle;
import android.view.Display;
import android.view.Display;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.content.Context;

public class JoystickActivity extends AppCompatActivity implements View.OnTouchListener {


    ImageView _knob;
    ViewGroup _root;
    private int _xDelta;
    private int _yDelta;
    private int originalX;
    private int originalY;
    private int radius;


    // send the values to the simulator
    private void sendToSimulator(double x, double y)
    {
        String xCommand = "set /controls/flight/aileron " + x + "\n";
        String yCommand = "set /controls/flight/elevator " + y + "\n";

        SendingTask s = MainActivity.sendingTask;
        s.addToQueue(xCommand);
        s.addToQueue(yCommand);
    }

    // The function checks if a point (x,y) is inside the circle
    private boolean isInCircle(int x, int y)
    {
        int diffX = originalX - x;
        int diffY = originalY - y;
        double d = Math.sqrt( Math.pow(diffX, 2) + Math.pow(diffY, 2));
        return d <= radius;
    }

    // Gets the Y value for the simulator
    private double getClientYValue(int currentY)
    {
        double normalY = Math.abs(originalY - currentY) / (double)radius;
        if (currentY > originalY)
            normalY = normalY *(-1);
        return normalY;
    }

    // Gets the X value for the simulator
    private double getClientXValue(int currentX)
    {
        double normalX = Math.abs(originalX - currentX) / (double)radius;
        if (currentX < originalX)
            normalX = normalX *(-1);
        return normalX;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if(hasFocus) {
            _root = (ViewGroup)findViewById(R.id.relativeRoot);
            _knob = (ImageView) findViewById(R.id.knob);
            ImageView outer = (ImageView) findViewById(R.id.outerCircle);
            radius = (outer.getBottom() - outer.getTop()) / 2;



            // Convert from pixels to dp
            float factor = getResources().getDisplayMetrics().density;
            int w  = (int)factor * 98;
            int h = (int) factor * 114;

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
            layoutParams.topMargin = (int)_knob.getY() +20;
            layoutParams.leftMargin = (int)_knob.getX() +20;

            // Save the original values for returning to center
            originalX = (int)_knob.getX() +20;
            originalY = (int)_knob.getY() +20;

            _knob.setLayoutParams(layoutParams);
            _knob.setOnTouchListener(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

    }

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;

            case MotionEvent.ACTION_UP:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.topMargin = originalY;
                layoutParams.leftMargin = originalX;
                view.setLayoutParams(layoutParams);
                sendToSimulator(0, 0);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                break;

            case MotionEvent.ACTION_POINTER_UP:
                break;

            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams1.leftMargin = X - _xDelta;
                layoutParams1.topMargin = Y - _yDelta;
                layoutParams1.rightMargin = -250;
                layoutParams1.bottomMargin = -250;

                // Get values for the simulator
                double simulatorX = getClientXValue(layoutParams1.leftMargin);
                double simulatorY = getClientYValue(layoutParams1.topMargin);

                if (isInCircle(layoutParams1.leftMargin, layoutParams1.topMargin) && Math.abs(simulatorX) <= 1 && Math.abs(simulatorY) <= 1)
                {
                    view.setLayoutParams(layoutParams1);
                    sendToSimulator(simulatorX, simulatorY);
                }



                break;
        }
        _root.invalidate();
        return true;
    }
}
