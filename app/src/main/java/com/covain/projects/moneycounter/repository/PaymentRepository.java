package com.covain.projects.moneycounter.repository;

import com.covain.projects.moneycounter.R;
import com.covain.projects.moneycounter.model.Payment;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Covain on 6/10/2017.
 */

public class PaymentRepository {

    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String PAYMENT_TYPE_ID = "paymentTypeId";

    private final Realm realm = Realm.getDefaultInstance();

    public Payment addPayment(Payment payment) {
        realm.beginTransaction();
        Payment paymentFromDb = realm.createObject(Payment.class, payment.getId());
        paymentFromDb.setAmount(payment.getAmount());
        paymentFromDb.setDate(payment.getDate());
        paymentFromDb.setPaymentTypeId(payment.getPaymentTypeId());
        realm.commitTransaction();
        return paymentFromDb;
    }

    public Payment editPayment(final Payment payment) {
        realm.beginTransaction();
        Payment paymentFromDb = realm
                .where(Payment.class)
                .equalTo(ID, payment.getId())
                .findFirst();
        if (paymentFromDb == null) {
            paymentFromDb = realm.createObject(Payment.class, payment.getId());
        }
        paymentFromDb.setDate(payment.getDate());
        paymentFromDb.setAmount(payment.getAmount());
        paymentFromDb.setPaymentTypeId(payment.getPaymentTypeId());
        realm.commitTransaction();
        return paymentFromDb;
    }

    public void removePayment(final Payment payment) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Payment paymentFromDb = realm.where(Payment.class).equalTo(ID, payment.getId()).findFirst();
                paymentFromDb.deleteFromRealm();
            }
        });
    }

    public void deletePaymentById(final long id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Payment paymentFromDb = realm.where(Payment.class).equalTo(ID, id).findFirst();
                paymentFromDb.deleteFromRealm();
            }
        });
    }

    public RealmResults<Payment> getAllPayments() {
        return realm.where(Payment.class).findAllSorted(DATE);
    }

    public RealmResults<Payment> getPayments(Date date) {
        return realm
                .where(Payment.class)
                .equalTo(DATE, date)
                .findAllSorted(DATE);
    }

    public RealmResults<Payment> getEarnPayments(Date dateFrom, Date dateTo) {
        return realm
                .where(Payment.class)
                .between(DATE, dateFrom, dateTo)
                .beginGroup()
                .equalTo(PAYMENT_TYPE_ID, R.string.earn)
                .or()
                .equalTo(PAYMENT_TYPE_ID, R.string.tips)
                .endGroup()
                .findAllSorted(DATE);

    }

    public RealmResults<Payment> getSpentPayments(Date dateFrom, Date dateTo) {
        return realm
                .where(Payment.class)
                .between(DATE, dateFrom, dateTo)
                .equalTo(PAYMENT_TYPE_ID, R.string.spent)
                .findAllSorted(DATE);
    }

    public RealmResults<Payment> getPayments(Date fromDate, Date toDate) {
        return realm
                .where(Payment.class)
                .between(DATE, fromDate, toDate)
                .findAllSorted(DATE);
    }

}
