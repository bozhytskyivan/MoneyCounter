package com.covain.projects.moneycounter.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.covain.projects.moneycounter.R;
import com.covain.projects.moneycounter.enums.PaymentMode;
import com.covain.projects.moneycounter.model.Payment;
import com.covain.projects.moneycounter.repository.PaymentRepository;
import com.covain.projects.moneycounter.view.PaymentViewHolder;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;

/**
 * Created by Covain on 6/12/2017.
 */

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentViewHolder>
        implements OrderedRealmCollectionChangeListener<RealmResults<Payment>> {

    private RealmResults<Payment> mData;

    private PaymentMode paymentMode = PaymentMode.READ_WRITE;

    public PaymentsAdapter(@Nullable RealmResults<Payment> data, PaymentMode paymentMode) {
        this.paymentMode = paymentMode;

        if (data != null) {
            setmData(data);
            mData.addChangeListener(this);
        }

    }

    public void setmData(RealmResults<Payment> data) {
        if (data != null) {
            mData = data;
            mData.addChangeListener(this);
            notifyDataSetChanged();
        }
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PaymentViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_paymnet_item, parent, false), paymentMode);

    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onChange(RealmResults<Payment> payments, OrderedCollectionChangeSet changeSet) {
        notifyDataSetChanged();
    }


}
