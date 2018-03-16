package com.xiangsun.commonutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.xiangsun.commonutil.common_util.Util;

import static com.xiangsun.commonutil.Constant.TOAST_MSG;
import static com.xiangsun.commonutil.Constant.TOAST_SHOW;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case TOAST_SHOW:
                    String toastMsg = bundle.getString(TOAST_MSG);
                    Toast.makeText(mContext, toastMsg, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.setHandler(handler);
    }
}
