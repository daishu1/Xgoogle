package org.cn.google;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PayActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        TextView textView = findViewById(R.id.tv_empty);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                noticeGamePayCancel();
                sentPayResult();
            }
        });
    }



    private void sentPayResult() {
        Intent intent = new Intent();
        intent.putExtra("INAPP_PURCHASE_DATA", "{\"orderId\":\"GPA.3315-6563-0607-34401\",\"packageName\":\"com.igg.android.lordsmobile\",\"productId\":\"23438\",\"purchaseTime\":1616596172087,\"purchaseState\":0,\"purchaseToken\":\"hnlhndnbjpmaphckfgkipgie.AO-J1OxQ5o2fQvs5QfgtRP2ZPJ8vWupovB8LiAM91Mkas5nCiIl0SZZvjCyFkLa1RQOvof-5Ey8ZZE5bk1yNzB2afp6p6xG0TvyyNuvmarbUo0gB31pmij8\",\"acknowledged\":false}");
        intent.putExtra("INAPP_DATA_SIGNATURE", "eSK9jd/VrISZTu2U7UTCIafsli3Wym+qzO+9ORNT8SiQvaQpGITCtCn/qcIpLU7Wyl0uDxWrfqHB0ufb7WtX/pWYwss3JtGPPh9lnRFhb0Qkt+DaC08JQiz8s+Fs7Lt2FLRCIwwljMSy9MOz6AkkWb041gmfoTQCyUX7jNR20pGKFWX8iRqqZWVY238sfry1lvMSY0YYEIS1FXCAq17yUieIAs6RrqiWZZ83G/dj+bFsjdd/E9eggySeMmC7c363aVCr8MQ2hxJG5cYnWmlNa+vLjratYO4If+3BtCuZ589Wa41qSQy/fPLrTSV9t8Eg8l+qRC2B58jx36KfkyTZXg==");
        intent.putExtra("RESPONSE_CODE", 0);
        setResult(-1, intent);
        finish();
    }


    private void noticeGamePayCancel() {
        Intent intent = new Intent();
        intent.putExtra("RESPONSE_CODE", 1);
        intent.putExtra("DEBUG_MESSAGE", "用户取消");
        setResult(0, intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() != 4) {
            return super.onTouchEvent(event);
        }
        noticeGamePayCancel();
        return true;
    }

    @Override // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }
}
