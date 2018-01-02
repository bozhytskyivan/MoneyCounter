package com.covain.projects.moneycounter.presenter;

import android.view.View;

import com.covain.projects.moneycounter.model.Payment;
import com.covain.projects.moneycounter.repository.PaymentRepository;

/**
 * Created by Covain on 6/11/2017.
 */

public class PaymentsPresenter implements View.OnClickListener {

    PaymentRepository mPaymentRepository;

    public PaymentsPresenter() {
        mPaymentRepository = new PaymentRepository();
    }


    public void addPayment(Payment payment) {
        mPaymentRepository.addPayment(payment);
    }

    public void editPayment(Payment payment) {
        mPaymentRepository.editPayment(payment);
    }

    @Override
    public void onClick(View v) {

    }
}
