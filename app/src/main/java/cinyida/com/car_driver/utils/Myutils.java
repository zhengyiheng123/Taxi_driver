package cinyida.com.car_driver.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/29.
 */
public class Myutils {
    private static int statusBarHeight;
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight != 0)
            return statusBarHeight;

        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    /** 文字局部变色 */
    public static void setTextColorOfPart(Context context, TextView tv, int colorRes, int start, int end) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(
                tv.getText().toString());// 用于可变字符串
        ForegroundColorSpan span = new ForegroundColorSpan(context.getResources()
                .getColor(colorRes));
        spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }
}
