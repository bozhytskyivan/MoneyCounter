package com.covain.projects.moneycounter.fragment;

/**
 * Created by Covain on 6/22/2017.
 */

public class DatePickerFromFragment extends DatePickerBaseFragment {

    public static String SHARED_PREFIX = "from";

    @Override
    public String getSharedPrefix() {
        return SHARED_PREFIX;
    }
}
