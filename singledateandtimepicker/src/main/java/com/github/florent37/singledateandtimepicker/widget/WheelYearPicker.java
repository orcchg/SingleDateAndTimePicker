package com.github.florent37.singledateandtimepicker.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.github.florent37.singledateandtimepicker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WheelYearPicker extends WheelPicker {
    public static final int MIN_YEAR = 1900;
    public static final int MAX_YEAR = 2100;
    public static final int STEP_YEAR_DEFAULT = 1;

    private int defaultYear;
    private int minYear = MIN_YEAR, maxYear = MAX_YEAR;
    private int stepYears = STEP_YEAR_DEFAULT;

    private List<String> years;
    private WheelPicker.Adapter adapter;

    private int lastScrollPosition;

    private OnYearSelectedListener onYearSelectedListener;

    public WheelYearPicker(Context context) {
        this(context, null);
    }

    public WheelYearPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WheelPicker);
        minYear = ta.getInt(R.styleable.WheelPicker_wheel_min_year, MIN_YEAR);
        maxYear = ta.getInt(R.styleable.WheelPicker_wheel_max_year, MAX_YEAR);
        ta.recycle();

        initAdapter();
    }

    private void initAdapter() {
        years = new ArrayList<>();
        for (int min = minYear; min <= maxYear; min += stepYears) {
            years.add(getFormattedValue(min));
        }
        adapter = new Adapter(years);
        setAdapter(adapter);

        defaultYear = Calendar.getInstance().get(Calendar.YEAR);

        updateDefaultYear();
    }

    public int getMinYear() {
        return minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMinMaxYears(int min, int max) {
        minYear = min;
        maxYear = max;
        initAdapter();
    }

    public void setOnYearSelectedListener(OnYearSelectedListener onYearSelectedListener) {
        this.onYearSelectedListener = onYearSelectedListener;
    }

    @Override
    protected void onItemSelected(int position, Object item) {
        if (onYearSelectedListener != null) {
            onYearSelectedListener.onYearSelected(this, position, convertItemToYear(item));
        }
    }

    @Override
    protected void onItemCurrentScroll(int position, Object item) {
        if (lastScrollPosition != position) {
            onYearSelectedListener.onYearCurrentScrolled(this, position, convertItemToYear(item));
            if (lastScrollPosition == maxYear && position == 0)
                if (onYearSelectedListener != null) {
                    onYearSelectedListener.onYearScrolledNewYear(this);
                }
            lastScrollPosition = position;
        }
    }

    @Override
    protected String getFormattedValue(Object value) {
        return String.format(getCurrentLocale(), "%s", value);
    }

    public String getItemStringByPosition(int position) {
        return years.get(position);
    }

    @Override
    public int getDefaultItemPosition() {
        return findIndexOfYear(defaultYear);
    }

    public int getCurrentYear() {
        return convertItemToYear(adapter.getItem(getCurrentItemPosition()));
    }

    private int convertItemToYear(Object item) {
        return Integer.valueOf(String.valueOf(item));
    }

    public int findIndexOfYear(int year) {
        final int itemCount = adapter.getItemCount();
        for (int i = 0; i < itemCount; ++i) {
            final String object = adapter.getItemText(i);
            final Integer value = Integer.valueOf(object);
            if (year < value) {
                return i - 1;
            }
        }
        return 0;
    }

    private void updateDefaultYear() {
        setSelectedItemPosition(findIndexOfYear(defaultYear));
    }

    public void setDefaultYear(int year) {
        this.defaultYear = year;
        updateDefaultYear();
    }

    public void setStepYears(int stepYears) {
        if (stepYears > 0) {
            this.stepYears = stepYears;
            initAdapter();
        }
    }

    public interface OnYearSelectedListener {
        void onYearSelected(WheelYearPicker picker, int position, int year);

        void onYearCurrentScrolled(WheelYearPicker picker, int position, int year);

        void onYearScrolledNewYear(WheelYearPicker picker);
    }
}
