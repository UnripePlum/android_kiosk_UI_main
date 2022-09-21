package org.techtown.samplekiosk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import org.techtown.samplekiosk.NormalActivity.NormalActivity;
import org.techtown.samplekiosk.OldActivity.OldActivity;

public class LoopActivity extends AppCompatActivity {

    private final int MODE_NORMAL = 0;
    private final int MODE_OLD = 1;
    private final int MODE_BLIND = 2;
    public int mode = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button buttonMODE_NORMAL = findViewById(R.id.buttonMODE_NORMAL);
        Button buttonMODE_OLD = findViewById(R.id.buttonMODE_OLD);

        buttonMODE_NORMAL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuStart(MODE_NORMAL);
            }
        });

        buttonMODE_OLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuStart(MODE_OLD);
            }
        });


    }

    private void MenuStart(int mode){
        Intent intent = null;
        this.mode = mode;
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
    }





}