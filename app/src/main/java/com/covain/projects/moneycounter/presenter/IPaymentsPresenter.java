package com.covain.projects.moneycounter.presenter;

import com.covain.projects.moneycounter.model.Payment;

/**
 * Created by Covain on 6/11/2017.
 */

public interface IPaymentsPresenter {

    void addPayment(Payment payment);

    void editPayment(Payment payment);

    void updatePayment(Payment payment);

    void deletePayment(long paymentId);
}
