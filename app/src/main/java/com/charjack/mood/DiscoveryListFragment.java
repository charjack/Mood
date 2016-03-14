package com.charjack.mood;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.charjack.mood.Constant.UrlConstant;
import com.charjack.mood.adapter.DiscoveryAdapter;
import com.charjack.mood.vo.PassageInfo;
import com.charjack.mood.vo.WebPassageInfo;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/3/13.
 */
public class DiscoveryListFragment extends Fragment{


    DiscoveryAdapter discoveryAdapter;
    MainActivity mainActivity;
    ArrayList<PassageInfo> passageInfos= new ArrayList<PassageInfo>();
    ListView listView_discovery;
    private WebPassageInfo webPassageInfo;
    public int currentpage=1;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }
  //  private ExecutorService mThreadPool;

    public DiscoveryListFragment(){/*mThreadPool= Executors.newSingleThreadExecutor();*/}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.discovery_list_fragment,null);

        listView_discovery = (ListView) view.findViewById(R.id.discovery_list);
        webPassageInfo = new WebPassageInfo(new ArrayList<PassageInfo>());
        //这里初始化加载第一个page，后面当list下滑到底部后，出发一个操作继续加载
        //passageInfos = loadDataUtils.getLists();//通过网络来加载数据
        discoveryAdapter = new DiscoveryAdapter(mainActivity,webPassageInfo.getData());
        listView_discovery.setAdapter(discoveryAdapter);
       // getInfofromNetwork();
        getBodyData();
        return view;
    }
//    public Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch(msg.what){
//                case 1:
//                    break;
//            }
//        }
//    };

/*    public void getInfofromNetwork() {
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
                    if(response.isSuccessful()){
                        System.out.println("网络下载成功");
                        System.out.println(response.body().string());
                        WebPassageInfo webpassageInfo = JSONObject.parseObject(response.body().string(),WebPassageInfo.class);
//                        System.out.println(webpassageInfo.toString());
                        webinfo.getData().addAll(webpassageInfo.getData());
                        discoveryAdapter.notifyDataSetChanged();
//                        Message msg = handler.obtainMessage(1,"success");
//                        msg.sendToTarget();
                    }
                } catch (IOException e) {
                    System.out.println("网络下载失败");
                    e.printStackTrace();
                }
            }
        });
    }*/
    private HttpUtils httpUtils = new HttpUtils();
    private HttpHandler httpHandler;
    //httpuitls
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
}
