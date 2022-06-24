package com.rejointech.jobber.Utils;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Typewriter extends androidx.appcompat.widget.AppCompatTextView {
    private CharSequence mytext;
    private int myIndex;
    private int delay=150;
    public Typewriter(Context context) {
        super(context);
    }

    public Typewriter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    private Handler handler=new Handler();
    private Runnable charAdder=new Runnable() {
        @Override
        public void run() {
            setText(mytext.subSequence(0,myIndex++));
            if(myIndex<mytext.length()){
                handler.postDelayed(charAdder,delay);
            }
        }
    };
    public void animateText(CharSequence mytxt){
        mytext=mytxt;
        myIndex=0;
        setText("");
        handler.removeCallbacks(charAdder);
        handler.postDelayed(charAdder,delay);
    }
    public void setCharDelay(int m){
        delay=m;
    }
}
