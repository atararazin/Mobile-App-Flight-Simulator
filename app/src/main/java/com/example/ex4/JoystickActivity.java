package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
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

public class JoystickActivity extends AppCompatActivity implements View.OnTouchListener {


    ImageView _knob;
    ViewGroup _root;
    private int _xDelta;
    private int _yDelta;
    private int isFirst;
    private int originalX;
    private int originalY;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if(hasFocus) {
            isFirst = 1;
            _root = (ViewGroup)findViewById(R.id.relativeRoot);
            _knob = (ImageView) findViewById(R.id.knob);

            //////////////////////////////////////////////////////////////////////////////
            Rect offsetViewBounds = new Rect();
            //returns the visible bounds
            _knob.getDrawingRect(offsetViewBounds);
            // calculates the relative coordinates to the parent
            _root.offsetDescendantRectToMyCoords(_knob, offsetViewBounds);

            int relativeTop = offsetViewBounds.top;
            int relativeLeft = offsetViewBounds.left;

            //////////////////////////////////////////////////////////////////////////////


            int[] pos = new int[2];
            _knob.getLocationOnScreen(pos);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
            layoutParams.topMargin = (int)_knob.getY() +50;
            layoutParams.leftMargin = (int)_knob.getX() +50;

            originalX = (int)_knob.getX() +50;
            originalY = (int)_knob.getY() +50;

            _knob.setLayoutParams(layoutParams);
            _knob.setOnTouchListener(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        _root = (ViewGroup)findViewById(R.id.relativeRoot);

        _knob = (ImageView) findViewById(R.id.knob);


        //////////////////////////////////////////////////////////////////////////////
        Rect offsetViewBounds = new Rect();
        //returns the visible bounds
        _knob.getDrawingRect(offsetViewBounds);
        // calculates the relative coordinates to the parent
        _root.offsetDescendantRectToMyCoords(_knob, offsetViewBounds);

        int relativeTop = offsetViewBounds.top;
        int relativeLeft = offsetViewBounds.left;
        Log.d("a" ,String.valueOf(_knob.getTop()));
        Log.d("a" ,String.valueOf(_knob.getLeft()));

        //////////////////////////////////////////////////////////////////////////////

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(98, 114);
        //layoutParams.leftMargin = 50;
        //layoutParams.topMargin = 50;
        //layoutParams.bottomMargin = -250;
        //layoutParams.rightMargin = -250;

        //layoutParams.topMargin = relativeTop;
        //layoutParams.leftMargin = relativeLeft;


        //layoutParams.topMargin = _knob.getTop();
        //layoutParams.leftMargin = _knob.getLeft();
        //_knob.setLayoutParams(layoutParams);

        //_knob.setOnTouchListener(this);
    }

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        Log.d("X" ,String.valueOf(X));
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
                //layoutParams.rightMargin = 1000;
                //layoutParams.bottomMargin = 1000;
                view.setLayoutParams(layoutParams1);
                Log.d("ACTION_MOVE" ,String.valueOf(_xDelta));
                break;
        }
        _root.invalidate();
        return true;
    }
}
