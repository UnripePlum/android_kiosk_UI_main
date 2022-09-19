package org.techtown.samplekiosk.OldActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import org.techtown.samplekiosk.NormalActivity.Cart;
import org.techtown.samplekiosk.NormalActivity.Data;
import org.techtown.samplekiosk.R;

public class OldActivity extends AppCompatActivity {
    Cart cart;
    ViewPager2 pager;
    TabLayout tabs;
    ScreenSlidePagerAdapter adapter;




    int curtab = 0;
    int NUM_PAGES = 1;



    String[] tabtitles = {"추천메뉴", "햄버거", "디저트/치킨", "음료/커피"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_old);


        pager = findViewById(R.id.pager);
        adapter = new ScreenSlidePagerAdapter(this);
        tabs = findViewById(R.id.tabs);
        pager.setAdapter(adapter);




        cart = new Cart();
        getSupportFragmentManager().beginTransaction().replace(R.id.cartlist, cart).commit();



        tabs.addTab(tabs.newTab().setText(tabtitles[0]));
        tabs.addTab(tabs.newTab().setText(tabtitles[1]));
        tabs.addTab(tabs.newTab().setText(tabtitles[2]));
        tabs.addTab(tabs.newTab().setText(tabtitles[3]));
        /*
        TabLayout mediator 해야함
        new TabLayoutMediator(tabs, pager, (tab, position) -> tab.setText(tabtitles[position])).attach();
        */
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                curtab = tab.getPosition();
                pager.setAdapter(adapter);
                switch (curtab){
                    case 0:
                        NUM_PAGES = 1;
                        break;
                    case 1:
                        NUM_PAGES = 4;
                        break;
                    case 2:
                        NUM_PAGES = 8;
                        break;
                    case 3:
                        NUM_PAGES = 4;
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonNext = findViewById(R.id.buttonNext);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonBackClicked();
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonNextClicked();
            }
        });







    }

    public void makeToast(Intent intent){
        if(intent == null) return;

        Bundle bundle = intent.getExtras();
        for(int i = 0; i<=7;i++){

            Data data = bundle.getParcelable("button"+i);
            if(data == null) continue;

            Intent intent1 = new Intent(getApplicationContext(), Cart.class);
            intent1.putExtra("order", data);
            cart.order(intent1);

        }

    }

    private void onButtonNextClicked(){

        if(pager.getCurrentItem() == NUM_PAGES-1) {

            return;
        }
        else if(pager.getCurrentItem() < NUM_PAGES-1){
            pager.setCurrentItem(pager.getCurrentItem()+1);
            return;
        }

    }

    private void onButtonBackClicked(){
        if(pager.getCurrentItem() == 0) {
            return;
        }
        else if(pager.getCurrentItem() > 0){
            pager.setCurrentItem(pager.getCurrentItem()-1);
            return;
        }
    }
    public void onDeleteButtonClicked(){


    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {



        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            OldMenu newMenuHam = new OldMenu();
            newMenuHam.tab = curtab;
            newMenuHam.curPage = position+1;

            return newMenuHam;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }


}