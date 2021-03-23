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
                noticeGamePayCancel();
            }
        });
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
