package org.techtown.samplekiosk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import org.techtown.samplekiosk.NormalActivity.NormalActivity;
import org.techtown.samplekiosk.OldActivity.OldActivity;

import java.util.HashMap;

public class LoopActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    

    private final int MODE_NORMAL = 0;
    private final int MODE_OLD = 1;
    private final int MODE_BLIND = 2;
    public int mode = -1;


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
                intent.putExtra("send", "101");
                startService(intent);
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
}