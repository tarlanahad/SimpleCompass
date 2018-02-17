package com.tarlanahad.simplecompass.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.tarlanahad.simplecompass.R;

public class FontableTextView extends AppCompatTextView {
    public FontableTextView(Context context) {
        super(context);
    }

    public FontableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontableTextView, 0, 0);
        try {
            setFont(ta.getString(R.styleable.FontableTextView_typeface));
        } finally {
            ta.recycle();
        }
    }

    void setFont(String FontName) {
        try {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/" + FontName));
        } catch (Exception e) {
        }
    }

    public FontableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}