package com.covain.projects.moneycounter.presenter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.DatePicker;

import com.covain.projects.moneycounter.fragment.DatePickerBaseFragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Covain on 6/23/2017.
 */

public class DatePickerPresenter implements DatePickerDialog.OnDateSetListener {

    private static final String SAVED_DATE_VALUE = "saved_date_value";

    private DatePickerBaseFragment mDatePickerFragment;
    private SharedPreferences mPersistentDate;

    private String yearKey;
    private String monthKey;
    private String dayOfMonthKey;

    public DatePickerPresenter(DatePickerBaseFragment datePickerFragment) {
        mDatePickerFragment = datePickerFragment;
        mPersistentDate = mDatePickerFragment
                .getActivity()
                .getSharedPreferences(SAVED_DATE_VALUE, Context.MODE_PRIVATE);

        yearKey = mDatePickerFragment.getSharedPrefix() + "_year";
        monthKey = mDatePickerFragment.getSharedPrefix() + "_month";
        dayOfMonthKey = mDatePickerFragment.getSharedPrefix() + "_day_of_month";

        fillDatePickerData();
    }

    private void fillDatePickerData() {
        mDatePickerFragment.setmDate(getPersistentDate());
    }

    public Map<Integer, Integer> getPersistentDate() {
        Map<Integer, Integer> result = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        result.put(Calendar.DAY_OF_MONTH, mPersistentDate.getInt(dayOfMonthKey, calendar.get(Calendar.DAY_OF_MONTH)));
        result.put(Calendar.MONTH, mPersistentDate.getInt(monthKey, calendar.get(Calendar.MONTH)));
        result.put(Calendar.YEAR, mPersistentDate.getInt(yearKey, calendar.get(Calendar.YEAR)));
        return result;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SharedPreferences.Editor persistentDateEditor = mPersistentDate.edit();
        persistentDateEditor.putInt(yearKey, year);
        persistentDateEditor.putInt(monthKey, month);
        persistentDateEditor.putInt(dayOfMonthKey, dayOfMonth);
        persistentDateEditor.apply();
    }
}
