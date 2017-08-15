package com.cucr.myapplication.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by 911 on 2017/8/11.
 */

public class EditTextUtils {
    public static void set(final EditText et, final String regular, final String msg) {
        et.addTextChangedListener(new TextWatcher() {
            String before = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                before = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches(regular) && !"".equals(s.toString())) {
                    et.setText(before);
                    et.setSelection(et.getText().toString().length());
                    if (msg != null) {
                        ToastUtils.showToast(et.getContext(),msg);
                    }
                }
            }
        });
    }
}
