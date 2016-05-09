package com.expressba.express.sorter.work;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import com.expressba.express.R;

/**
 * Created by 黎明 on 2016/5/7.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    int _year = 2016;
    int _month = 5;
    int _day = 5;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        // TODO 日期选择完成事件，取消时不会触发
        _year = year;
        _month = monthOfYear + 1;
        _day = dayOfMonth;
        SearchWorkFragment fragment = new SearchWorkFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", _year + "-" + _month + "-" + _day);
        fragment.setArguments(bundle);
        FragmentTransaction transactio = getFragmentManager().beginTransaction();
        transactio.replace(R.id.fragment_container_layout, fragment);
        transactio.commit();
    }

    private String getValue() {
        return "" + _year + _month + _day;
    }

}