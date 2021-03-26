package org.cn.google.hook_import.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

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
import org.cn.google.common.ProtoDialog;

import java.util.List;

public class SkuListActivity extends BillingActivity implements PurchasesUpdatedListener, BillingClientStateListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BillingClientManager.init(this, this, this);

    }

    @Override
    public void onClick(View view) {
        ProtoDialog.showLoadingDialog(this);
        BillingClientManager.querySkuDetailsAsync("inapp", new SkuDetailsResponseListener() {
            @Override
            public final void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                StringBuilder sb = new StringBuilder();
                sb.append("getResponseCode:").append(billingResult.getResponseCode()).append("\nList<SkuDetails>:").append(GsonUtils.toJson(list)).append("\n");
                mEditText.setText(sb.toString());

                BillingClientManager.launchBillingFlow(SkuListActivity.this, list.get(0));
                ProtoDialog.dismissLoading();
            }
        }, "23438");
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        super.onItemClick(adapterView, view, i, l);
//    }

    @Override
    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

    }

    @Override
    public void onBillingServiceDisconnected() {

    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        for (int i = 0; i < list.size(); i++) {
            mEditText.setText(mEditText.getText() + "\njsonPurchaseInfo" + list.get(i).getOriginalJson() + "\nmSignature" + list.get(i).getSignature());
        }
    }

    @Override
    public void startActivity(Intent intent) {
        handleEvent(intent);
    }
}
