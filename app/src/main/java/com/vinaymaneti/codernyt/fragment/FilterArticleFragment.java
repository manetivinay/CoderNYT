package com.vinaymaneti.codernyt.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vinaymaneti.codernyt.R;
import com.vinaymaneti.codernyt.model.SearchRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 23/10/16.
 */

public class FilterArticleFragment extends DialogFragment implements View.OnClickListener,
        DatePickerFragment.onDatePickerSelectionListener,
        CompoundButton.OnCheckedChangeListener {

    private OnFilterSearchListener mOnFilterSearchListener;
    public static final String DATE_PICKER = "DatePicker";

    @BindView(R.id.cancelBtn)
    AppCompatImageView cancelBtn;

    @BindView(R.id.radioPriority)
    RadioGroup radioPriority;

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

    @BindView(R.id.startDateTv)
    AppCompatTextView startDateTv;

    @BindView(R.id.endDateTv)
    AppCompatTextView endDateTv;

    @BindView(R.id.searchBtn)
    AppCompatButton searchBtn;

    RadioButton radioButton;
    private boolean artsStatus = false;
    private boolean fAndSCbxStatus = false;
    private boolean sportsCbxStatus = false;
    SearchRequest mSearchRequest;
    private String beginDate = null, endDate = null;
    private String order = "newest";

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
        mSearchRequest = new SearchRequest();
        startDateView.setOnClickListener(this);
        endDateView.setOnClickListener(this);
        artsCbx.setOnCheckedChangeListener(this);
        fAndSCbx.setOnCheckedChangeListener(this);
        sportsCbx.setOnCheckedChangeListener(this);
        cancelBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        radioPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) group.findViewById(checkedId);
                order = radioButton.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startDateView:
                showDatePickerDialog(0);
                break;
            case R.id.endDateView:
                showDatePickerDialog(1);
                break;
            case R.id.cancelBtn:
                getDialog().dismiss();
                break;
            case R.id.searchBtn:
                this.mOnFilterSearchListener.onFilterComplete(order, artsStatus, fAndSCbxStatus, sportsCbxStatus, beginDate, endDate);
                getDialog().dismiss();
                break;
        }
    }

    private void showDatePickerDialog(int i) {
        DialogFragment dialogFragment = DatePickerFragment.newInstance(this, i);
        dialogFragment.show(getFragmentManager(), DATE_PICKER);
    }

    @Override
    public void onCompleteDateSelected(String date, int value) {
        if (value == 0) {
            beginDate = date;
            startDateTv.setText(date);
        } else {
            endDate = date;
            endDateTv.setText(date);

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.artsCbx:
                Toast.makeText(getContext(), buttonView.getText(), Toast.LENGTH_LONG).show();
                artsStatus = isChecked;
                break;
            case R.id.fAndSCbx:
                Toast.makeText(getContext(), buttonView.getText(), Toast.LENGTH_LONG).show();
                fAndSCbxStatus = isChecked;
                break;
            case R.id.sportsCbx:
                Toast.makeText(getContext(), buttonView.getText(), Toast.LENGTH_LONG).show();
                sportsCbxStatus = isChecked;
                break;


        }
    }

    public interface OnFilterSearchListener {
        void onFilterComplete(String order, boolean hasArts, boolean hasFashionAndStyle, boolean hasSports, String beginDate, String endDate);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mOnFilterSearchListener = (OnFilterSearchListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "Must implement mOnFilterSearchListener");
        }
    }
}
