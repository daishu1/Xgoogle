package org.cn.google.hook_import.view;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.cn.google.BillingClientManager;

public class BillingActivity extends Activity implements View.OnClickListener {

    LinearLayout mRootLinearLayout;
    EditText mEditText;

    static final String KEY_RESULT_RECEIVER = "result_receiver";
    private static final int REQUEST_CODE_LAUNCH_ACTIVITY = 100;
    private static final String TAG = "ProxyBillingActivity";
    private ResultReceiver mResultReceiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRootView();
        initTestButton();
        initTestTextView();

        if (savedInstanceState == null) {
            handleEvent(getIntent());
            return;
        }
        this.mResultReceiver = (ResultReceiver) savedInstanceState.getParcelable(KEY_RESULT_RECEIVER);

    }

    @Override
    public void onClick(View view) {

    }

    private void initRootView() {
        mRootLinearLayout = new LinearLayout(this);
        mRootLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mRootLinearLayout.setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(-1, -1);
        mRootLinearLayout.setLayoutParams(layoutParams);
        setContentView(mRootLinearLayout);
    }

    private void initTestButton() {
        Button mButton = new Button(this);
        mButton.setText("入库测试");
        mButton.setBackgroundColor(Color.WHITE);
        mButton.setTextColor(Color.BLACK);
        mButton.setTextSize(15f);
        mButton.setMinWidth(220);
        mButton.setMinHeight(110);
        mButton.setOnClickListener(this);
        mRootLinearLayout.addView(mButton);
    }

    private void initTestTextView() {
        mEditText = new EditText(this);
        mEditText.setText("log信息");
        mEditText.setTextColor(Color.BLUE);
        mEditText.setTextSize(13f);
        mRootLinearLayout.addView(mEditText);
    }

    public void handleEvent(Intent intent) {
        PendingIntent pendingIntent;
        this.mResultReceiver = (ResultReceiver) intent.getParcelableExtra(KEY_RESULT_RECEIVER);
        if (intent.hasExtra("BUY_INTENT")) {
            pendingIntent = (PendingIntent) intent.getParcelableExtra("BUY_INTENT");
        } else {
            pendingIntent = intent.hasExtra("SUBS_MANAGEMENT_INTENT") ? (PendingIntent) intent.getParcelableExtra("SUBS_MANAGEMENT_INTENT") : null;
        }
        try {
            startIntentSenderForResult(pendingIntent.getIntentSender(), 100, new Intent(), 0, 0, 0);
        } catch (Exception e) {
            ResultReceiver resultReceiver = this.mResultReceiver;
            if (resultReceiver != null) {
                resultReceiver.send(6, null);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        ResultReceiver resultReceiver = this.mResultReceiver;
        if (resultReceiver != null) {
            outState.putParcelable(KEY_RESULT_RECEIVER, resultReceiver);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100) {
//            int responseCodeFromIntent = C0089BillingHelper.getResponseCodeFromIntent(intent, TAG);
//            if (!(resultCode == -1 && responseCodeFromIntent == 0)) {
//                C0089BillingHelper.logWarn(TAG, "Activity finished with resultCode " + i2 + " and billing's responseCode: " + responseCodeFromIntent);
//            }
//            this.mResultReceiver.send(responseCodeFromIntent, data == null ? null : data.getExtras());
//            return;
//        }
        this.mResultReceiver.send(resultCode, data == null ? null : data.getExtras());

        mEditText.setText(mEditText.getText() + "\nonActivityResult:" + requestCode + "," + resultCode);

    }
}
