package com.covain.projects.moneycounter.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Covain on 6/10/2017.
 */

public class Payment extends RealmObject {

    @PrimaryKey
    long id;
    double amount;
    int paymentTypeId;
    @Required
    Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return String.format("Id: %s, Amount: %s, Date: %s, PaymentType: %s",
                id, amount, new SimpleDateFormat("yyyy MM dd", Locale.getDefault()).format(date), paymentTypeId);
    }

    public static class Builder {

        Payment payment;

        public Builder() {
            payment = new Payment();
        }

        public Builder setId(long id) {
            payment.id = id;
            return this;
        }

        public Builder setDate(Date date) {
            payment.date = date;
            return this;
        }

        public Builder setAmount(double amount) {
            payment.amount = amount;
            return this;
        }

        public Builder setPaymentType(int paymentType) {
            payment.paymentTypeId = paymentType;
            return this;
        }

        public Payment build() {
            return payment;
        }

    }
}
