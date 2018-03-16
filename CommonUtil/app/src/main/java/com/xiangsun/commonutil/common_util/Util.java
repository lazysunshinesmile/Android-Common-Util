package com.xiangsun.commonutil.common_util;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.xiangsun.commonutil.ErrorInfo;

import static com.xiangsun.commonutil.Constant.TOAST_MSG;
import static com.xiangsun.commonutil.Constant.TOAST_SHOW;

public class Util {

    public static Handler handler;

    public static void setHandler(Handler handler) {
        Util.handler = handler;
    }

    public static String getFirstChar(String place) throws PinyinException {
        String ret = "";
        for(int i =0 ; i< place.length();i++) {
            String pinyin = PinyinHelper.convertToPinyinString(String.valueOf(place.charAt(i)), ",", PinyinFormat.WITHOUT_TONE);
            pinyin = pinyin.toUpperCase();
            ret = ret + pinyin.charAt(0);
        }

        return ret;
    }

    public static String getDateStr(int year, int month, int day) {
        String m = "";
        String d = "";
        if(month < 9) {
            m = "0" + (month + 1);
        }
        if(day < 10) {
            d = "0" + day;
        }
        return year + "-" + m + "-" + d;
    }

    public static void toastShow(String msgToShow) {
        Bundle bundle = new Bundle();
        bundle.putString(TOAST_MSG, msgToShow);
        Message msg = new Message();
        msg.setData(bundle);
        msg.what = TOAST_SHOW;
        handler.sendMessage(msg);
    }


    public static void showErrorMsg(int errorNum) {
        switch (errorNum){
            case ErrorInfo.NETWORK_ERROR:
                toastShow("网络错误，请检查网络");
                break;
            case ErrorInfo.OPTION_ERROR:
                toastShow("参数错误，请校验日期，起点，终点是否正确");
                break;
            default:
                break;
        }
    }




}
