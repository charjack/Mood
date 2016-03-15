package com.charjack.mood.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.charjack.mood.utils.ImageLoadHelper;
import com.charjack.mood.vo.PassageInfo;
import com.charjack.mood.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2016/3/13.
 */
public class DiscoveryAdapter extends BaseAdapter{

    Context ctx;
    ArrayList<PassageInfo> passageInfos;
//    private int iscompleted = 0;


    private static OnDicoveryAdapterListener dicoveryListener;

    public DiscoveryAdapter(Context ctx, ArrayList<PassageInfo> passageInfos){
        this.ctx = ctx;
        dicoveryListener = (OnDicoveryAdapterListener) ctx;
        this.passageInfos = passageInfos;
    }

    @Override
    public int getCount() {
        return passageInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return passageInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        ViewHolder vh;
        if(convertview == null){
            convertview = LayoutInflater.from(ctx).inflate(R.layout.item_discovery,null);
            vh = new ViewHolder();
            vh.imageview_search = (ImageView) convertview.findViewById(R.id.imageview_search);
            vh.textview_desc = (TextView) convertview.findViewById(R.id.textview_desc);
            vh.textview_zannum = (TextView) convertview.findViewById(R.id.textview_zannum);
            vh.imageview_share = (ImageView) convertview.findViewById(R.id.imageview_share);
            convertview.setTag(vh);
        }
//        iscompleted = 0;
        vh = (ViewHolder) convertview.getTag();
        PassageInfo passageInfo = passageInfos.get(i);
        vh.imageview_search.setVisibility(View.GONE);
        vh.textview_desc.setText("       "+passageInfo.getDescription());
        vh.textview_zannum.setText(String.valueOf(passageInfo.getZannum()));
        //图片加载用universal imageloader
        DisplayImageOptions options= ImageLoadHelper.getInstance().getDisplayImageOptions(R.mipmap.no_network, R.mipmap.no_network);;
        //ImageLoader.getInstance().displayImage(passageInfo.getImg(),vh.imageview_search,options);
        ImageLoader.getInstance().displayImage(passageInfo.getImg(), vh.imageview_search, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                view.setVisibility(View.VISIBLE);
//                Message msg = imagehandler.obtainMessage(10, "success");
//                msg.sendToTarget();
//                iscompleted = 1*9;
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });

//        if(iscompleted == 9){
//            vh.imageview_search.setVisibility(ImageView.VISIBLE);
//        }

        vh.imageview_share.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  dicoveryListener.Onsharelistener();
              }
          }
        );

        return convertview;
    }

    private class ViewHolder{
        ImageView imageview_search;
        ImageView imageview_share;
        TextView textview_desc;
        TextView textview_zannum;
    }

//    public Handler imagehandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch(msg.what) {
//                case 10:
//                    View v = LayoutInflater.from(ctx).inflate(R.layout.item_discovery,null);
//                    ImageView imageView = (ImageView) v.findViewById(R.id.imageview_search);
//                    imageView.setVisibility(ImageView.VISIBLE);
//                    break;
//            }
//        }
//    };

    public interface OnDicoveryAdapterListener{
        public void Onsharelistener();
    }
}
