package com.github.orcchg.monthyearpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.florent37.singledateandtimepicker.R;
import com.github.florent37.singledateandtimepicker.widget.WheelMonthPicker;
import com.github.florent37.singledateandtimepicker.widget.WheelPicker;
import com.github.florent37.singledateandtimepicker.widget.WheelYearPicker;

import java.util.Calendar;
import java.util.Date;

public class MonthYearPicker extends LinearLayout {

    public static final int DELAY_BEFORE_CHECK_PAST = 200;

    private WheelMonthPicker monthsPicker;
    private WheelYearPicker yearsPicker;

    private Date minDate;
    private Date maxDate;

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
                updateListener();
                checkMinMaxDate(picker);
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
                updateListener();
                checkMinMaxDate(picker);
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
        // TODO
    }

    private void checkMinMaxDate(final WheelPicker picker){
        checkBeforeMinDate(picker);
        checkAfterMaxDate(picker);
    }

    private void checkBeforeMinDate(final WheelPicker picker) {
        picker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (minDate != null && isBeforeMinDate(getDate())) {
                    //scroll to Min position
                    monthsPicker.scrollTo(monthsPicker.findIndexOfDate(minDate));
                    yearsPicker.scrollTo(yearsPicker.findIndexOfDate(minDate));
                }
            }
        }, DELAY_BEFORE_CHECK_PAST);
    }

    private void checkAfterMaxDate(final WheelPicker picker) {
        picker.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (maxDate != null && isAfterMaxDate(getDate())) {
                    //scroll to Max position
                    monthsPicker.scrollTo(monthsPicker.findIndexOfDate(maxDate));
                    yearsPicker.scrollTo(yearsPicker.findIndexOfDate(maxDate));
                }
            }
        }, DELAY_BEFORE_CHECK_PAST);
    }

    public Date getDate() {
//        int hour = hoursPicker.getCurrentHour();
//        if (isAmPm && amPmPicker.isPm()) {
//            hour += PM_HOUR_ADDITION;
//        }
//        final int minute = minutesPicker.getCurrentMinute();
//
        final Calendar calendar = Calendar.getInstance();
//        final Date dayDate = daysPicker.getCurrentDate();
//        calendar.setTime(dayDate);
//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    private boolean isBeforeMinDate(Date date) {
        final Calendar minDateCalendar = Calendar.getInstance();
        minDateCalendar.setTime(minDate);
        minDateCalendar.set(Calendar.MILLISECOND, 0);
        minDateCalendar.set(Calendar.SECOND, 0);

        final Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        dateCalendar.set(Calendar.MILLISECOND, 0);
        dateCalendar.set(Calendar.SECOND, 0);

        return dateCalendar.before(minDateCalendar);
    }

    private boolean isAfterMaxDate(Date date) {
        final Calendar maxDateCalendar = Calendar.getInstance();
        maxDateCalendar.setTime(maxDate);
        maxDateCalendar.set(Calendar.MILLISECOND, 0);
        maxDateCalendar.set(Calendar.SECOND, 0);

        final Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        dateCalendar.set(Calendar.MILLISECOND, 0);
        dateCalendar.set(Calendar.SECOND, 0);

        return dateCalendar.after(maxDateCalendar);
    }

    private void updateListener() {
        //
    }
}
