package com.github.orcchg.monthyearpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.florent37.singledateandtimepicker.R;
import com.github.florent37.singledateandtimepicker.widget.WheelMonthPicker;
import com.github.florent37.singledateandtimepicker.widget.WheelYearPicker;

public class MonthYearPicker extends LinearLayout {

    private WheelMonthPicker monthsPicker;
    private WheelYearPicker yearsPicker;

    public MonthYearPicker(Context context) {
        this(context, null);
    }

    public MonthYearPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthYearPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        inflate(context, R.layout.month_year_picker, this);

        monthsPicker = (WheelMonthPicker) findViewById(R.id.monthsPicker);
        yearsPicker = (WheelYearPicker) findViewById(R.id.yearsPicker);
    }

    private void init(Context context, AttributeSet attrs) {
        // TODO
    }
}
