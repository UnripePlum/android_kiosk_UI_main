package org.techtown.samplekiosk.NormalActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sheet);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView = findViewById(R.id.recycleView2);
        board_dataList = new ArrayList<>();

        orderAdapter = new OrderAdapter(this, board_dataList, board_data, rule);
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        intent = getIntent();
        order(intent);

        TextView orderCost = findViewById(R.id.orderCost);
        TextView saleCost = findViewById(R.id.saleCost);
        TextView payCost = findViewById(R.id.payCost);
        Button totalCost = findViewById(R.id.totalCostButton);

        orderCost.setText(""+orderAdapter.totalcost);
        saleCost.setText("0");
        int payCostValue = orderAdapter.totalcost - Integer.parseInt(saleCost.getText().toString());
        payCost.setText(""+payCostValue);
        totalCost.setText(""+payCostValue);

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
                    SampleDialogFragment sampleDialogFragment = new SampleDialogFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    sampleDialogFragment.show(manager, "1001");
                    buttonCheckingFinished = true;
                }

            }
        });


        RadioButton radioButton1 = findViewById(R.id.radioButton1);
        RadioButton radioButton2 = findViewById(R.id.radioButton2);
        RadioButton radioButton3 = findViewById(R.id.radioButton3);
        RadioButton radioButton4 = findViewById(R.id.radioButton4);
        RadioButton radioButton5 = findViewById(R.id.radioButton5);
        RadioButton radioButton6 = findViewById(R.id.radioButton6);
        RadioButton radioButton7 = findViewById(R.id.radioButton7);
        RadioButton radioButton8 = findViewById(R.id.radioButton8);

        RadioButton[] radioButtons = {radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8};

        for(int i=0; i<8; i++){
            radioButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(buttonCheckingFinished == true) {
                        SampleDialogFragment sampleDialogFragment = new SampleDialogFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        sampleDialogFragment.show(manager, "1001");
                    }
                }
            });
        }




        Button cancelButton2 = findViewById(R.id.cancelButton2);
        Button extraOrderButton = findViewById(R.id.extraOrderButton);

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






}