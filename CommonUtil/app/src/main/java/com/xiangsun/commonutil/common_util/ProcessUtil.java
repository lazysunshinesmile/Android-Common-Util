package com.xiangsun.commonutil.common_util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

/**
 * Created by Sun on 2016/12/28.
 */

public class ProcessUtil {
    /**
     * 检测某应用是否已退到后台
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isBackground(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return !cn.getPackageName().equals(packageName);
    }
}
