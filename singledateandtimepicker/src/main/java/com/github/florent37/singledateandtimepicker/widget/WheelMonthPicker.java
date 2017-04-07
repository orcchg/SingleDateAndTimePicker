package com.github.florent37.singledateandtimepicker.widget;

import android.content.Context;
import android.util.AttributeSet;

public class WheelMonthPicker extends WheelPicker {

    public WheelMonthPicker(Context context) {
        this(context, null);
    }

    public WheelMonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onItemSelected(int position, Object item) {

    }

    @Override
    protected void onItemCurrentScroll(int position, Object item) {

    }

    @Override
    protected String getFormattedValue(Object value) {
        return null;
    }

    @Override
    public int getDefaultItemPosition() {
        return 0;
    }
}
