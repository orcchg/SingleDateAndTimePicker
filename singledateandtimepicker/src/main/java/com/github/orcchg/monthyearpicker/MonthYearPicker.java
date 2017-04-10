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
                if (listener != null) listener.onMonthChanged(picker.getItemStringByPosition(position), month);
                checkMinMaxMonth(picker);
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
                if (listener != null) listener.onYearChanged(yearsPicker.getItemStringByPosition(position), year);
                checkMinMaxYear(picker);
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

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setMinMonth(int month) { minMonth = month; }
    public void setMaxMonth(int month) { maxMonth = month; }
    public void setMinYear(int year) { minYear = year; }
    public void setMaxYear(int year) { maxYear = year; }

    private void checkMinMaxMonth(final WheelPicker picker) {
        checkBeforeMinMonth(picker);
        checkAfterMaxMonth(picker);
    }

    private void checkMinMaxYear(final WheelPicker picker) {
        checkBeforeMinYear(picker);
        checkAfterMaxYear(picker);
    }

    private void checkBeforeMinMonth(final WheelPicker picker) {
        picker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (monthsPicker.getCurrentMonth() < minMonth) {
                    //scroll to Min position
                    monthsPicker.scrollTo(monthsPicker.findIndexOfMonth(minMonth));
                }
            }
        }, DELAY_BEFORE_CHECK_PAST);
    }

    private void checkAfterMaxMonth(final WheelPicker picker) {
        picker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (monthsPicker.getCurrentMonth() > maxMonth) {
                    //scroll to Max position
                    monthsPicker.scrollTo(monthsPicker.findIndexOfMonth(maxMonth));
                }
            }
        }, DELAY_BEFORE_CHECK_PAST);
    }

    private void checkBeforeMinYear(final WheelPicker picker) {
        picker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (yearsPicker.getCurrentYear() < minYear) {
                    //scroll to Min position
                    yearsPicker.scrollTo(yearsPicker.findIndexOfYear(minYear));
                }
            }
        }, DELAY_BEFORE_CHECK_PAST);
    }

    private void checkAfterMaxYear(final WheelPicker picker) {
        picker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (yearsPicker.getCurrentYear() > maxYear) {
                    //scroll to Max position
                    yearsPicker.scrollTo(yearsPicker.findIndexOfYear(maxYear));
                }
            }
        }, DELAY_BEFORE_CHECK_PAST);
    }

    public int getMoth() {
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
