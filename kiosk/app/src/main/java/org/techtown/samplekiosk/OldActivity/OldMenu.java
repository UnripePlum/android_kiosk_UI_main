package org.techtown.samplekiosk.OldActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import org.techtown.samplekiosk.NormalActivity.Data;
import org.techtown.samplekiosk.R;


public class OldMenu extends Fragment {

    int NumButtonInPage = 4;
    int curPage = 1;
    int tab = 0;
    int index = NumButtonInPage * (curPage - 1);;
    int pages;
    int drawId;
    Button[] buttons = {null,null,null,null,null,null,null,null};
    String[] titles = null;
    int[] costs = null;
    TypedArray images = null;

    ViewGroup rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_old, container, false);
        Resources res = getResources();

        setItem(tab, curPage, res);






        return rootView;

    }
    public void setItem(int tab, int curPage, Resources res){

        this.tab = tab;



        switch (tab) {
            case 0:
                titles = res.getStringArray(R.array.burgertitles);
                costs = res.getIntArray(R.array.burgercosts);
                images = res.obtainTypedArray(R.array.burgerimages);
                drawId = res.getIdentifier("burgerimages", "id", getActivity().getPackageName());

                break;
            case 1:
                titles = res.getStringArray(R.array.burgertitles);
                costs = res.getIntArray(R.array.burgercosts);
                images = res.obtainTypedArray(R.array.burgerimages);
                drawId = res.getIdentifier("burgerimages", "id", getActivity().getPackageName());

                break;
            case 2:
                titles = res.getStringArray(R.array.desserttitles);
                costs = res.getIntArray(R.array.dessertcosts);
                images = res.obtainTypedArray(R.array.dessertimages);
                drawId = res.getIdentifier("dessertimages", "id", getActivity().getPackageName());

                break;
            case 3:
                titles = res.getStringArray(R.array.drinktitles);
                costs = res.getIntArray(R.array.drinkcosts);
                images = res.obtainTypedArray(R.array.drinkimages);
                drawId = res.getIdentifier("drinkimages", "id", getActivity().getPackageName());

                break;
        }
        this.curPage = curPage;
        if(titles.length % 4 == 0) pages = titles.length / NumButtonInPage;
        else pages = titles.length / NumButtonInPage + 1;

        index = NumButtonInPage * (this.curPage - 1);


        index = NumButtonInPage * (this.curPage - 1);



        int[] buttonsId = {R.id.button, R.id.button1, R.id.button2, R.id.button3};
        for(int i=0;i<buttonsId.length;i++){
            buttons[i] = rootView.findViewById(buttonsId[i]);
            if(buttons[i] == null) continue;
            if(i+index >= titles.length) continue;

            // button 설정 과정
            int draw = images.getResourceId(i, drawId);
            buttons[i].setCompoundDrawablesWithIntrinsicBounds(draw, 0,0,0);
            buttons[i].setText(titles[i+index] + "\n" + costs[i+index]);

            final int id = i;

            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    OldActivity oldActivity = (OldActivity) getActivity();
                    Intent intent = new Intent(getActivity(), OldActivity.class);

                    Data data = new Data(titles[id+index], 1, costs[id+index]);

                    intent.putExtra("button" + id,data);


                    oldActivity.makeToast(intent);

                }
            });


        }
    }
}