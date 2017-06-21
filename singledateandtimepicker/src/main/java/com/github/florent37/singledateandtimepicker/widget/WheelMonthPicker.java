package com.github.florent37.singledateandtimepicker.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.github.florent37.singledateandtimepicker.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class WheelMonthPicker extends WheelPicker {
    private int defaultMonth;  // index of month and position in adapter

    private List<String> months;
    private WheelPicker.Adapter adapter;

    private int lastScrollPosition;

    private OnMonthSelectedListener onMonthSelectedListener;

    public WheelMonthPicker(Context context) {
        this(context, null);
    }

    public WheelMonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAdapter(-1);
    }

    private void initAdapter(int lastMonth) {
        months = Arrays.asList(getResources().getStringArray(R.array.months));
        boolean useLastMonth = lastMonth >= Calendar.JANUARY && lastMonth < Calendar.DECEMBER;
        if (useLastMonth) {
            months = months.subList(0, lastMonth + 1);
        }
        adapter = new Adapter(months);
        setAdapter(adapter);

        defaultMonth = Calendar.getInstance().get(Calendar.MONTH);
        if (useLastMonth && defaultMonth > lastMonth) {
            defaultMonth = lastMonth;
        }

        updateDefaultMonth();
    }

    public void setLastMonth(int lastMonth) {
        initAdapter(lastMonth);
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
            if (lastScrollPosition == Calendar.DECEMBER && position == 0)
                if (onMonthSelectedListener != null) {
                    onMonthSelectedListener.onMonthScrolledNewMonth(this);
                }
            lastScrollPosition = position;
        }
    }

    @Override
    protected String getFormattedValue(Object value) {
        return String.format(getCurrentLocale(), "%s", value);
    }

    public String getItemStringByPosition(int position) {
        return months.get(position);
    }

    @Override
    public int getDefaultItemPosition() {
        return defaultMonth;
    }

    public int getCurrentMonth() {
        return getCurrentItemPosition();  // position in adapter is index of month
    }

    public void setCurrentMonth(int month) {
        if (month < Calendar.JANUARY || month > Calendar.DECEMBER) {
            throw new IllegalArgumentException("Invalid month value: " + month);
        }
        setSelectedItemPosition(month);
    }

    public int findIndexOfMonth(int month) {
        return month;  // position in adapter is index of month
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

        void onMonthScrolledNewMonth(WheelMonthPicker picker);
    }
}
