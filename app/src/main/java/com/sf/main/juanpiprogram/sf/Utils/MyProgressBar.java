package com.sf.main.juanpiprogram.sf.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.main.juanpiprogram.R;

/**
 * Created by Administrator on 15-11-23.
 */
public class MyProgressBar extends Dialog {

    private static MyProgressBar dialog = null;

    public MyProgressBar(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyProgressBar(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context        上下文
     * @param message        提示
     * @param cancelable     是否按返回键取消
     * @param cancelListener 按下返回键监听
     * @return
     */
    public static MyProgressBar show(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
        dialog = new MyProgressBar(context, R.style.CustomDialog);
        //  dialog.setTitle(message);
        dialog.setContentView(R.layout.progress_bar_item);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        // 按返回键是否取消
        dialog.setCancelable(cancelable);
        // 监听返回键处理
        dialog.setOnCancelListener(cancelListener);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        Window lp = dialog.getWindow();

        lp.setBackgroundDrawableResource(R.drawable.progress_dialog_shape);

        dialog.show();
        return dialog;
    }

    public static MyProgressBar dismissDialog() {
        dialog.dismiss();
        dialog = null;
       return dialog;
    }


}



    /*public static MyProgressBar createDialog(Context context) {
        myProgressBar = new MyProgressBar(context, R.style.CustomDialog);
        myProgressBar.setContentView(R.layout.progress_bar_item);
        myProgressBar.getWindow().getAttributes().gravity = Gravity.CENTER;

        WindowManager.LayoutParams winbg = myProgressBar.getWindow().getAttributes();
        winbg.dimAmount = 0.1f;
        myProgressBar.getWindow().setAttributes(winbg);

        return myProgressBar;
    }

    }
            */