package com.charjack.mood;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.charjack.mood.adapter.DiscoveryAdapter;
import com.charjack.mood.vo.PassageInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SettingFragment extends Fragment {

    DiscoveryAdapter discoveryAdapter;
    MainActivity mainActivity;
    ArrayList<PassageInfo> passageInfos;
    ListView listView_discovery;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.setting_fragment,null);
        return view;
    }
}
