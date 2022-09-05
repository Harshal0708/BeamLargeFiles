//package com.example.android.beamlargefiles.activity;
//
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.DialogFragment;
//
//import java.util.Calendar;
//
//public class DatePicker extends DialogFragment {
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        Calendar mCalendar = Calendar.getInstance();
//
//        int year = mCalendar.get(Calendar.YEAR);
//        int month = mCalendar.get(Calendar.MONTH);
//        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
////        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
//
//        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)
//                getActivity(), year, month, dayOfMonth);
//    }
//}