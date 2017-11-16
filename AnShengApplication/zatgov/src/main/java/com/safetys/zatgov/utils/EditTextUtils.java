package com.safetys.zatgov.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Author:Created by Ricky on 2017/11/15.
 * Description:
 */
public class EditTextUtils {
    public static void editWatcher(final EditText editText, final EditTextChanged editTextChanged) {

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                editTextChanged.beforeTextChanged();
            }

        });
    }

    public interface EditTextChanged {

        void beforeTextChanged();

    }
}
