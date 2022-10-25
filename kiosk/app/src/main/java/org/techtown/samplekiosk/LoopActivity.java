package org.techtown.samplekiosk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import org.techtown.samplekiosk.BlindActivity.BlindActivity;
import org.techtown.samplekiosk.NormalActivity.NormalActivity;
import org.techtown.samplekiosk.OldActivity.OldActivity;

import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;


public class LoopActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    
    private TextToSpeech tts;
    private final int MODE_NORMAL = 0;
    private final int MODE_OLD = 1;
    private final int MODE_BLIND = 2;
    private final int MODE_WHEEL = 3;
    public int mode = -1;
    private boolean isNext = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new DevicesFragment(), "devices").commit();
        else
            onBackStackChanged();

        Button sendbtn = findViewById(R.id.sendbtn);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UsbService.class);
                intent.putExtra("send", "1");
                startService(intent);
            }
        });

        Button button_normal = findViewById(R.id.button_normal);
        button_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuStart(MODE_NORMAL);
            }
        });
        Button button_old = findViewById(R.id.button_old);
        button_old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuStart(MODE_OLD);
            }
        });
        Button button_blind = findViewById(R.id.button_blind);
        button_blind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuStart(MODE_BLIND);
            }
        });
        Button button_wheel = findViewById(R.id.button_wheel);
        button_wheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuStart(MODE_WHEEL);
            }
        });

        // TTS 설정
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
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
                intent = new Intent(getApplicationContext(), BlindActivity.class);
                break;
            case MODE_WHEEL:
                intent = new Intent(getApplicationContext(), NormalActivity.class);
                Intent intentService = new Intent(getApplicationContext(), UsbService.class);
                intentService.putExtra("send", "130");
                startService(intentService);
                break;
        }
        startActivity(intent);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        if("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(intent.getAction())) {
//            TerminalFragment terminal = (TerminalFragment)getSupportFragmentManager().findFragmentByTag("terminal");
//            if (terminal != null)
//                terminal.status("USB device detected");
        }
        super.onNewIntent(intent);
    }


    @Override
    public void onBackStackChanged() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount()>0);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initSpeech(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isNext) return;
                tts.speak("화면 앞에 서주세요", TextToSpeech.QUEUE_FLUSH, null);
                handler.postDelayed(this, 10000);

            }
        }, 10000);


    }

    @Override
    protected void onStop() {
        super.onStop();
        isNext = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initSpeech();
    }
}