package org.cn.google.common;

import android.app.ProgressDialog;
import android.content.Context;

public class ProtoDialog {

    private static ProgressDialog pd;

    public static void showLoadingDialog(Context context) {
        if (pd == null) {
            ProgressDialog progressDialog = new ProgressDialog(context);
            pd = progressDialog;
            progressDialog.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage("加载中...");
        }
        if (!pd.isShowing()) {
            pd.show();
        }
    }

    public static void dismissLoading() {
        ProgressDialog progressDialog = pd;
        if (progressDialog != null && progressDialog.isShowing()) {
            pd.dismiss();
        }
    }

}
