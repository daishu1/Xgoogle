package org.cn.google.provider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.lzy.okgo.OkGo;

import org.cn.google.app.AppConstance;
import org.cn.google.mode.SkuDetailModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HookProvider extends BaseProvider {
    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        Bundle bundle = new Bundle();
        bundle.putInt("code", -1);
        if (extras == null) {
            bundle.putString("message", "参数错误");
        } else {
            dispatchMethod(method, extras, bundle);
        }
        return bundle;
    }
//    private void dispatchMethod(String str, Bundle bundle, Bundle bundle2) {
//        if (ProviderApi.HOOK_STATUS.equals(str)) {
//            bundle2.putInt(ProviderApi.HOOK_STATUS, this.hookRepository.getHookStatus());
//        } else if (ProviderApi.HTTP_COLL_SKU.equals(str)) {
//            new MethodCollection(bundle).getCollectionResult(bundle2);
//        } else if (ProviderApi.HTTP_GET_SKU_LIST.equals(str)) {
//            new MethodImport().getSkuDetail(bundle2, bundle);
//        } else if (ProviderApi.HTTP_IN_STORE.equals(str)) {
//            new MethodImport().importPurchase(bundle2, bundle);
//        }
//    }

    private void dispatchMethod(String method, Bundle extras, Bundle bundle) {
        if (AppConstance.KEY_HOOK_STATUS.equals(method)) {
            getHookStatus(bundle);
        } else if (AppConstance.HTTP_GET_SKU_LIST.equals(method)) {
            httpGetSkuList(extras, bundle);
        } else if (AppConstance.HTTP_PUT_SKU_DETAILS.equals(method)) {
            httpPutSkuDetails(extras, bundle);
        }
    }

    /**
     * 获取hook状态
     */
    private void getHookStatus(Bundle bundle) {
        bundle.putInt("code", 0);
        bundle.putInt(AppConstance.KEY_HOOK_STATUS, SPStaticUtils.getInt(AppConstance.KEY_HOOK_STATUS));
    }

    /**
     * 上传sku详情
     */
    private void httpPutSkuDetails(Bundle extras, Bundle bundle) {

        String skuJson = extras.getString("skuJson");
        String gameName = extras.getString("gameName");
        String packageName = extras.getString("packageName");

        //此处添加网络请求数据

//        try {
//            OkGo.<String>get("http://games.usbuydo.com/api/login/login")
//                    .tag(this)
//                    .execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //此处添加网络请求数据

        //一下测试数据
        String json = "skuJson=" + skuJson +
                ",gameName=" + gameName +
                ",packageName=" + packageName;
        SPStaticUtils.put(AppConstance.HTTP_PUT_SKU_DETAILS, GsonUtils.toJson(json));

        bundle.putInt("code", 0);
    }

    /**
     * 获取sku列表
     */
    private void httpGetSkuList(Bundle extras, Bundle bundle) {
        String packageName = extras.getString("packageName");

        //此处添加网络请求数据


        //此处添加网络请求数据

        //一下测试数据
        GsonUtils.fromJson("", List.class);
        bundle.putInt("code", 0);
        bundle.putString("data", "这是测试数据->获取SKU列表" + packageName);
    }

    class SkuTestModel {
        private List<SkuDetailModel> skuDetailModels = new ArrayList<>();
        private String gameName;
        private String packageName;

        public List<SkuDetailModel> getSkuDetailModels() {
            return skuDetailModels;
        }

        public void setSkuDetailModels(List<SkuDetailModel> skuDetailModels) {
            this.skuDetailModels = skuDetailModels;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }
    }
}
