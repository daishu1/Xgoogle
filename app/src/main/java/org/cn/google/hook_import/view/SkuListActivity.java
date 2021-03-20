package org.cn.google.hook_import.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.blankj.utilcode.util.GsonUtils;

import org.cn.google.BillingClientManager;

import java.util.List;

public class SkuListActivity extends BillingActivity implements PurchasesUpdatedListener, BillingClientStateListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BillingClientManager.init(this, this, this);
    }

    @Override
    public void onClick(View view) {
//        ToastUtils.showLong("测试:" + getPackageName());

        BillingClientManager.querySkuDetailsAsync("inapp", new SkuDetailsResponseListener() {
            @Override
            public final void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                StringBuilder sb = new StringBuilder();
                sb.append("getResponseCode:").append(billingResult.getResponseCode()).append("\nList<SkuDetails>:").append(GsonUtils.toJson(list)).append("\n");
                mEditText.setText(sb.toString());

                BillingClientManager.launchBillingFlow(SkuListActivity.this, list.get(0));

            }
        }, "34");
    }

    @Override
    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

    }

    @Override
    public void onBillingServiceDisconnected() {

    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        mEditText.setText(mEditText.getText() + "\nonPurchasesUpdated:" + billingResult.getResponseCode() + "\nList<Purchase>:" + GsonUtils.toJson(list));
    }

    @Override
    public void startActivity(Intent intent) {
        handleEvent(intent);
    }
}
