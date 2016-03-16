package com.charjack.mood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.charjack.mood.Constant.UrlConstant;
import com.charjack.mood.adapter.DiscoveryAdapter;
import com.charjack.mood.vo.PassageInfo;
import com.charjack.mood.vo.WebPassageInfo;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
*做开发时。1：定要先定义好布局，
*       2、确定并熟悉所用的所有第三方库，以免出现架构的错误，就想pulltorefresh这样的错误，不能犯。（这里只是把库集成进来了，暂时不进行修改了）
*       3、开发
* /
 */
/**
 * Created by Administrator on 2016/3/13.
 */
public class DiscoveryListFragment extends Fragment implements AbsListView.OnScrollListener,View.OnClickListener{


    DiscoveryAdapter discoveryAdapter;
    MainActivity mainActivity;
    ArrayList<PassageInfo> passageInfos= new ArrayList<PassageInfo>();
    ListView listView_discovery;
    private WebPassageInfo webPassageInfo;
    public int currentpage=1;
    private final OkHttpClient client = new OkHttpClient();
    private ImageView sliding_image,duomi_link_image;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.discovery_list_fragment,null);

        listView_discovery = (ListView) view.findViewById(R.id.discovery_list);
        sliding_image = (ImageView) view.findViewById(R.id.sliding_image);
        sliding_image.setOnClickListener(this);
        duomi_link_image = (ImageView) view.findViewById(R.id.duomi_link_image);
        duomi_link_image.setOnClickListener(this);

        listView_discovery.setOnScrollListener(this);
        LayoutInflater ifl = LayoutInflater.from(mainActivity);
        View footerView = ifl.inflate(R.layout.footviewlayout, null);
        listView_discovery.addFooterView(footerView);

        webPassageInfo = new WebPassageInfo(new ArrayList<PassageInfo>());
        //这里初始化加载第一个page，后面当list下滑到底部后，出发一个操作继续加载
        //passageInfos = loadDataUtils.getLists();//通过网络来加载数据
        discoveryAdapter = new DiscoveryAdapter(mainActivity,webPassageInfo.getData());
        listView_discovery.setAdapter(discoveryAdapter);
        getInfofromNetwork();
        // getBodyData();
        return view;
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    discoveryAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    //okhttp
    private ExecutorService mThreadPool;
    public DiscoveryListFragment(){mThreadPool= Executors.newSingleThreadExecutor();}

    public void getInfofromNetwork() {
        final String urlhome = UrlConstant.HOME_BODY_BEGIN+ currentpage +UrlConstant.HOME_BODY_END;
        System.out.println(urlhome);
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(urlhome).build();
                //android.os.NetworkOnMainThreadException 如果在主线程中执行的话会报这个异常
                //所以要想办法放到子线程执行
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        System.out.println("网络下载成功");
//                        System.out.println(response.body().string());
                        //response.body().string()这个是存在缓存中数据，只能使用一次，如果之前打印使用一次，后面就会出错。
                        WebPassageInfo webpassageInfo = JSONObject.parseObject(response.body().string(), WebPassageInfo.class);
//                      System.out.println(webpassageInfo.toString());
                        webPassageInfo.getData().addAll(webpassageInfo.getData());
                        //discoveryAdapter.notifyDataSetChanged();
                        Message msg = handler.obtainMessage(1,"success");
                        msg.sendToTarget();
                    }
                } catch (IOException e) {
                    System.out.println("网络下载失败");
                    e.printStackTrace();
                }
            }
        });
    }


    //httpuitls
    private HttpUtils httpUtils = new HttpUtils();
    private HttpHandler httpHandler;

    private void getBodyData() {

        final String urlhome = UrlConstant.HOME_BODY_BEGIN+ currentpage +UrlConstant.HOME_BODY_END;
        httpHandler = httpUtils.send(HttpRequest.HttpMethod.GET, urlhome, null, new RequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                String result = (String) responseInfo.result;
                if (!TextUtils.isEmpty(result)) {
                    WebPassageInfo temp = JSONObject.parseObject(result, WebPassageInfo.class);
                    System.out.println(result);
                    System.out.println(temp);
                    if (temp == null) {

                    } else {
                        webPassageInfo.getData().addAll(temp.getData());
                        discoveryAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                System.out.println("httputil failed");
            }
        });
    }

    private int visibleLastIndex;//用来可显示的最后一条数据的索引
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (discoveryAdapter.getCount()==visibleLastIndex && scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){


            currentpage++;
            getBodyData();
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        visibleLastIndex = firstVisibleItem+visibleItemCount-1;
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
            case R.id.sliding_image:
                Toast.makeText(mainActivity,"slidingmenu",Toast.LENGTH_SHORT).show();
                mainActivity.menu.showMenu(true);
                break;

           case R.id.duomi_link_image:
               break;

        }
    }
}
