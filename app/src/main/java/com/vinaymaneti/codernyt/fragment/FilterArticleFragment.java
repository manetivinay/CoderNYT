package com.vinaymaneti.codernyt.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.vinaymaneti.codernyt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 23/10/16.
 */

public class FilterArticleFragment extends DialogFragment {

    @BindView(R.id.cancelBtn)
    AppCompatImageView cancelBtn;

    @BindView(R.id.radioPriority)
    RadioGroup radioPriority;

    @BindView(R.id.newestRb)
    AppCompatRadioButton newestRb;

    @BindView(R.id.oldestRb)
    AppCompatRadioButton oldestRb;

    @BindView(R.id.artsCbx)
    AppCompatCheckBox artsCbx;

    @BindView(R.id.fAndSCbx)
    AppCompatCheckBox fAndSCbx;

    @BindView(R.id.sportsCbx)
    AppCompatCheckBox sportsCbx;

    @BindView(R.id.startDateView)
    RelativeLayout startDateView;

    @BindView(R.id.endDateView)
    RelativeLayout endDateView;

    @BindView(R.id.searchBtn)
    AppCompatButton searchBtn;

    public FilterArticleFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_search, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }
}
