package org.cn.google.hook_import.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.cn.google.app.AppConstance;
import org.cn.google.app.ProviderBridge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

public class BaseSkuActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    LinearLayout mRootLinearLayout;
    EditText mEditText;
    Button mButton;
    ListView mSkuListView;

    protected SkuListAdapter mSkuListAdapter;
    protected List<String> mSkuList = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRootView();
        initTestButton();
        initTestTextView();
        initSkuListView();
        getProductData();
    }

    private void initRootView() {
        mRootLinearLayout = new LinearLayout(this);
        mRootLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mRootLinearLayout.setBackgroundColor(Color.parseColor("#333333"));
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(-1, -1);
        mRootLinearLayout.setLayoutParams(layoutParams);
        setContentView(mRootLinearLayout);
    }

    private void initTestButton() {
        mButton = new Button(this);
        mButton.setText("入库测试");
        mButton.setBackgroundColor(Color.WHITE);
        mButton.setTextColor(Color.BLACK);
        mButton.setTextSize(15f);
        mButton.setMinWidth(220);
        mButton.setMinHeight(110);
        mButton.setOnClickListener(this);
        mRootLinearLayout.addView(mButton);
    }

    private void initSkuListView() {
        mSkuListView = new ListView((Context) this);
        mSkuListAdapter = new SkuListAdapter((Context) this, mSkuList);
        mSkuListView.setAdapter(mSkuListAdapter);
        mSkuListView.setOnItemClickListener(this);
        mRootLinearLayout.addView(mSkuListView);
    }

    private void initTestTextView() {
        mEditText = new EditText(this);
        mEditText.setText("log信息");
        mEditText.setTextColor(Color.BLUE);
        mEditText.setTextSize(13f);
        mRootLinearLayout.addView(mEditText);
    }

    public void getProductData() {
        Bundle bundle = new Bundle();
        bundle.putString("packageName", getPackageName());
        Flowable.just(bundle).map(new Function<Bundle, Bundle>() {
            @Override
            public Bundle apply(Bundle bundle) throws Throwable {
                return ProviderBridge.httpGetSkuList((Context) BaseSkuActivity.this, bundle);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<Bundle>() {
                    @Override
                    public void onNext(Bundle bundle) {
                        mEditText.setText(bundle.getString("data"));
                    }

                    @Override
                    public void onError(Throwable t) {
                        mEditText.setText(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View view) {
//        for (int i = 0; i < 10; i++) {
//            mSkuList.add(i + "测试111");
//        }
//        mSkuListAdapter.setNewInstance(mSkuList);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText((Context) this, mSkuList.get(i), Toast.LENGTH_SHORT).show();
    }
}
