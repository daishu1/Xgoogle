package org.cn.google.hook_collect;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;

import org.cn.google.app.ProviderBridge;

import java.util.ArrayList;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class CollectionHooker {

    public void hook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        CurrentActivityUtils.hookCurrentActivity(loadPackageParam);
        new CollActivityHooker().hook(loadPackageParam);
        hookGoogleBinder(loadPackageParam);
    }

    public void hookGoogleBinder(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod("android.os.BinderProxy", loadPackageParam.classLoader, "transact", new Object[]{Integer.TYPE, Parcel.class, Parcel.class, Integer.TYPE, new XC_MethodHook() {

            public void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                int intValue = ((Integer) methodHookParam.args[0]).intValue();
                if (intValue == 8 || intValue == 3) {
                    Parcel parcel = (Parcel) methodHookParam.args[1];
                    parcel.setDataPosition(4);
                    if (TextUtils.equals(parcel.readString(), "com.android.vending.billing.IInAppBillingService")) {
                        CollectionHooker.this.getSku((IBinder) methodHookParam.thisObject, parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString());
                    }
                }
            }
        }});
    }


    public void getSku(IBinder iBinder, int i, String str, String str2, String str3) {
        Application currentApplication = AndroidAppHelper.currentApplication();
        try {
            ArrayList<String> stringArrayList = getSkuDetails(iBinder, i, str, str2, str3).getStringArrayList("DETAILS_LIST");
            if (stringArrayList == null || stringArrayList.isEmpty()) {
                throw new Exception("谷歌请求档位信息失败");
            }
            String str4 = stringArrayList.get(0);
            httpStoreSku(currentApplication, str4);
        } catch (Exception e) {
            ToastUtils.showShort("采集失败" + e.getMessage());
        }
    }

    public void httpStoreSku(Context context, String str) throws Exception {
        String packageName = context.getPackageName();
        String charSequence = context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
        Bundle bundle = new Bundle();
        bundle.putString("skuJson", str);
        bundle.putString("gameName", charSequence);
        bundle.putString("packageName", packageName);
        Bundle httpCollSku = ProviderBridge.httpPutSkuDetails(context, bundle);
        if (ProviderBridge.checkResultCode(httpCollSku)) {
            ToastUtils.showShort("采集成功");
            return;
        }
        String message = ProviderBridge.getResultMsg(httpCollSku);
        throw new Exception(message);
    }


    public Bundle getSkuDetails(IBinder iBinder, int i, String str, String str2, String str3) throws RemoteException {
        Bundle bundle = new Bundle();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str2);
        bundle.putStringArrayList("ITEM_ID_LIST", arrayList);
        bundle.putString("playBillingLibraryVersion", "2.0.0");
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.vending.billing.IInAppBillingService");
            obtain.writeInt(i);
            obtain.writeString(str);
            obtain.writeString(str3);
            obtain.writeInt(1);
            bundle.writeToParcel(obtain, 0);
            iBinder.transact(2, obtain, obtain2, 0);
            obtain2.readException();
            return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
        return null;
    }

}
