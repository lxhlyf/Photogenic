package com.example.yxm.photogenic.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ScaleXSpan;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yxm on 2020-1-7
 *
 * @function:
 */
public class TimeHelper {

    /**
     * 将给定的字符串给定的长度两端对齐
     *
     * @param str 待对齐字符串
     * @param size 汉字个数，eg:size=5，则将str在5个汉字的长度里两端对齐
     * @Return
     */
    public static SpannableStringBuilder justifyString(String str, int size) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (android.text.TextUtils.isEmpty(str)) {
            return spannableStringBuilder;
        }
        char[] chars = str.toCharArray();
        if (chars.length >= size || chars.length == 1) {
            return spannableStringBuilder.append(str);
        }
        int l = chars.length;
        float scale = (float) (size - l) / (l - 1);
        for (int i = 0; i < l; i++) {
            spannableStringBuilder.append(chars[i]);
            if (i != l - 1) {
                SpannableString s = new SpannableString("　");//全角空格
                s.setSpan(new ScaleXSpan(scale), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.append(s);
            }
        }
        return spannableStringBuilder;
    }

    /**
     * 秒转分秒
     * @param seconds
     * @return
     */
    public static String secToTime(long seconds){
        if(seconds < 60){
            return String.valueOf(seconds);
        }
        String min = (seconds / 60) + "";
        String second = (seconds % 60) + "";
        if(min.length() < 2){
            min = 0 + min;
        }
        if(second.length() < 2){
            second = 0 + second;
        }
        return min + ":" + second;
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param timeStamp 时间戳
     * @return String date
     */
    public static String timeStamp2Date(long timeStamp) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(timeStamp/1000));
    }

}