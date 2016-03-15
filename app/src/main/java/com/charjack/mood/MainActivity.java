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

import com.charjack.mood.adapter.DiscoveryAdapter;
import com.charjack.mood.utils.FragmentTabUtils;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class MainActivity extends AppCompatActivity implements FragmentTabUtils.OnRgsExtraCheckedChangedListener,DiscoveryAdapter.OnDicoveryAdapterListener{

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



    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.ssdk_oks_share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    public void Onsharelistener() {
        showShare();
    }
}
