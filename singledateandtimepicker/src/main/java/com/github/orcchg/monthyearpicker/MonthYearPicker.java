package com.github.orcchg.monthyearpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.florent37.singledateandtimepicker.R;
import com.github.florent37.singledateandtimepicker.widget.WheelMonthPicker;
import com.github.florent37.singledateandtimepicker.widget.WheelPicker;
import com.github.florent37.singledateandtimepicker.widget.WheelYearPicker;

import java.util.Calendar;

public class MonthYearPicker extends LinearLayout {

    public static final int DELAY_BEFORE_CHECK_PAST = 200;

    private WheelMonthPicker monthsPicker;
    private WheelYearPicker yearsPicker;

    private int minMonth = Calendar.JANUARY,  minYear = WheelYearPicker.MIN_YEAR;
    private int maxMonth = Calendar.DECEMBER, maxYear = WheelYearPicker.MAX_YEAR;

    private Listener listener;

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

        monthsPicker.setOnMonthSelectedListener(new WheelMonthPicker.OnMonthSelectedListener() {
            @Override
            public void onMonthSelected(WheelMonthPicker picker, int position, int month) {
                if (checkBeforeMinMonth(picker)) position = 0;
                if (checkAfterMaxMonth(picker))  position = monthsPicker.size() - 1;
                if (listener != null) listener.onMonthChanged(picker.getItemStringByPosition(position), month);
            }

            @Override
            public void onMonthCurrentScrolled(WheelMonthPicker picker, int position, int month) {
                // no-op
            }

            @Override
            public void onMonthScrolledNewMonth(WheelMonthPicker picker) {
                monthsPicker.scrollTo(monthsPicker.getCurrentItemPosition() + 1);
            }
        });

        yearsPicker.setOnYearSelectedListener(new WheelYearPicker.OnYearSelectedListener() {
            @Override
            public void onYearSelected(WheelYearPicker picker, int position, int year) {
                if (checkBeforeMinYear(picker)) position = 0;
                if (checkAfterMaxYear(picker))  position = yearsPicker.size() - 1;
                if (listener != null) listener.onYearChanged(yearsPicker.getItemStringByPosition(position), year);
            }

            @Override
            public void onYearCurrentScrolled(WheelYearPicker picker, int position, int year) {
                // no-op
            }

            @Override
            public void onYearScrolledNewYear(WheelYearPicker picker) {
                yearsPicker.scrollTo(yearsPicker.getCurrentItemPosition() + 1);
            }
        });
    }

    private void init(Context context, AttributeSet attrs) {
        // uses default values
    }

    public int getMinMonth() { return minMonth; }
    public int getMaxMonth() { return maxMonth; }
    public int getMinYear() { return minYear; }
    public int getMaxYear() { return maxYear; }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setMinMonth(int month) { minMonth = month; }
    public void setMaxMonth(int month) { maxMonth = month; }
    public void setMinMaxYears(int min, int max) {
        minYear = min;
        maxYear = max;
        yearsPicker.setMinMaxYears(minYear, maxYear);
    }

    private boolean checkBeforeMinMonth(final WheelPicker picker) {
        final boolean less = monthsPicker.getCurrentMonth() < minMonth;
        picker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (less) {
                    //scroll to Min position
                    monthsPicker.scrollTo(monthsPicker.findIndexOfMonth(minMonth));
                }
            }
        }, DELAY_BEFORE_CHECK_PAST);
        return less;
    }

    private boolean checkAfterMaxMonth(final WheelPicker picker) {
        final boolean greater = monthsPicker.getCurrentMonth() > maxMonth;
        picker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (greater) {
                    //scroll to Max position
                    monthsPicker.scrollTo(monthsPicker.findIndexOfMonth(maxMonth));
                }
            }
        }, DELAY_BEFORE_CHECK_PAST);
        return greater;
    }

    private boolean checkBeforeMinYear(final WheelPicker picker) {
        final boolean less = yearsPicker.getCurrentYear() < minYear;
        picker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (less) {
                    //scroll to Min position
                    yearsPicker.scrollTo(yearsPicker.findIndexOfYear(minYear));
                }
            }
        }, DELAY_BEFORE_CHECK_PAST);
        return less;
    }

    private boolean checkAfterMaxYear(final WheelPicker picker) {
        final boolean greater = yearsPicker.getCurrentYear() > maxYear;
        picker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (greater) {
                    //scroll to Max position
                    yearsPicker.scrollTo(yearsPicker.findIndexOfYear(maxYear));
                }
            }
        }, DELAY_BEFORE_CHECK_PAST);
        return greater;
    }

    public int getMonth() {
        return monthsPicker.getCurrentMonth();
    }

    public int getYear() {
        return yearsPicker.getCurrentYear();
    }

    public interface Listener {
        void onMonthChanged(String displayed, int month);
        void onYearChanged(String displayed, int year);
    }
}
