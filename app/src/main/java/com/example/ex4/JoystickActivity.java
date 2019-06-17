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
    private int isFirst;
    private int originalX;
    private int originalY;
    private int radius;

    private int outerTop;
    private int outerBot;


    private boolean isInCircle(int x, int y)
    {
        //int diffX = originalX - x + 40;
        //int diffY = originalY - y + 280;
        int diffX = originalX - x;
        int diffY = originalY - y;
        double d = Math.sqrt( Math.pow(diffX, 2) + Math.pow(diffY, 2));
        return d <= radius;

    }

    private boolean isInCircle2(int x, int y)
    {
        Log.d("outerBot" ,String.valueOf(outerBot));
        Log.d("outerTop" ,String.valueOf(outerTop));
        Log.d("x" ,String.valueOf(x));
        Log.d("y" ,String.valueOf(y));
        return (y < outerBot) && (y > outerTop);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if(hasFocus) {
            isFirst = 1;
            _root = (ViewGroup)findViewById(R.id.relativeRoot);
            _knob = (ImageView) findViewById(R.id.knob);
            ImageView outer = (ImageView) findViewById(R.id.outerCircle);
            radius = (outer.getBottom() - outer.getTop()) / 2;
            outerBot = outer.getBottom();
            outerTop = outer.getTop();



            float factor = getResources().getDisplayMetrics().density;
            int w  = (int)factor * 98;
            int h = (int) factor * 114;

            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(98, 114);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
            //RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) _knob.getLayoutParams();
            layoutParams.topMargin = (int)_knob.getY() +20;
            layoutParams.leftMargin = (int)_knob.getX() +20;
            //layoutParams.topMargin = (int)_knob.getY();
            //layoutParams.leftMargin = (int)_knob.getX();

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
        //Log.d("X" ,String.valueOf(X));
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                //_xDelta = 0;
                //_yDelta = 0;

                break;
            case MotionEvent.ACTION_UP:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.topMargin = originalY;
                layoutParams.leftMargin = originalX;
                view.setLayoutParams(layoutParams);
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
                //if (isInCircle(X,Y))
                if (isInCircle(layoutParams1.leftMargin, layoutParams1.topMargin))
                {
                    view.setLayoutParams(layoutParams1);
                    //Log.d("ACTION_MOVE" ,String.valueOf(_xDelta));
                }

                break;
        }
        _root.invalidate();
        return true;
    }
}
