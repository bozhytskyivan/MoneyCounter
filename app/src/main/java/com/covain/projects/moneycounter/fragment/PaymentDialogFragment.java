package com.covain.projects.moneycounter.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.covain.projects.moneycounter.R;
import com.covain.projects.moneycounter.enums.EditMode;
import com.covain.projects.moneycounter.model.Payment;
import com.covain.projects.moneycounter.model.PaymentType;
import com.covain.projects.moneycounter.presenter.IPaymentsPresenter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Covain on 6/11/2017.
 */

public class PaymentDialogFragment extends DialogFragment {

    private static final Map<String, Integer> paymentTypes = new HashMap<>();

    Spinner mPaymentTypeSpinner;
    EditText mAmountEditText;
    IPaymentsPresenter presenter;

    private EditMode mode = EditMode.CREATE;
    private Payment payment = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private Dialog init() {
        initParams();
        presenter = (IPaymentsPresenter) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View addPaymentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_payment, null);
        mPaymentTypeSpinner = (Spinner) addPaymentLayout.findViewById(R.id.payment_type_spinner);
        mAmountEditText = (EditText) addPaymentLayout.findViewById(R.id.amount_edit_text);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.payment_type,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPaymentTypeSpinner.setAdapter(adapter);
        if (EditMode.EDIT.equals(mode) && payment != null) {
            mPaymentTypeSpinner.setSelection(adapter.getPosition(getString(payment.getPaymentTypeId())));
            mAmountEditText.setText(String.valueOf(payment.getAmount()));
        }

        return builder.setMessage(R.string.add_payment_title)
                .setView(addPaymentLayout)
                .setPositiveButton(R.string.fire, positiveButtonListener)
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private void initParams() {
        for (PaymentType type : PaymentType.values()) {
            paymentTypes.put(getActivity().getString(type.getId()), type.getId());
        }
    }

    private DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            presenter.addPayment(Payment.builder()
                    .setId(System.currentTimeMillis())
                    .setDate(new Date())
                    .setAmount(Double.valueOf(mAmountEditText.getText().toString()))
                    .setPaymentType(paymentTypes.get(mPaymentTypeSpinner.getSelectedItem()))
                    .build());
        }
    };

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        PaymentDialogFragment paymentDialogFragment;

        private Builder() {
            paymentDialogFragment = new PaymentDialogFragment();
        }

        public Builder setMode(EditMode paymentMode) {
            paymentDialogFragment.mode = paymentMode;
            return this;
        }

        public Builder setPayment(Payment payment) {
            paymentDialogFragment.payment = payment;
            return this;
        }

        public PaymentDialogFragment create() {
            return paymentDialogFragment;
        }
    }
}
