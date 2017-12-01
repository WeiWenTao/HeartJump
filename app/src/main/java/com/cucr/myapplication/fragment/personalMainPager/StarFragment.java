package com.cucr.myapplication.fragment.personalMainPager;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.RlVAdapter.RLVStarAdapter;
import com.cucr.myapplication.core.starListAndJourney.QueryMyFocusStars;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.starList.MyFocusStarInfo;
import com.cucr.myapplication.utils.ToastUtils;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/7/19.
 */

public class StarFragment extends Fragment {

    private View mView;
    private int userId;

    public StarFragment(int userId) {
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_star, null);
            initView(mView);
        }
        return mView;
    }

    private void initView(View mView) {
        QueryMyFocusStars core = new QueryMyFocusStars();
        RecyclerView rlv_starlist = (RecyclerView) mView.findViewById(R.id.rlv_his_starlist);
        rlv_starlist.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        final RLVStarAdapter adapter = new RLVStarAdapter();
        //分割线
        DividerItemDecoration decor = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        rlv_starlist.addItemDecoration(decor);
        rlv_starlist.setAdapter(adapter);
        adapter.setOnItemClick(new RLVStarAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {

            }
        });
        //查询ta的关注明星
        core.queryMyFocuses(userId, new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                MyFocusStarInfo starInfo = MyApplication.getGson().fromJson(response.get(), MyFocusStarInfo.class);
                if (starInfo.isSuccess()) {
                    adapter.setData(starInfo.getRows());
                } else {
                    ToastUtils.showToast(response.get());
                }
            }
        });
    }
}
