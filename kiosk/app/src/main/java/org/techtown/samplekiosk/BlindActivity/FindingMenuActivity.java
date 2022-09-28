package org.techtown.samplekiosk.BlindActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.techtown.samplekiosk.R;

public class FindingMenuActivity extends AppCompatActivity {

    Button button_L;
    Button button_R;
    int button_step = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_menu);

        button_L = findViewById(R.id.button_L);
        button_R = findViewById(R.id.button_R);
        button_L.setText("햄버거 포함");
        button_R.setText("햄버거 미포함");

        button_L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (button_step) {
                    case 0:
                        button_L.setText("단품");
                        button_R.setText("세트");

                        button_step++;
                        break;
                    case 1:
                        break;
                    case 2:
                        break:
                    case 3:
                        break;
                }
            }
        });
        button_R.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (button_step) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break:
                    case 3:
                        break;
                }
            }
        });
    }
}