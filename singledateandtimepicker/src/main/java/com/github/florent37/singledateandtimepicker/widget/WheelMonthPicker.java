package com.github.florent37.singledateandtimepicker.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.github.florent37.singledateandtimepicker.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class WheelMonthPicker extends WheelPicker {
    private int defaultMonth;  // index of month and position in adapter

    private WheelPicker.Adapter adapter;

    private int lastScrollPosition;

    private OnMonthSelectedListener onMonthSelectedListener;

    public WheelMonthPicker(Context context) {
        this(context, null);
    }

    public WheelMonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAdapter();
    }

    private void initAdapter() {
        List<String> months = Arrays.asList(getResources().getStringArray(R.array.months));
        adapter = new Adapter(months);
        setAdapter(adapter);

        defaultMonth = Calendar.getInstance().get(Calendar.MONTH);

        updateDefaultMonth();
    }

    public void setOnMonthSelectedListener(OnMonthSelectedListener onMonthSelectedListener) {
        this.onMonthSelectedListener = onMonthSelectedListener;
    }

    @Override
    protected void onItemSelected(int position, Object item) {
        if (onMonthSelectedListener != null) {
            onMonthSelectedListener.onMonthSelected(this, position, position /* position is index of month */);
        }
    }

    @Override
    protected void onItemCurrentScroll(int position, Object item) {
        if (lastScrollPosition != position) {
            onMonthSelectedListener.onMonthCurrentScrolled(this, position, position /* position is index of month */);
            if (lastScrollPosition == 11 && position == 0)
                if (onMonthSelectedListener != null) {
                    onMonthSelectedListener.onMonthScrolledNewHour(this);
                }
            lastScrollPosition = position;
        }
    }

    @Override
    protected String getFormattedValue(Object value) {
        return String.format(getCurrentLocale(), "%s", value);
    }

    @Override
    public int getDefaultItemPosition() {
        return defaultMonth;
    }

    public int getCurrentMonth() {
        return getCurrentItemPosition();  // position in adapter is index of month
    }

    private void updateDefaultMonth() {
        setSelectedItemPosition(defaultMonth);
    }

    public void setDefaultMonth(int month) {
        this.defaultMonth = month;
        updateDefaultMonth();
    }

    public interface OnMonthSelectedListener {
        void onMonthSelected(WheelMonthPicker picker, int position, int month);

        void onMonthCurrentScrolled(WheelMonthPicker picker, int position, int month);

        void onMonthScrolledNewHour(WheelMonthPicker picker);
    }
}
