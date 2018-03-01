package com.cucr.myapplication.fragment.hyt;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.hyt.CreatHytActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.CommonRebackMsg;
import com.cucr.myapplication.bean.Hyt.HytListInfos;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.dialog.MyWaitDialog;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Locale;

import io.rong.imlib.model.Conversation;

/**
 * Created by cucrx on 2018/1/16.
 */

@SuppressLint("ValidFragment")
public class Fragment_hyt extends Fragment implements HytAdapter.OnClickItems {

    private Context mContext;
    private View rootView;
    private Intent mIntent;
    private HytCore mCore;
    private int startId;
    private Gson mGson;
    private HytAdapter mAdapter;
    private int page;
    private int rows;
    private MyWaitDialog mMyWaitDialog;
    private SwipeRecyclerView mRlv_hyt;

    public Fragment_hyt(int startId) {
        this.startId = startId;
    }

    public Fragment_hyt() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = MyApplication.getInstance();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_hyt_hyt, container, false);
            init();
        }
        return rootView;
    }

    private void init() {
        page = 1;
        rows = 15;
        mGson = MyApplication.getGson();
        mCore = new HytCore();
        mMyWaitDialog = new MyWaitDialog(rootView.getContext(), R.style.MyWaitDialog);
        mIntent = new Intent(MyApplication.getInstance(), CreatHytActivity.class);
        mIntent.putExtra("starId", startId);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mRlv_hyt = (SwipeRecyclerView) rootView.findViewById(R.id.rlv_hyt);
        mRlv_hyt.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mAdapter = new HytAdapter();
        mAdapter.setOnClickItems(this);
        mRlv_hyt.setAdapter(mAdapter);
        queryHyt();
    }

    //查询
    private void queryHyt() {
        mCore.queryHyt(startId, page, rows, mCommonListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && data.getBooleanExtra("need", false)) {
            queryHyt();
        }
    }

    private RequersCallBackListener mCommonListener = new RequersCallBackListener() {
        @Override
        public void onRequestSuccess(int what, Response<String> response) {
            HytListInfos hytListInfos = mGson.fromJson(response.get(), HytListInfos.class);
            mAdapter.setData(hytListInfos.getRows());
        }

        @Override
        public void onRequestStar(int what) {
            mMyWaitDialog.show();
        }

        @Override
        public void onRequestFinish(int what) {
            mMyWaitDialog.dismiss();
        }
    };

    //点击条目
    @Override
    public void onClickItem(int position) {
        //创建后援团
        if (position == 0) {
            startActivity(mIntent);
            return;
        }
    }

    //点击加入
    @Override
    public void onClickJoin(final int hytId, final String name, boolean isjoin) {
        final Conversation.ConversationType conversationType = Conversation.ConversationType.GROUP;
        if (isjoin) {
//            RongIM.getInstance().startConversation(MyApplication.getInstance(), Conversation.ConversationType.GROUP, hytId + "", name);
                Uri uri = Uri.parse("rong://" + mContext.getApplicationInfo().processName).buildUpon().appendPath("conversation").appendPath(conversationType.getName().toLowerCase(Locale.US)).appendQueryParameter("targetId", hytId+"").appendQueryParameter("title", name).build();
                startActivityForResult(new Intent("android.intent.action.VIEW", uri),2);
            return;
            }

        mCore.joinHyt(hytId, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                if (what == Constans.TYPE_THREE) {
                    CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                    if (msg.isSuccess()) {
                        //再次查询
                        queryHyt();
//                        RongIM.getInstance().startConversation(MyApplication.getInstance(), Conversation.ConversationType.GROUP, hytId + "", name);
                        Uri uri = Uri.parse("rong://" + mContext.getApplicationInfo().processName).buildUpon().appendPath("conversation").appendPath(conversationType.getName().toLowerCase(Locale.US)).appendQueryParameter("targetId", hytId+"").appendQueryParameter("title", name).build();
                        startActivityForResult(new Intent("android.intent.action.VIEW", uri),2);
                    } else {
                        ToastUtils.showToast(msg.getMsg());
                    }
                }
            }

            @Override
            public void onRequestStar(int what) {
                mMyWaitDialog.show();
            }

            @Override
            public void onRequestFinish(int what) {
                mMyWaitDialog.dismiss();
            }
        });
    }
}
