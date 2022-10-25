package org.techtown.samplekiosk.NormalActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.tabs.TabLayout;

import org.techtown.samplekiosk.Types.Cart;
import org.techtown.samplekiosk.Types.Data;
import org.techtown.samplekiosk.NormalActivity.Menu.MenuHam1;
import org.techtown.samplekiosk.NormalActivity.Menu.MenuHam2;
import org.techtown.samplekiosk.NormalActivity.Menu.MenuReco1;
import org.techtown.samplekiosk.NormalActivity.Menu.Menudess1;
import org.techtown.samplekiosk.NormalActivity.Menu.Menudess2;
import org.techtown.samplekiosk.NormalActivity.Menu.Menudess3;
import org.techtown.samplekiosk.NormalActivity.Menu.Menudess4;
import org.techtown.samplekiosk.NormalActivity.Menu.Menudrink1;
import org.techtown.samplekiosk.NormalActivity.Menu.Menudrink2;
import org.techtown.samplekiosk.R;

public class NormalActivity extends AppCompatActivity {
    Cart cart = new Cart();
    ViewPager2 pager;
    TabLayout tabs;
    ScreenSlidePagerAdapter adapter;
    RadioGroup radioPage;

    //
    MenuReco1 menuReco1 = new MenuReco1();
    MenuHam1 menuHam1 = new MenuHam1();
    MenuHam2 menuHam2 = new MenuHam2();
    Menudess1 menudess1 = new Menudess1();
    Menudess2 menudess2 = new Menudess2();
    Menudess3 menudess3 = new Menudess3();
    Menudess4 menudess4 = new Menudess4();
    Menudrink1 menudrink1 = new Menudrink1();
    Menudrink2 menudrink2 = new Menudrink2();

    int curtab = 0;
    int NUM_PAGES = 0;
    Fragment[][] list = {{menuReco1},{menuHam1, menuHam2},{menudess1, menudess2, menudess3, menudess4},{menudrink1, menudrink2}};

    String[] tabtitles = {"추천메뉴", "햄버거", "디저트/치킨", "음료/커피"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        adapter = new ScreenSlidePagerAdapter(this);
        tabs = findViewById(R.id.tabs);
        pager.setAdapter(adapter);
        radioPage = findViewById(R.id.radioPage);

        //Normal화면상의 cartlist를 cart fragment로 Transaction
        cart = new Cart();
        getSupportFragmentManager().beginTransaction().replace(R.id.cartlist, cart).commit();

        MakeRadioPage();

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
                MakeRadioPage();
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

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                radioPage.check(radioPage.getChildAt(position).getId());
            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return list[curtab][position];
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES = list[curtab].length;
        }
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

    private void onButtonBackClicked(){
        if(pager.getCurrentItem() == 0) {
            return;
        }
        else if(pager.getCurrentItem() > 0){
            pager.setCurrentItem(pager.getCurrentItem()-1);
            return;
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

    public void onDeleteButtonClicked(){
    }

    private void MakeRadioPage(){
        radioPage.removeAllViews();
        for(int i=0;i<NUM_PAGES;i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(null);
            radioPage.addView(radioButton);
        }
        radioPage.check(radioPage.getChildAt(0).getId());
    }

}