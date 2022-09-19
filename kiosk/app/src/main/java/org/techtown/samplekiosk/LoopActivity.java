package org.techtown.samplekiosk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;

import org.techtown.samplekiosk.NormalActivity.NormalActivity;
import org.techtown.samplekiosk.OldActivity.OldActivity;

public class LoopActivity extends AppCompatActivity {

    private final int MODE_NORMAL = 0;
    private final int MODE_OLD = 1;
    private final int MODE_BLIND = 2;
    public int mode = MODE_OLD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //손가락으로 화면을 누르기 시작했을 때 할 일
                Intent intent = null;
                switch (mode){
                    case MODE_NORMAL:
                        intent = new Intent(getApplicationContext(), NormalActivity.class);
                        break;
                    case MODE_OLD:
                        intent = new Intent(getApplicationContext(), OldActivity.class);
                        break;
                    case MODE_BLIND:
                        intent = new Intent(getApplicationContext(), OldActivity.class);
                        break;
                }

                startActivity(intent);
                break;
            case MotionEvent.ACTION_MOVE:
                //터치 후 손가락을 움직일 때 할 일
                break;
            case MotionEvent.ACTION_UP:
                //손가락을 화면에서 뗄 때 할 일
                break;
            case MotionEvent.ACTION_CANCEL:
                // 터치가 취소될 때 할 일
                break;
            default:
                break;
        }
        return true;
    }


}