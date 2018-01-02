package com.covain.projects.moneycounter.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.covain.projects.moneycounter.presenter.DatePickerPresenter;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by Covain on 6/23/2017.
 */

public abstract class DatePickerBaseFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener mOnDateSetListener;
    DatePickerDialog mDatePickerDialog;
    Map<Integer, Integer> mDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerPresenter mDatePickerFromPresenter = new DatePickerPresenter(this);
        setmOnDateSetListener(mDatePickerFromPresenter);
        if (mDatePickerDialog == null) {
            mDatePickerDialog = new DatePickerDialog(getActivity(),
                    mOnDateSetListener,
                    mDate.get(Calendar.YEAR),
                    mDate.get(Calendar.MONTH),
                    mDate.get(Calendar.DAY_OF_MONTH));
        }
        return mDatePickerDialog;
    }

    public void setmOnDateSetListener(DatePickerDialog.OnDateSetListener listener) {
        mOnDateSetListener = listener;
    }

    public void setmDate(Map<Integer, Integer> date) {
        mDate = date;
    }

    public abstract String getSharedPrefix();

/*    public void onDateChanged(int year, int month, int dayOfMonth) {
        mDatePickerDialog.updateDate(year, month, dayOfMonth);
    }*/
}
