package org.techtown.samplekiosk.OrderFragment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.samplekiosk.LoopActivity;
import org.techtown.samplekiosk.R;
import org.techtown.samplekiosk.TextToSpeechService;
import org.techtown.samplekiosk.Types.Board;
import org.techtown.samplekiosk.Types.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSheet extends AppCompatActivity {
    Intent intent;
    RecyclerView recyclerView;
    List<Board> board_dataList;
    OrderAdapter orderAdapter;
    Map<String, Board> board_data = new HashMap<>();
    Map<String, Integer> rule = new HashMap<>();
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    RadioGroup radioGroup3;
    LinearLayout step1;
    LinearLayout step2;
    LinearLayout step3;
    Boolean buttonCheckingFinished = false;
    int mode = -1;
    Handler handler = new Handler();
    Intent intentTTS;
    int pointer = 0;
    int stepPointer = 0;
    Button cancelButton2;
    Button extraOrderButton;
    boolean isDialog = false;

    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    RadioButton radioButton5;
    RadioButton radioButton6;
    RadioButton radioButton7;
    RadioButton radioButton8;

    private final int MODE_NORMAL = 0;
    private final int MODE_OLD = 1;
    private final int MODE_BLIND = 2;
    private final int MODE_WHEEL = 3;

    String[][] orderStr = {{"포장 1회 용기 제공", "매장 다회용기 제공"}, {"제휴사 할인", "엘포인트 적립 또는 사용", "선택 없음"}, {"신용카드 또는 체크카드", "모바일 또는 바코드 또는 페이류"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        mode = bundle.getInt("mode");
        board_dataList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, board_dataList, board_data, rule, mode);
        intentTTS = new Intent(this, TextToSpeechService.class);
        order(intent);

        Toast.makeText(getApplicationContext(), ""+mode, Toast.LENGTH_LONG).show();
        switch (mode){
            case MODE_NORMAL:
                setContentView(R.layout.activity_order_sheet);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                recyclerView = findViewById(R.id.recycleView2);

                recyclerView.setAdapter(orderAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                setCheckOrderSheet();
                setRadioButton();
                break;
            case MODE_OLD:
                setContentView(R.layout.activity_order_sheet_old);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                Popup();

                setRadioButton();
                break;

            case MODE_BLIND:
                setContentView(R.layout.activity_order_sheet_old);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                Popup();

                setRadioButton();
                break;
            case MODE_WHEEL:
                setContentView(R.layout.activity_order_sheet);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                recyclerView = findViewById(R.id.recycleView2);

                recyclerView.setAdapter(orderAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                setCheckOrderSheet();
                setRadioButton();
                break;
        }

        cancelButton2 = findViewById(R.id.cancelButton2);
        extraOrderButton = findViewById(R.id.extraOrderButton);

        cancelButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "취소되었습니다", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LoopActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        extraOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    public void order(Intent intent){
        if(intent == null) return;

        Bundle bundle = intent.getExtras();

        int size = bundle.getInt("getItemCount");

        for(int i = 0; i<size;i++){
            Data data = bundle.getParcelable("Order"+(i+1));
            if(data == null) return;
            orderAdapter.putInMap(data.name, Integer.toString(data.count), Integer.toString(data.cost), orderAdapter.getItemCount());


        }

    }
    private static void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }

    private void setRadioButton(){
        LinearLayout step1 = findViewById(R.id.step1);
        LinearLayout step2 = findViewById(R.id.step2);
        LinearLayout step3 = findViewById(R.id.step3);

        setViewAndChildrenEnabled(step2, false);
        setViewAndChildrenEnabled(step3, false);

        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);

        TextView stepTextView1 = findViewById(R.id.stepTextView1);
        TextView stepTextView2 = findViewById(R.id.stepTextView2);
        TextView stepTextView3 = findViewById(R.id.stepTextView3);
        stepTextView2.setBackgroundColor(Color.parseColor("#FFC6C6C6"));
        stepTextView3.setBackgroundColor(Color.parseColor("#FFC6C6C6"));



        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                stepTextView2.setBackgroundColor(Color.parseColor("#931010"));
                setViewAndChildrenEnabled(step2, true);
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                stepTextView3.setBackgroundColor(Color.parseColor("#931010"));
                setViewAndChildrenEnabled(step3, true);
            }
        });


        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(buttonCheckingFinished == false){
                    isDialog = true;
                    SampleDialogFragment sampleDialogFragment = new SampleDialogFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    sampleDialogFragment.show(manager, "1001");
                    buttonCheckingFinished = true;
                }

            }
        });


        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        radioButton6 = findViewById(R.id.radioButton6);
        radioButton7 = findViewById(R.id.radioButton7);
        radioButton8 = findViewById(R.id.radioButton8);

        RadioButton[] radioButtons = {radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8};

        for(int i=0; i<8; i++){
            radioButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(buttonCheckingFinished == true) {
                        isDialog = true;
                        SampleDialogFragment sampleDialogFragment = new SampleDialogFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        sampleDialogFragment.show(manager, "1001");
                    }
                }
            });
        }

    }

    private void setCheckOrderSheet(){
        TextView orderCost = findViewById(R.id.orderCost);
        TextView saleCost = findViewById(R.id.saleCost);
        TextView payCost = findViewById(R.id.payCost);
        Button totalCost = findViewById(R.id.totalCostButton);

        orderCost.setText(""+orderAdapter.totalcost);
        saleCost.setText("0");
        int payCostValue = orderAdapter.totalcost - Integer.parseInt(saleCost.getText().toString());
        payCost.setText(""+payCostValue);
        totalCost.setText(""+payCostValue);
    }
    private void Popup(){
        Intent popupOrderIntent = new Intent(getApplicationContext(), PopUpOrderActivity.class);

        String str1 = "총 주문 수량 ";
        String str2 = "";
        int totalCount = 0;
        int totalCost = 0;
        popupOrderIntent.putExtra("getItemCount", orderAdapter.getItemCount());
        popupOrderIntent.putExtra("mode", mode);
//        popupOrderIntent.putExtra("totalCost", totalCost);
//        popupOrderIntent.putExtra("saleCost", saleCost);
//        popupOrderIntent.putExtra("orderCost", orderCost);
//        popupOrderIntent.putExtra("payCost", payCost);

        for(int i = 0; i<orderAdapter.getItemCount();i++){
            Board buf = orderAdapter.getItem(i);
            str2 += (buf.getTitle() + " " + buf.getCount() + "개 " + buf.getCost() + "원\n");
            totalCount += Integer.parseInt(buf.getCount());
            totalCost += Integer.parseInt(buf.getCost());


            Data data = new Data(buf.getTitle(), Integer.parseInt(buf.getCount()), Integer.parseInt(buf.getCost()));
            popupOrderIntent.putExtra("Order"+(i+1), data);
        }
        str1 = str1 + Integer.toString(totalCount) + "개 \n 결제금액 " + Integer.toString(totalCost) + "원\n";
        str2 = str2 + "확인하셨다면 5번을 눌러주세요";
        if(mode == MODE_BLIND){
            intentTTS.putExtra("word", str1+str2);
            startService(intentTTS);
        }

        startActivity(popupOrderIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent == null) return;

        if(intent.getExtras().getBoolean("Return") == true){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent LoopIntent = new Intent(getApplicationContext(), LoopActivity.class);
                    LoopIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(LoopIntent);
                }
            } , 1000);
        }
        if(intent.getExtras().getBoolean("popFinish") == true){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    intentTTS.putExtra("word", "포장 1회 용기 제공");
                    startService(intentTTS);
                }
            }, 3000);
        }
        if(intent.getExtras().getString("keyPadValue") != null){
            if(intent.getExtras().getString("keyPadValue").length() == 1)
                isCursor(intent.getExtras().getString("keyPadValue"));
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void isCursor(String str){

        switch (str){

            case "4":
                if(pointer == 0) return;
                else pointer--;
                break;
            case "6":
                if(pointer == orderStr[stepPointer].length -1) return;
                else pointer++;

                break;

            case "5":
                if(stepPointer == 2) {
                    if(pointer == 0) radioButton6.setChecked(true);
                    else if(pointer == 1) radioButton7.setChecked(true);
                    else if(pointer == 2) radioButton8.setChecked(true);

                    if(buttonCheckingFinished == false){
                        isDialog = true;
                        SampleDialogFragment sampleDialogFragment = new SampleDialogFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        sampleDialogFragment.show(manager, "1001");
                        buttonCheckingFinished = true;
                    }
                }
                else if(isDialog){
                    Toast.makeText(this, "ok", Toast.LENGTH_LONG).show();
                    Intent LoopIntent = new Intent(this, OrderSheet.class);
                    LoopIntent.putExtra("Return", true);
                    LoopIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(LoopIntent);
                }
                else if(buttonCheckingFinished == true){
                    isDialog = true;
                    SampleDialogFragment sampleDialogFragment = new SampleDialogFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    sampleDialogFragment.show(manager, "1001");

                }
                else {
                    if(stepPointer == 0){
                        if(pointer == 0) radioButton1.setChecked(true);
                        else if(pointer == 1) radioButton2.setChecked(true);
                    }
                    else if(stepPointer == 1){
                        if(pointer == 0) radioButton3.setChecked(true);
                        else if(pointer == 1) radioButton4.setChecked(true);
                        else if(pointer == 2) radioButton5.setChecked(true);
                    }

                    pointer = 0;
                    stepPointer++;

                }
                return;
            case "0":
                if(stepPointer == 0) extraOrderButton.callOnClick();
                else {
                    pointer = 0;
                    stepPointer--;
                }
                break;
        }

        intentTTS.putExtra("word", orderStr[stepPointer][pointer]);
        startService(intentTTS);
    }
}