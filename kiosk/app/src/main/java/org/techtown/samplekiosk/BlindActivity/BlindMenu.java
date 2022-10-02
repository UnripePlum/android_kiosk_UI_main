package org.techtown.samplekiosk.BlindActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.techtown.samplekiosk.NormalActivity.Board;
import org.techtown.samplekiosk.NormalActivity.Cart;
import org.techtown.samplekiosk.NormalActivity.Data;
import org.techtown.samplekiosk.OldActivity.OldActivity;
import org.techtown.samplekiosk.R;
import org.techtown.samplekiosk.TextToSpeechService;


public class BlindMenu extends Fragment {

    Intent intentTTS;

    int NumButtonInPage = 8;
    int curPage = 1;
    int tab = 0;
    int index = NumButtonInPage * (curPage - 1);;
    int pages;
    int drawId;
    Button[] buttons = {null,null,null,null,null,null,null,null};
    String[] titles = null;
    int[] costs = null;
    TypedArray images = null;
    int pointer = 0;

    ViewGroup rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_menu_ham1, container, false);
        Resources res = getResources();

        setItem(tab, curPage, res);
        pointer = 0;
        intentTTS = new Intent(getActivity(), TextToSpeechService.class);

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
        if(titles.length % NumButtonInPage == 0) pages = titles.length / NumButtonInPage;
        else pages = titles.length / NumButtonInPage + 1;

        index = NumButtonInPage * (this.curPage - 1);


        index = NumButtonInPage * (this.curPage - 1);



        int[] buttonsId = {R.id.button, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7};
        for(int i=0;i<NumButtonInPage;i++){
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

                    BlindActivity blindActivity = (BlindActivity) getActivity();
                    Intent intent = new Intent(getActivity(), BlindActivity.class);

                    Data data = new Data(titles[id+index], 1, costs[id+index]);

                    intent.putExtra("button" + id,data);


                    blindActivity.makeToast(intent);
                }
            });
            


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void setPointer(String where){

        Cart cart = ((BlindActivity) getActivity()).cart;

        switch (where){
            case "left":
                if(pointer == 0 || pointer == NumButtonInPage) {
                    BlindActivity blindActivity = (BlindActivity) getActivity();
                    blindActivity.onButtonBackClicked();
                    pointer = 0;
                }
                else pointer--;
                break;
            case "right":
                if(pointer == NumButtonInPage-1 || pointer == NumButtonInPage+1) {
                    BlindActivity blindActivity = (BlindActivity) getActivity();
                    blindActivity.onButtonNextClicked();
                    pointer = 0;
                }
                else pointer++;
                break;
            case "up":
                if(pointer == 0 | pointer == 1) return;
                else pointer -= 2;
                break;
            case "down":
                if(pointer == NumButtonInPage | pointer == NumButtonInPage+1) {
                    return;
                }
                else pointer += 2;
                break;
            case "confirm":
                if(pointer < NumButtonInPage) buttons[pointer].callOnClick();
                else if(pointer >= NumButtonInPage) {
                    cart.buttons[pointer-NumButtonInPage].callOnClick();
                }
                return;
            case "delete":
                BlindActivity blindActivity = (BlindActivity) getActivity();
                for(int i = 0; i < cart.boardAdapter.datas.size(); i++){
                    Board data = cart.boardAdapter.datas.get(i);
                    if(data.getTitle() == titles[index + pointer]){
                        cart.boardAdapter.putOutMap(i);
                        intentTTS.putExtra("word", titles[index + pointer] + "가 주문 목록에서 삭제되었습니다");
                        getActivity().startService(intentTTS);
                    }
                }
                return;
        }
        for(int i=0; i<NumButtonInPage; i++){
            buttons[pointer].setBackgroundColor(00000000);
        }

        if(pointer >= NumButtonInPage){
            intentTTS.putExtra("word", cart.buttons[pointer-NumButtonInPage].getText());
            getActivity().startService(intentTTS);
            cart.buttons[pointer-NumButtonInPage].setBackgroundColor(000000);
            return;

        }

        intentTTS.putExtra("word", (pointer+1) + " " + buttons[pointer].getText());
        getActivity().startService(intentTTS);
        buttons[pointer].setBackgroundColor(000000);
    }




}