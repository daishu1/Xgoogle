package org.cn.google;

import android.app.Application;
import android.content.Context;
import android.os.Parcel;
import android.text.TextUtils;

import com.android.billingclient.BuildConfig;

import org.cn.google.app.ProviderBridge;
import org.cn.google.hook_collect.CollectionHooker;
import org.cn.google.hook_export.OutBinderProxyHooker;
import org.cn.google.hook_import.ImportHooker;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author Administrator
 */
public class MainXposed implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        String packageName = loadPackageParam.processName;

        if (!skipPackageName(packageName)) {
            XposedBridge.log("HOOK【...】主进程：" + packageName);
            //com.igg.android.lordsmobile
            //com.pandavpn.androidproxy
            if (packageName.equals("com.igg.android.lordsmobile")) {
                enterApplication(loadPackageParam);
            }

        }
    }

    public void enterApplication(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod(Application.class, "attach", new Object[]{Context.class, new XC_MethodHook() {
            /* class com.game.silkroads.HookApplication.AnonymousClass1 */

            /* access modifiers changed from: protected */
            public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                super.afterHookedMethod(methodHookParam);
                Application application = (Application) methodHookParam.thisObject;
                int hookStatus = ProviderBridge.getHookStatus(application);
                if (hookStatus == 1) {
                    new CollectionHooker().hook(loadPackageParam);
                } else if (hookStatus == 2) {
                    new ImportHooker().hook(application, loadPackageParam);
                } else if (hookStatus == 3 || hookStatus == 4) {
                    new OutBinderProxyHooker().enter(loadPackageParam);
                }
                XposedBridge.log("HOOK STATUS ->" + ProviderBridge.getHookStatus(application));
            }
        }});
    }


    private boolean skipPackageName(String str) {
        return str.startsWith("com.android") || str.startsWith("com.google") || str.contains("launcher3") || str.startsWith("com.samsung") || TextUtils.equals(str, "com.topjohnwu.magisk") || str.startsWith("com.facebook") || TextUtils.equals(str, BuildConfig.APPLICATION_ID);
    }


}
