package org.techtown.samplekiosk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {
    // 해당 어댑터의 ViewHolder를 상속받는다.
    private List<Board> datas;
    private Context context;
    Map<String, Board> dict;
    Map<String, Integer> rule;
    Cart cart;
    int totalcost = 0;
    int totalcount = 0;
    private int buttonPosition;


    public BoardAdapter(Context context, List<Board> datas, Map<String, Board> dict, Map<String, Integer> rule, Cart cart) {
        this.context = context;
        this.datas = datas;
        this.dict = dict;
        this.rule = rule;
        this.cart = cart;
    }

    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewHodler 객체를 생성 후 리턴한다.

        return new BoardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleframe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        // ViewHolder 가 재활용 될 때 사용되는 메소드
        Board data = datas.get(position);
        holder.title.setText(data.getTitle());
        holder.count.setText(data.getCount());
        holder.cost.setText(data.getCost());


    }

    public Board getItem(int position){
        Board data = datas.get(position);
        return data;

    }

    @Override
    public int getItemCount() {
        return datas.size(); // 전체 데이터의 개수 조회
    }


    // 아이템 뷰를 저장하는 클래스
    public class BoardViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder 에 필요한 데이터들을 적음.
        private TextView title;
        private TextView count;
        private TextView cost;
        Button deleteButton;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            // 아이템 뷰에 필요한 View
            title = itemView.findViewById(R.id.board_title);
            count = itemView.findViewById(R.id.board_count);
            cost = itemView.findViewById(R.id.board_cost);
            deleteButton = itemView.findViewById(R.id.deletebutton);


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final int adapterPosition = getAdapterPosition();
                    putOutMap(adapterPosition);
                    cart.msetText(totalcount, totalcost);

                }

            });


        }
    }
    public void putInMap(String title, String count, String cost, int adapterPosition){
        Board buf = new Board();
        buf.setTitle(title);
        buf.setCount(count);
        buf.setCost1(cost);

        if(dict.get(title) != null){


            int newCount = Integer.parseInt(dict.get(title).getCount())+1;
            int newCost = Integer.parseInt(dict.get(title).getCost());
            dict.remove(title);

            buf.setCount(Integer.toString(newCount));
            buf.setCost1(Integer.toString(newCost/(newCount-1) * newCount));
            dict.put(title, buf);
            datas.set(rule.get(title), buf);
            notifyItemChanged(rule.get(title), buf);

        }
        else{

            rule.put(title, adapterPosition);
            dict.put(title, buf);
            datas.add(buf);
            notifyItemInserted(adapterPosition);
            adapterPosition++;

        }

        calculate();

        return;


    }

    public void putOutMap(int adapterPostion){
        dict.remove(datas.get(adapterPostion).getTitle());
        rule.remove(datas.get(adapterPostion).getTitle());
        datas.remove(adapterPostion);
        notifyItemRemoved(adapterPostion);

        calculate();

    }
    public void calculate(){
        int totalcountBuf = 0;
        int totalcostBuf = 0;
        for(int i = 0; i<getItemCount();i++){
            totalcountBuf = totalcountBuf + Integer.parseInt(getItem(i).getCount());
            totalcostBuf = totalcostBuf + Integer.parseInt(getItem(i).getCost());
        }
        totalcount = totalcountBuf;
        totalcost = totalcostBuf;
    }

}
