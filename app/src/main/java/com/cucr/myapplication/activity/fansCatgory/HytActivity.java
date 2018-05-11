package com.cucr.myapplication.activity.fansCatgory;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.BaseActivity;
import com.cucr.myapplication.activity.hyt.CreatHytActivity;
import com.cucr.myapplication.adapter.RlVAdapter.HytAdapter;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Hyt.HytListInfos;
import com.cucr.myapplication.bean.app.CommonRebackMsg;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.core.hyt.HytCore;
import com.cucr.myapplication.listener.RequersCallBackListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.refresh.swipeRecyclerView.SwipeRecyclerView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Locale;

import io.rong.imlib.model.Conversation;

public class HytActivity extends BaseActivity implements SwipeRecyclerView.OnLoadListener, HytAdapter.OnClickItems, RequersCallBackListener {

    private Intent mIntent;
    private HytCore mCore;
    private int startId;
    private Gson mGson;
    private HytAdapter mAdapter;
    private int page;
    private int rows;
    //    private MyWaitDialog mMyWaitDialog;
    private SwipeRecyclerView mRlv_hyt;
    private boolean isRefresh;

    @Override
    protected void initChild() {
        initTitle("后援团");
        init();
    }

    @Override
    protected int getChildRes() {
        return R.layout.activity_hyt2;
    }

    private void init() {
        page = 1;
        rows = 15;
        mGson = MyApplication.getGson();
        mCore = new HytCore();
        startId = getIntent().getIntExtra("starId", -1);
//        mMyWaitDialog = new MyWaitDialog(rootView.getContext(), R.style.MyWaitDialog);
        mIntent = new Intent(MyApplication.getInstance(), CreatHytActivity.class);
        mIntent.putExtra("starId", startId);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mRlv_hyt = (SwipeRecyclerView) findViewById(R.id.rlv_hyt);
        mRlv_hyt.getRecyclerView().setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        mRlv_hyt.setOnLoadListener(this);
        mAdapter = new HytAdapter();
        mAdapter.setOnClickItems(this);
        mRlv_hyt.setAdapter(mAdapter);
        onRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && data.getBooleanExtra("need", false)) {
            onRefresh();
        }
    }

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
    public void onClickJoin(final int hytId, final String name, boolean isjoin, int creatId) {
        final Conversation.ConversationType conversationType = Conversation.ConversationType.GROUP;
        if (isjoin) {
//          RongIM.getInstance().startConversation(MyApplication.getInstance(), Conversation.ConversationType.GROUP, hytId + "", name);
            Uri uri = Uri.parse("rong://" + getApplicationInfo().processName).buildUpon().appendPath("conversation")
                    .appendPath(conversationType.getName().toLowerCase(Locale.US))
                    .appendQueryParameter("targetId", hytId + "")
                    .appendQueryParameter("title", name)
                    .appendQueryParameter("ctretId", creatId + "").build();

            startActivityForResult(new Intent("android.intent.action.VIEW", uri), 2);
            return;
        }

        mCore.joinHyt(hytId, new RequersCallBackListener() {
            @Override
            public void onRequestSuccess(int what, Response<String> response) {
                if (what == Constans.TYPE_THREE) {
                    CommonRebackMsg msg = mGson.fromJson(response.get(), CommonRebackMsg.class);
                    if (msg.isSuccess()) {
                        //再次查询
                        onRefresh();
//                        RongIM.getInstance().startConversation(MyApplication.getInstance(), Conversation.ConversationType.GROUP, hytId + "", name);
                        Uri uri = Uri.parse("rong://" + getApplicationInfo().processName).buildUpon().appendPath("conversation").appendPath(conversationType.getName().toLowerCase(Locale.US)).appendQueryParameter("targetId", hytId + "").appendQueryParameter("title", name).build();
                        startActivityForResult(new Intent("android.intent.action.VIEW", uri), 2);
                    } else {
                        ToastUtils.showToast(msg.getMsg());
                    }
                }
            }

            @Override
            public void onRequestStar(int what) {
//                mMyWaitDialog.show();
            }

            @Override
            public void onRequestError(int what, Response<String> response) {

            }

            @Override
            public void onRequestFinish(int what) {
//                mMyWaitDialog.dismiss();
            }
        });
    }

    //刷新
    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        mRlv_hyt.getSwipeRefreshLayout().setRefreshing(true);
        mCore.queryHyt(startId, page, rows, this);
    }

    //加载
    @Override
    public void onLoadMore() {
        isRefresh = false;
        page++;
        mRlv_hyt.onLoadingMore();
        mCore.queryHyt(startId, page, rows, this);
    }

    @Override
    public void onRequestSuccess(int what, Response<String> response) {
        HytListInfos infos = mGson.fromJson(response.get(), HytListInfos.class);
        if (infos.isSuccess()) {
            if (isRefresh) {
                mAdapter.setData(infos.getRows());
            } else {
                mAdapter.addData(infos.getRows());
            }
            if (infos.getTotal() <= page * rows) {
                mRlv_hyt.onNoMore("没有更多了");
            } else {
                mRlv_hyt.complete();
            }
        } else {
            ToastUtils.showToast(infos.getErrorMsg());
        }
    }

    @Override
    public void onRequestStar(int what) {
    }

    @Override
    public void onRequestError(int what, Response<String> response) {

    }

    @Override
    public void onRequestFinish(int what) {
        switch (what) {
            //后援活动查询
            case Constans.TYPE_TWO:
                if (mRlv_hyt.isRefreshing()) {
                    mRlv_hyt.getSwipeRefreshLayout().setRefreshing(false);
                }
                break;
        }
    }
}
