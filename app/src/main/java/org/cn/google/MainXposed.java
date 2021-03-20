package org.cn.google;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;

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
            if (packageName.equals("com.pandavpn.androidproxy")) {
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
                new ImportHooker().hook(application, loadPackageParam);
//                new CollectionHooker().hook(loadPackageParam);

                ToastUtils.showLong("======" + loadPackageParam.processName + "=====");
            }
        }});
    }


    private boolean skipPackageName(String str) {
        return str.startsWith("com.android") || str.startsWith("com.google") || str.contains("launcher3") || str.startsWith("com.samsung") || TextUtils.equals(str, "com.topjohnwu.magisk") || str.startsWith("com.facebook") || TextUtils.equals(str, BuildConfig.APPLICATION_ID);
    }

}
