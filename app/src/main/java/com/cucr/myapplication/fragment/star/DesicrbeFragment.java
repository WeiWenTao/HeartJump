package com.cucr.myapplication.fragment.star;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.star.StarDesrc;
import com.cucr.myapplication.core.star.StarInfoCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by cucr on 2018/5/23.
 */

public class DesicrbeFragment extends Fragment implements RequersCallBackListener {

    @ViewInject(R.id.tv_star_name)
    private TextView starName;

    @ViewInject(R.id.tv_star_gender)
    private TextView starGender;

    @ViewInject(R.id.tv_star_hight)
    private TextView starHight;

    @ViewInject(R.id.tv_star_blood)
    private TextView starBlood;

    @ViewInject(R.id.tv_star_bir)
    private TextView starBir;

    @ViewInject(R.id.tv_star_detail)
    private TextView starDetail;

    private View view;
    private Gson mGson;
    private StarDesrc mStarDesrc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_star_describe, container, false);
            ViewUtils.inject(this, view);
            init();
        }
        return view;
    }

    private void init() {
        mGson = MyApplication.getGson();
        StarInfoCore core = new StarInfoCore();
        if (getArguments() != null) {
            int starId = getArguments().getInt("starId");
            core.queryStarDesrc(starId, this);
        }
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        mStarDesrc = mGson.fromJson(response.get(), StarDesrc.class);

        if (mStarDesrc.isSuccess()) {
            setView();
        } else {
            ToastUtils.showToast(mStarDesrc.getMsg());
        }
    }

    private void setView() {
        starName.setText(mStarDesrc.getObj().getRealName());
        starGender.setText(mStarDesrc.getObj().getSex() == 1 ? "男" : "女");
        starHight.setText(mStarDesrc.getObj().getHeight());
        starBlood.setText(mStarDesrc.getObj().getBlood());
        starBir.setText(mStarDesrc.getObj().getBirthday().substring(0, 10));
        starDetail.setText("简介：   " + mStarDesrc.getObj().getPlain());
    }

    @Override
    public void onRequestStar(int what) {

    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {

    }
}
