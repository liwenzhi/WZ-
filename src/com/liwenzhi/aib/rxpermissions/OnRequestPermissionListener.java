package com.liwenzhi.aib.rxpermissions;

import android.content.Context;
import com.liwenzhi.aib.util.CommonUtils;


/**
 * 请求权限监听
 */

public abstract class OnRequestPermissionListener {

    /**
     * 同意权限
     */
    public abstract void onGrantedPermission();

    /**
     * 拒绝权限
     */
    public void onDeniedPermission(Context context, String deniedNotify) {
        CommonUtils.showToast(context, deniedNotify);
    }

    /**
     * 拒绝权限同时不再询问
     */
    public void onDeniedPermissionWithAskNeverAgain(Context context, String deniedNotify) {
        CommonUtils.showToast(context, deniedNotify);
    }

    /**
     * 出现异常
     */
    public void onError() {
    }

}
