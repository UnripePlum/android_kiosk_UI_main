package org.techtown.samplekiosk;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

import java.util.Locale;
import static android.speech.tts.TextToSpeech.ERROR;

public class TextToSpeechService extends Service {
    TextToSpeech tts;
    boolean isNew = true;
    public TextToSpeechService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        Speech("");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null) return super.onStartCommand(intent, flags, startId);

        String str = intent.getExtras().getString("word");
        if(str == null) return super.onStartCommand(intent, flags, startId);


        Speech(str);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void Speech(String str){
        tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
    }
}