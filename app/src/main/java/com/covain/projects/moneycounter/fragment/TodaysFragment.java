package com.covain.projects.moneycounter.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.covain.projects.moneycounter.R;
import com.covain.projects.moneycounter.activity.MainActivity;
import com.covain.projects.moneycounter.adapter.PaymentsAdapter;
import com.covain.projects.moneycounter.enums.EditMode;
import com.covain.projects.moneycounter.enums.PaymentMode;
import com.covain.projects.moneycounter.repository.PaymentRepository;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodaysFragment extends Fragment
        implements View.OnClickListener, PaymentView {

    @BindView(R.id.earn_recycler_view)
    RecyclerView mEarnRecyclerView;
    @BindView(R.id.spent_recycler_view)
    RecyclerView mSpentRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mFAB;

    MainActivity mActivity;
    PaymentRepository mPaymentRepository;
    PaymentsAdapter mEarnAdapter;
    PaymentsAdapter mSpentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mActivity = (MainActivity) getActivity();
        mPaymentRepository = new PaymentRepository();
        mFAB.setOnClickListener(this);
        mEarnAdapter = new PaymentsAdapter(null, PaymentMode.READ_WRITE);
        mSpentAdapter = new PaymentsAdapter(null, PaymentMode.READ_WRITE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mActivity);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mActivity, layoutManager.getOrientation());

        mEarnRecyclerView.addItemDecoration(dividerItemDecoration);
        mSpentRecyclerView.addItemDecoration(dividerItemDecoration);
        mEarnRecyclerView.setLayoutManager(layoutManager);
        mSpentRecyclerView.setLayoutManager(layoutManager1);
        mEarnRecyclerView.setAdapter(mEarnAdapter);
        mSpentRecyclerView.setAdapter(mSpentAdapter);

        Calendar calendar = initCalendar();
        Date startOfTheDay = new Date(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endOfTheDay = new Date(calendar.getTimeInMillis());
        mEarnAdapter.setmData(mPaymentRepository.getEarnPayments(startOfTheDay, endOfTheDay));
        mSpentAdapter.setmData(mPaymentRepository.getSpentPayments(startOfTheDay, endOfTheDay));
    }

    private Calendar initCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                mActivity.showPaymentDialog(EditMode.CREATE, null);
                break;
        }
    }
}
