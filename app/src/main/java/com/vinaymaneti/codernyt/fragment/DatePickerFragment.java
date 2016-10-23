package com.vinaymaneti.codernyt.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by vinay on 23/10/16.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static int value;
    private onDatePickerSelectionListener mOnDatePickerSelectionListener;

    public static DatePickerFragment newInstance(onDatePickerSelectionListener listener, int i) {
        DatePickerFragment fragment = new DatePickerFragment();
        value = i;
        fragment.setOnDatePickerSelectionListener(listener, i);
        return fragment;
    }

    public onDatePickerSelectionListener getOnDatePickerSelectionListener() {
        return mOnDatePickerSelectionListener;
    }

    public void setOnDatePickerSelectionListener(onDatePickerSelectionListener onDatePickerSelectionListener, int i) {
        mOnDatePickerSelectionListener = onDatePickerSelectionListener;
        value = i;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        Toast.makeText(getContext(), formattedDate, Toast.LENGTH_LONG).show();
        if (this.mOnDatePickerSelectionListener != null)
            mOnDatePickerSelectionListener.onCompleteDateSelected(formattedDate, value);
    }

    public interface onDatePickerSelectionListener {
        void onCompleteDateSelected(String date, int value);
    }
}
