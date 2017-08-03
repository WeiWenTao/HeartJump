package com.cucr.myapplication.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.cucr.myapplication.R;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by 911 on 2017/4/12.
 */

public class CommonUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 过滤非0-9的数字
     */
    public static String StringFilter(String str) throws PatternSyntaxException {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 设置分割线颜色
     */
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
                        pf.set(picker, 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    /**
     * 获取当前时间
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        String dateStr = format.format(date);
        return dateStr;
    }


    /**
     * wheelView的初始化
     */
    public static List<String> getDateList() {
        List<String> list = null;

        if (list == null) {
            list = new ArrayList<>();
        }

        if (list.size() == 0) {
            for (int i = 1; i <= 12; i++) {
                list.add("2017年" + i + "月");
            }
        }

        return list;

    }


    //获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    //显示缩略图
    public static Bitmap decodeBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 通过这个bitmap获取图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap == null) {
//            System.out.println("bitmap为空");
        }
        float realWidth = options.outWidth;
        float realHeight = options.outHeight;
//        System.out.println("真实图片高度：" + realHeight + "宽度:" + realWidth);
        // 计算缩放比
        int scale = (int) ((realHeight > realWidth ? realHeight : realWidth) / 800);

        if (scale <= 0) {
            scale = 1;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        // 注意这次要把options.inJustDecodeBounds 设为 false,这次图片是要读取出来的。
        bitmap = BitmapFactory.decodeFile(path, options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
//        System.out.println("缩略图高度：" + h + "宽度:" + w);
        return bitmap;
    }


    //初始化Popwindow背景
    public static void initPopBg(boolean isIn, FrameLayout fl_pop_bg) {

        //防止重复创建对象
        AlphaAnimation animation1 = null;
        AlphaAnimation animation2 = null;

        //进入动画
        if (animation1 == null) {
            animation1 = new AlphaAnimation(0.0f, 1.0f);
        }

        //退出动画
        if (animation2 == null) {
            animation2 = new AlphaAnimation(1.0f, 0.0f);
        }

        animation1.setDuration(200);
        animation2.setDuration(200);
        fl_pop_bg.setAnimation(isIn ? animation1 : animation2);
        fl_pop_bg.setVisibility(isIn ? View.VISIBLE : View.GONE);

    }

}
