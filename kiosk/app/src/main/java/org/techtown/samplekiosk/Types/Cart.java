package org.techtown.samplekiosk.Types;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.samplekiosk.BlindActivity.BlindActivity;
import org.techtown.samplekiosk.LoopActivity;
import org.techtown.samplekiosk.NormalActivity.NormalActivity;
import org.techtown.samplekiosk.OldActivity.OldActivity;
import org.techtown.samplekiosk.OrderFragment.OrderSheet;
import org.techtown.samplekiosk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cart extends Fragment {
    public static final int REQUEST_CODE_MENU = 101;
    RecyclerView recyclerView;
    List<Board> board_dataList;
    public BoardAdapter boardAdapter;
    Map<String, Board> board_data = new HashMap<>();
    Map<String, Integer> rule = new HashMap<>();
    TextView totalCount;
    TextView totalCost;
    public Button[] buttons = {null, null};
    int size = 0;
    int mode = -1;

    private final int MODE_NORMAL = 0;
    private final int MODE_OLD = 1;
    private final int MODE_BLIND = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = null;
        // Inflate the layout for this fragment
        if(getActivity().getClass() == NormalActivity.class){
            mode = MODE_NORMAL;
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cart, container, false);
        }
        else if(getActivity().getClass() == OldActivity.class){
            mode = MODE_OLD;
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cart_old, container, false);
        }
        else if(getActivity().getClass() == BlindActivity.class){
            mode = MODE_BLIND;
            rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cart, container, false);
        }

        totalCount = rootView.findViewById(R.id.totalCount);
        totalCost = rootView.findViewById(R.id.totalcost);
        Button cancelButton = rootView.findViewById(R.id.cancelButton);
        Button payButton = rootView.findViewById(R.id.payButton);
        buttons[0] = cancelButton;
        buttons[1] = payButton;

        recyclerView = rootView.findViewById(R.id.recyclerView);
        board_dataList = new ArrayList<>();

        boardAdapter = new BoardAdapter(getActivity(), board_dataList, board_data, rule, this, mode);

        recyclerView.setAdapter(boardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "취소되었습니다", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), LoopActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String orderData = "";
                Intent intent = new Intent(getActivity(), OrderSheet.class);

                intent.putExtra("mode", mode);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                if(boardAdapter.getItemCount() == 0) return;

                intent.putExtra("getItemCount", boardAdapter.getItemCount());
                for(int i = 0; i<boardAdapter.getItemCount();i++){
                    Board buf = boardAdapter.getItem(i);
                    //
                    Data data = new Data(buf.getTitle(), Integer.parseInt(buf.getCount()), Integer.parseInt(buf.getCost()));
                    intent.putExtra("Order"+(i+1), data);
                    orderData = orderData +  "Order " + Integer.toString(i+1) + " : { " + "Title : " + buf.getTitle() + ", Count : " + buf.getCount() + ", Cost : " + buf.getCost() + " }\n";
                }
                orderData = orderData + "TotalCount = " + boardAdapter.totalcount + ", TotalCost = " + boardAdapter.totalcost;

                Toast.makeText(getActivity(), orderData, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        return rootView;
    }

    public void order(Intent intent){
        if(intent == null) return;

        Bundle bundle = intent.getExtras();

        Data data = bundle.getParcelable("order");
        if(data == null) return;

        boardAdapter.putInMap(data.name, Integer.toString(data.count), Integer.toString(data.cost), boardAdapter.getItemCount());
        msetText(boardAdapter.totalcount, boardAdapter.totalcost);
        recyclerView.scrollToPosition(boardAdapter.getItemCount()-1);
    }

    public void msetText(int totalCount, int totalCost){
        this.totalCost.setText(""+totalCost);
        this.totalCount.setText(""+totalCount);
    }

}

