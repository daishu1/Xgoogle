package org.cn.google.provider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPStaticUtils;

import org.cn.google.app.AppConstance;

public class HookProvider extends BaseProvider {
    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        Bundle bundle = new Bundle();
        bundle.putInt("code", -1);
        if (AppConstance.KEY_HOOK_STATUS.equals(method)) {
            bundle.putInt(method, SPStaticUtils.getInt(method));
        }
        return bundle;
    }
}
