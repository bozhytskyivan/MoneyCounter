package com.covain.projects.moneycounter.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.covain.projects.moneycounter.R;
import com.covain.projects.moneycounter.adapter.PaymentsAdapter;
import com.covain.projects.moneycounter.enums.EditMode;
import com.covain.projects.moneycounter.enums.PaymentMode;
import com.covain.projects.moneycounter.fragment.PaymentDialogFragment;
import com.covain.projects.moneycounter.model.Payment;
import com.covain.projects.moneycounter.presenter.IPaymentsPresenter;
import com.covain.projects.moneycounter.repository.PaymentRepository;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity1 extends MainActivity
        implements View.OnClickListener {

    @BindView(R.id.earn_recycler_view)
    RecyclerView mEarnRecyclerView;
    @BindView(R.id.spent_recycler_view)
    RecyclerView mSpentRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mFAB;

    PaymentMode paymentMode = PaymentMode.READ_WRITE;

    private final PaymentRepository paymentRepository = new PaymentRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        super.init();
        mFAB.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mEarnRecyclerView.getContext(),
                layoutManager.getOrientation());
        mEarnRecyclerView.addItemDecoration(dividerItemDecoration);
        mEarnRecyclerView.setLayoutManager(layoutManager);
        PaymentsAdapter earnAdapter = new PaymentsAdapter(null, paymentMode);
        mEarnRecyclerView.setAdapter(earnAdapter);
        earnAdapter.setmData(paymentRepository.getEarnPayments(new Date(), new Date()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void addPayment(Payment payment) {
        paymentRepository.addPayment(payment);
    }

    @Override
    public void editPayment(Payment payment) {
        showPaymentDialog(EditMode.EDIT, payment);
    }

    @Override
    public void updatePayment(Payment payment) {
        paymentRepository.editPayment(payment);
    }

    @Override
    public void deletePayment(long paymentId) {
        paymentRepository.deletePaymentById(paymentId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                showPaymentDialog(EditMode.CREATE, null);
                break;
        }
    }
}
