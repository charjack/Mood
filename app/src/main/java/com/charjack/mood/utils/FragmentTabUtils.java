package com.charjack.mood.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 */
public class FragmentTabUtils implements RadioGroup.OnCheckedChangeListener{

    private List<Fragment> fragments; // 一个tab页面对应一个Fragment
    private RadioGroup rgs; // 用于切换tab
    private FragmentManager fragmentManager; // Fragment所属的Activity
    private int fragmentContentId; // Activity中所要被替换的区域的id
    private int currentTab; // 当前Tab页面索引
    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener; // 用于让调用者在切换tab时候增加新的功能

    public FragmentTabUtils(FragmentManager fragmentManager, List<Fragment> fragments, int fragmentContentId, RadioGroup rgs){
        this.fragments = fragments;
        this.rgs = rgs;
        this.fragmentManager = fragmentManager;
        this.fragmentContentId = fragmentContentId;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(fragmentContentId,fragments.get(0));
        ft.commit();
        rgs.setOnCheckedChangeListener(this);
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for(int i = 0;i< rgs.getChildCount();i++){
            if(rgs.getChildAt(i).getId() == checkedId){
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction(i);
                getCurrentFragment().onStop();
                if(fragment.isAdded()){
                    fragment.onStart();
                }else{
                    ft.add(fragmentContentId,fragment);
                    ft.commit();
                }
                showTab(i);

                // 如果设置了切换tab额外功能功能接口
                if (null != onRgsExtraCheckedChangedListener) {
                    onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
                }
            }
        }
    }

    private void showTab(int index) {
        for(int i = 0;i < fragments.size();i++ ){
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(index);
            if(index ==i){
                ft.show(fragment);
            }else{
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = index;
    }

    private FragmentTransaction obtainFragmentTransaction(int index){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        return ft;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
        return onRgsExtraCheckedChangedListener;
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }
    public static interface OnRgsExtraCheckedChangedListener{
        public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,int checkedId, int index);
    }
}
