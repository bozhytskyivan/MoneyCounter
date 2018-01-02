package com.covain.projects.moneycounter.model;

import com.covain.projects.moneycounter.R;

/**
 * Created by Covain on 6/11/2017.
 */

public enum PaymentType {

    EARN(R.string.earn),
    SPENT(R.string.spent),
    TIPS(R.string.tips);

    int id;

    PaymentType(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
