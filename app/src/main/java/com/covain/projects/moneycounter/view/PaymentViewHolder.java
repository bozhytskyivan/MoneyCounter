package com.covain.projects.moneycounter.view;

import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.covain.projects.moneycounter.R;
import com.covain.projects.moneycounter.enums.PaymentMode;
import com.covain.projects.moneycounter.model.Payment;
import com.covain.projects.moneycounter.presenter.IPaymentsPresenter;

/**
 * Created by Covain on 6/12/2017.
 */

public class PaymentViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private TextView mAmountTextView;
    private ImageView mPaymentMenuImageView;

    private IPaymentsPresenter mPresenter;
    private Payment mPayment;

    public PaymentViewHolder(View itemView, PaymentMode paymentMode) {
        super(itemView);

        mPresenter = (IPaymentsPresenter) itemView.getContext();
        mAmountTextView = (TextView) itemView.findViewById(R.id.amount_text_view);
        mPaymentMenuImageView = (ImageView) itemView.findViewById(R.id.payment_menu_image_view);

        if (PaymentMode.READ_ONLY.equals(paymentMode)) {
            mPaymentMenuImageView.setVisibility(View.GONE);
        } else {
            mPaymentMenuImageView.setOnClickListener(this);
        }
    }

    public void setData(Payment payment) {
        mPayment = payment;
        mAmountTextView.setText(String.valueOf(mPayment.getAmount()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payment_menu_image_view:
                showPopup(v);
                break;
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(itemView.getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.payment_record, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.edit:
                mPresenter.editPayment(mPayment);
                break;
            case R.id.delete:
                mPresenter.deletePayment(mPayment.getId());
                break;
        }
        return true;
    }
}