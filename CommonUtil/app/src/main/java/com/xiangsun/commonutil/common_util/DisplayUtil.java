package com.xiangsun.commonutil.common_util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Sun on 2018/3/17.
 * 关于显示的工具类
 */

public class DisplayUtil {
    private static final String TAG = "DisplayUtil";

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param context
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param context
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param context
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param context
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * @return 设备屏幕的真实宽高
     */
    public static Point getScreenSize(Context context) {
        Point screen = new Point();
        Display display = ((WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                screen.x = (Integer) mGetRawW.invoke(display);
                screen.y = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                display.getSize(screen);
                Log.e(TAG, "Cannot use reflection to get real screen size. " +
                        "Returned size may be wrong.");
            }
        } else {
            display.getRealSize(screen);
        }
        return screen;
    }

    public static int getScreenWidth(Context context) {
        return getScreenSize(context).x;
    }

    public static int getScreenHeight(Context context) {
        return getScreenSize(context).y;
    }

    /**
     * @return 状态栏高度
     */
    public static int getStatusbarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        } else return 0;
    }

    /**
     * @return 如果设备有虚拟导航栏，返回{@code true}；否则，返回{@code false}
     */
    public static boolean hasNavigationBar(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        return !hasMenuKey && !hasBackKey;
    }

    /**
     * @return 虚拟导航栏的高度
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        } else return 0;
    }

    /**
     * 为{@param editText}设置文字选择时开始、结束标志的颜色
     */
    public static void setSelectionHandlersColor(EditText editText, int color) throws NoSuchFieldException, IllegalAccessException {
        final Class<?> cTextView = TextView.class;
        final Field fhlRes = cTextView.getDeclaredField("mTextSelectHandleLeftRes");
        final Field fhrRes = cTextView.getDeclaredField("mTextSelectHandleRightRes");
        final Field fhcRes = cTextView.getDeclaredField("mTextSelectHandleRes");
        fhlRes.setAccessible(true);
        fhrRes.setAccessible(true);
        fhcRes.setAccessible(true);

        int hlRes = fhlRes.getInt(editText);
        int hrRes = fhrRes.getInt(editText);
        int hcRes = fhcRes.getInt(editText);

        final Field fEditor = TextView.class.getDeclaredField("mEditor");
        fEditor.setAccessible(true);
        final Object editor = fEditor.get(editText);

        final Class<?> cEditor = editor.getClass();
        final Field fSelectHandleL = cEditor.getDeclaredField("mSelectHandleLeft");
        final Field fSelectHandleR = cEditor.getDeclaredField("mSelectHandleRight");
        final Field fSelectHandleC = cEditor.getDeclaredField("mSelectHandleCenter");
        fSelectHandleL.setAccessible(true);
        fSelectHandleR.setAccessible(true);
        fSelectHandleC.setAccessible(true);

        Drawable selectHandleL = ContextCompat.getDrawable(editText.getContext(), hlRes);
        Drawable selectHandleR = ContextCompat.getDrawable(editText.getContext(), hrRes);
        Drawable selectHandleC = ContextCompat.getDrawable(editText.getContext(), hcRes);

        selectHandleL.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        selectHandleR.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        selectHandleC.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        fSelectHandleL.set(editor, selectHandleL);
        fSelectHandleR.set(editor, selectHandleR);
        fSelectHandleC.set(editor, selectHandleC);
    }


}
