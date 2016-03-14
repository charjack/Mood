package com.charjack.mood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.charjack.mood.vo.PassageInfo;
import com.charjack.mood.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/13.
 */
public class DiscoveryAdapter extends BaseAdapter{

    Context ctx;
    ArrayList<PassageInfo> passageInfos;
    public DiscoveryAdapter(Context ctx, ArrayList<PassageInfo> passageInfos){
        this.ctx = ctx;
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
            convertview.setTag(vh);
        }

        vh = (ViewHolder) convertview.getTag();
        PassageInfo passageInfo = passageInfos.get(i);
        //图片怎么加载啊？？？？
        vh.textview_desc.setText(passageInfo.getDescription());
        vh.textview_zannum.setText(String.valueOf(passageInfo.getZannum()));
        return convertview;
    }

    private class ViewHolder{
        ImageView imageview_search;
        TextView textview_desc;
        TextView textview_zannum;
    }
}
