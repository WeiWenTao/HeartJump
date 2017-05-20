package com.cucr.myapplication.fragment.live;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.MyfocusAdapter;
import com.etiennelawlor.quickreturn.library.enums.QuickReturnViewType;
import com.etiennelawlor.quickreturn.library.listeners.QuickReturnListViewOnScrollListener;

import net.lucode.hackware.magicindicator.MagicIndicator;

/**
 * Created by 911 on 2017/5/2.
 */

public class HotAndFocusFragment extends Fragment {

    private MagicIndicator MagicIndicator;
    private ListView mLv_tv_my_focus;

    @SuppressLint("ValidFragment")
    public HotAndFocusFragment(MagicIndicator MagicIndicator) {
        this.MagicIndicator = MagicIndicator;
    }

    public HotAndFocusFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_return_twitter, container, false);
        mLv_tv_my_focus = (ListView) view.findViewById(R.id.lv_tv_my_focus);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //添加头部
        mLv_tv_my_focus.addHeaderView(View.inflate(view.getContext(),R.layout.header_space,null),null,true);
        mLv_tv_my_focus.setHeaderDividersEnabled(false);
        mLv_tv_my_focus.setAdapter(new MyfocusAdapter());



        QuickReturnListViewOnScrollListener listener = new QuickReturnListViewOnScrollListener.Builder(QuickReturnViewType.HEADER)
                .header(MagicIndicator)
                .minHeaderTranslation(-100)
                .isSnappable(true)
                .build();

        mLv_tv_my_focus.setOnScrollListener(listener);


    }

}
