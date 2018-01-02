package com.covain.projects.moneycounter.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.covain.projects.moneycounter.R;
import com.covain.projects.moneycounter.activity.MainActivity;

import java.util.Calendar;

import butterknife.ButterKnife;

/**
 * Created by Covain on 6/22/2017.
 */

public class ArchiveFragment extends Fragment
    implements PaymentView {

    MainActivity mActivity;
    DatePickerFromFragment mDatePickerFrom;
    DatePickerToFragment mDatePickerTo;
    Calendar mCalendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_text, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        initParams();
        return view;
    }

    private void initParams() {
        mActivity = (MainActivity) getActivity();
        mCalendar = Calendar.getInstance();
        mDatePickerFrom = new DatePickerFromFragment();
        mDatePickerTo = new DatePickerToFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (R.id.action_pick_date == id) {
            mDatePickerFrom.show(getFragmentManager(), "DatePicker");
        }

        return true;
    }

}
