package com.damlacim.fitme.feature.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.damlacim.fitme.R;

public class LottieProgressDialog extends Dialog {
    public LottieProgressDialog(@NonNull Context context) {
        super(context, R.style.LottieProgressDialog);
        setContentView(R.layout.layout_lottie);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    private void clearOverlay() {
        if (getWindow() != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    @Override
    public void cancel() {
        if (isShowing()) {
            super.cancel();
        }
    }
}