package com.example.strokegenius;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import watch.nudge.gesturelibrary.AbstractGestureClientActivity;
import watch.nudge.gesturelibrary.GestureConstants;

public class MainWatchActivity  extends Activity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;

    @Override
    public ArrayList<GestureConstants.SubscriptionGesture> getGestureSubscpitionList() {
        ArrayList<GestureConstants.SubscriptionGesture> gestures = new ArrayList<GestureConstants.SubscriptionGesture>();
        gestures.add(GestureConstants.SubscriptionGesture.FLICK);
        gestures.add(GestureConstants.SubscriptionGesture.SNAP);
        gestures.add(GestureConstants.SubscriptionGesture.TWIST);
        return gestures;
    }

    @Override
    public boolean sendsGestureToPhone() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_watch);
        setAmbientEnabled();
        setSubscribeWindowEvents(true);
        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mClockView = (TextView) findViewById(R.id.clock);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSnap() {
        Toast.makeText(this,"Snap it up",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFlick() {
        Toast.makeText(this, "Got a flick!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTwist() {
        Toast.makeText(this,"Just twist it",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGestureWindowClosed() {
        Toast.makeText(this,"Gesture window closed.",Toast.LENGTH_LONG).show();
    }

//These functions won't be called until you subscribe to the appropriate gestures.

    @Override
    public void onTiltX() {
        throw new IllegalStateException("This function should not be called unless subscribed to TILT_X.");
    }

    @Override
    public void onTilt() {
        throw new IllegalStateException("This function should not be called unless subscribed to TILT.");
    }

}
