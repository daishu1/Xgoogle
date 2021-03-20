package org.cn.google;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PurchasesUpdatedListener, BillingClientStateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BillingClientManager.init(this,this,this);
        Button button = findViewById(R.id.buttonTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BillingClientManager.querySkuDetailsAsync("inapp", new SkuDetailsResponseListener() {

                        @Override
                        public final void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (billingResult.getResponseCode() != 0 || list == null) {
                                        ToastUtils.showLong("==="+billingResult.getResponseCode() + "===" + GsonUtils.toJson(list));
                                    } else {
                                        ToastUtils.showLong("===请求成功===" + list.size());
//                                        BillingClientManager.launchBillingFlow(MainActivity.this, (SkuDetails) list.get(0));
                                    }
                                }
                            });
                            Log.e("TAG",billingResult.getDebugMessage());
                        }
                    }, "34");
                }catch (Exception e) {
                    ToastUtils.showLong("Exception===" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

    }

    @Override
    public void onBillingServiceDisconnected() {

    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

    }
}