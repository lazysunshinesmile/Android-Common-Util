package com.xiangsun.commonutil.common_util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;


/**
 * Created by admin on 2017/1/4.
 */

public class ShareUtil {
    private static final String title = "分享到...";

    /**
     * 分享文字
     * @param context
     * @param content 需要分享的文字内容
     */
    public static void shareText(Context context, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(intent, title));
    }

    /**
     * 分享图片
     * @param context
     * @param path 需要分享的图片内容
     */
    public static void shareImage(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        Uri uri = Uri.fromFile(new File(path));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent, title));
    }
}
