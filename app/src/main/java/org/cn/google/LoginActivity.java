package org.cn.google;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;

import org.cn.google.app.AppConstance;
import org.cn.google.common.ProtoDialog;
import org.cn.google.mode.BaseResponse;
import org.cn.google.mode.LoginResponse;
import org.cn.google.mode.SkuDetailsModel;
import org.cn.google.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText edLoginNickname, edLoginNickPwd;
    private Button btnLoginNickConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        edLoginNickname = findViewById(R.id.edLoginNickname);
        edLoginNickPwd = findViewById(R.id.edLoginNickPwd);
        btnLoginNickConfirm = findViewById(R.id.btnLoginNickConfirm);

        edLoginNickname.addTextChangedListener(this);
        edLoginNickPwd.addTextChangedListener(this);

        btnLoginNickConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response = OkGo.post("http://games.usbuydo.com//api/games/price")
//                            .params("packageName", "com.igg.android.lordsmobile")
//                            .execute();
//                    if (response.code() != 200)
//                        throw new Exception(response.message() + response.code());
////                    String body = response.body().string();
//                    BaseResponse baseResponse = GsonUtils.fromJson(response.body().string(), BaseResponse.class);
//                    if (baseResponse.getCode() != 1)
//                        throw new Exception(baseResponse.getMsg());
//                    List<SkuDetailsModel> skuDetailsModels = JsonUtils.stringToList(baseResponse.getData(), SkuDetailsModel.class);
//                    LogUtils.e(JsonUtils.objectToString(skuDetailsModels));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    LogUtils.e(e.getMessage());
//                }
//            }
//        }).start();

        Observable.combineLatest(Observable.just(edLoginNickname.getText().toString()), Observable.just(edLoginNickPwd.getText().toString()), (s, s2) -> {
            Response response = OkGo.post("http://games.usbuydo.com/api/login/login")
                    .params("username", s)
                    .params("password", s2)
                    .params("device_id", "1234567890")
                    .execute();
            if (response.code() != 200) {
                throw new Exception(response.message());
            }
            return GsonUtils.fromJson(response.body().string(), BaseResponse.class);
        })
                .map(new Function<BaseResponse, LoginResponse.UserInfo>() {
                    @Override
                    public LoginResponse.UserInfo apply(BaseResponse baseResponse) throws Throwable {
                        if (baseResponse.getCode() != 200) {
                            throw new Exception(baseResponse.getMsg());
                        }
                        return JsonUtils.objectToClass(baseResponse.getData(), LoginResponse.class).getUserinfo();
                    }
                })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse.UserInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        ProtoDialog.showLoadingDialog(LoginActivity.this);
                    }

                    @Override
                    public void onNext(LoginResponse.@NonNull UserInfo userInfo) {
                        SPStaticUtils.put(AppConstance.KEY_USER_INFO, GsonUtils.toJson(userInfo));
                        Intent intent = new Intent();
                        intent.setClass((Context) LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ProtoDialog.dismissLoading();
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        ProtoDialog.dismissLoading();
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        btnLoginNickConfirm.setEnabled(!TextUtils.isEmpty(edLoginNickname.getText()) && !TextUtils.isEmpty(edLoginNickPwd.getText()));
    }
}
