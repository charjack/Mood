package com.charjack.mood;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.charjack.mood.utils.FragmentTabUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements FragmentTabUtils.OnRgsExtraCheckedChangedListener{

    private RadioGroup rgs;
    private RadioButton radio_discovery,radio_fav,radio_setting;
    private List<Fragment> fragments = new ArrayList<>();
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rgs = (RadioGroup) findViewById(R.id.radio_group);
        radio_discovery = (RadioButton) findViewById(R.id.radio_discovery);
        radio_fav = (RadioButton) findViewById(R.id.radio_fav);
        radio_setting = (RadioButton) findViewById(R.id.radio_setting);

        fragments.add(new DiscoveryListFragment());
        fragments.add(new FavorityFragment());
        fragments.add(new SettingFragment());
        FragmentTabUtils utils = new FragmentTabUtils(getSupportFragmentManager(), fragments, R.id.fragment_main_container, rgs);
        utils.setOnRgsExtraCheckedChangedListener(this);
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
//            System.exit(0);
        }
    }

    @Override
    public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
        //可以改变底部的图片
        System.out.println("checkedid:"+checkedId);//系统分配的一个id值
        System.out.println("index:"+index);  //0--3
        System.out.println("checkedid:" + rgs.getChildAt(index).getId());
//        switch(index){
//            case 0:
//                radio_discovery.draw(R.mipmap.discovery);
//                break;
//        }
    }
}
