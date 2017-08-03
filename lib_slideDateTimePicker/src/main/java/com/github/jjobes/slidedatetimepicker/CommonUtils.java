package com.github.jjobes.slidedatetimepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

/**
 * Created by 911 on 2017/7/25.
 */
public class CommonUtils {
    public static void setDatePickerDividerColor(Context context, DatePicker datePicker) {

        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);

        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();

            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(context.getResources().getColor(R.color.pink)));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }


            //设置分割线高度
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDividerHeight")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public static void setNumberPickerDivider(Context context, NumberPicker numberPicker) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            try {
                Field dividerField = numberPicker.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(
                        ContextCompat.getColor(context,R.color.pink));
                dividerField.set(numberPicker, colorDrawable);
                numberPicker.invalidate();
            } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                Log.w("setNumberPickerTxtClr", e);
            }
        }
    }

}
