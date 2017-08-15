package com.cucr.myapplication.activity.regist;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.fragment.load.DongtaiLoadFragment;
import com.cucr.myapplication.fragment.load.PswLoadFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

public class LoadActivity extends Activity {

    @ViewInject(R.id.rg_load)
    RadioGroup rg_load;

    private List<Fragment> mFragmentList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        ViewUtils.inject(this);
        initBar();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new PswLoadFragment());
        mFragmentList.add(new DongtaiLoadFragment());

        initFragment(0);
        initView();

    }

    private void initFragment(int i) {
        getFragmentManager().beginTransaction().replace(R.id.ll_load_continer,mFragmentList.get(i)).commit();
    }

    private void initView() {
        rg_load.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_1){
                    initFragment(0);
                }else{
                    initFragment(1);
                }
            }
        });
    }


    protected void initBar() {
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();

    }
}
