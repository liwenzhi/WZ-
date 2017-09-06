package com.liwenzhi.aib.util;

import android.util.TypedValue;
import com.liwenzhi.aib.app.App;

/**
 *
 */
public class DisplayUtils {

    private DisplayUtils() {
    }

    /**
     * dp 转 px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                App.getInstance().getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spVal
     * @return
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, App.getInstance().getResources().getDisplayMetrics());
    }
}
