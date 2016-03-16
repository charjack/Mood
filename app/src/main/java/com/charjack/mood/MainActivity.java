package com.charjack.mood;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.charjack.mood.adapter.DiscoveryAdapter;
import com.charjack.mood.utils.FragmentTabUtils;
import com.charjack.mood.utils.SystemBarTintManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/*
* 2016.3.16 结束
* 不足 处：
* 1、不应该用三个fragment来做切换的，而是直接用三个activity来进行
* 2、没有加入pulltorefresh，因为之前用了list来做上下拉的动作
* 3、没有加入toolbar进行标题栏的操作，导致后面的状态栏调节有点问题。
* 4、图片的加载还不是很流畅。
* 5、还没有加入完成sharesdk的sso认证
* 6、没有完成多米音乐的链接下载功能，这应该是一个webview的功能
*/

/*
*做开发时。1：定要先定义好布局，
*       2、确定并熟悉所用的所有第三方库，以免出现架构的错误，就想pulltorefresh这样的错误，不能犯。（这里只是把库集成进来了，暂时不进行修改了）
*       3、确认架构正确再进行开发
*/

public class MainActivity extends AppCompatActivity implements FragmentTabUtils.OnRgsExtraCheckedChangedListener,DiscoveryAdapter.OnDicoveryAdapterListener{

    private RadioGroup rgs;
    private RadioButton radio_discovery,radio_fav,radio_setting;
    private List<Fragment> fragments = new ArrayList<>();
    private long exitTime = 0;
    SlidingMenu menu;
    public SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);

        //创建侧滑菜单
        //加入这个slidingmenu后，状态栏悬浮在slidingmenu这个布局上了，
        menu  = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.55f);
        menu.setMenu(R.layout.sliding_menu_layout);
        menu.setBackgroundColor(Color.WHITE);
        menu.setBehindScrollScale(0.0f);
        menu.setBehindOffsetRes(R.dimen.menu_offset);//设置相对屏幕的偏移量
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        rgs = (RadioGroup) findViewById(R.id.radio_group);
        radio_discovery = (RadioButton) findViewById(R.id.radio_discovery);
        radio_fav = (RadioButton) findViewById(R.id.radio_fav);
        radio_setting = (RadioButton) findViewById(R.id.radio_setting);

        fragments.add(new DiscoveryListFragment());
        fragments.add(new FavorityFragment());
        fragments.add(new SettingFragment());
        FragmentTabUtils utils = new FragmentTabUtils(getSupportFragmentManager(), fragments, R.id.fragment_main_container, rgs);
        utils.setOnRgsExtraCheckedChangedListener(this);

        tintManager = new SystemBarTintManager(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (findViewById(R.id.rootView) != null) {
                findViewById(R.id.rootView).post(new Runnable() {   //让布局绘制完成了再设置
                    @Override
                    public void run() {
                        tintManager.setStatusBarTintEnabled(true);
                            tintManager.setStatusBarTintResource(R.color.myGreen);
                        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
                       // findViewById(R.id.rootView).setPadding(0, config.getPixelInsetTop(false), 0, config.getPixelInsetBottom());
                    }
                });
            }
        }

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
