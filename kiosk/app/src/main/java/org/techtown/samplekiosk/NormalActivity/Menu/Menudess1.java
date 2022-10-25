package org.techtown.samplekiosk.NormalActivity.Menu;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import org.techtown.samplekiosk.Types.Data;
import org.techtown.samplekiosk.NormalActivity.NormalActivity;
import org.techtown.samplekiosk.R;


public class Menudess1 extends Fragment {


    int NumButtonInPage = 8;
    int curPage = 1;
    int index = NumButtonInPage * (curPage - 1);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_dess1, container, false);
        Button[] buttons = {null,null,null,null,null,null,null,null};
        Resources res = getResources();
        String[] titles = res.getStringArray(R.array.desserttitles);
        int[] costs = res.getIntArray(R.array.dessertcosts);
        TypedArray images = res.obtainTypedArray(R.array.dessertimages);

        int[] buttonsId = {R.id.button, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7};
        for(int i=0;i<=7;i++){
            buttons[i] = rootView.findViewById(buttonsId[i]);
            if(buttons[i] == null) continue;
            if(i+index >= titles.length) continue;

            // button 설정 과정
            int draw = images.getResourceId(i+index, R.array.dessertimages);
            buttons[i].setCompoundDrawablesWithIntrinsicBounds(draw, 0,0,0);
            buttons[i].setText(titles[i+index] + "\n" + costs[i+index]);

            final int id = i;

            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    NormalActivity normalActivity = (NormalActivity) getActivity();
                    Intent intent = new Intent(getActivity(), NormalActivity.class);

                    Data data = new Data(titles[id+index], 1, costs[id+index]);

                    intent.putExtra("button" + id,data);

                    normalActivity.makeToast(intent);

                }
            });


        }
        return rootView;

    }
}