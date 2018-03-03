package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.user.PersonalMainPagerActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.eventBus.CommentEvent;
import com.cucr.myapplication.bean.login.ReBackMsg;
import com.cucr.myapplication.bean.starList.FocusInfo;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.focus.FocusCore;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by cucr on 2018/2/10.
 */

public class MyFocusAdapter extends RecyclerView.Adapter<MyFocusAdapter.FocusHolder> {

    private List<FocusInfo.RowsBean> rows;
    private FocusCore mCore;
    private MyApplication mInstance;
    private Intent mIntent;
    private Gson mGson;

    public void setData(List<FocusInfo.RowsBean> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }

    @Override
    public FocusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInstance = MyApplication.getInstance();
        mIntent = new Intent(mInstance, PersonalMainPagerActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCore = new FocusCore();
        mGson = MyApplication.getGson();
        View inflate = LayoutInflater.from(mInstance).inflate(R.layout.item_my_focus, parent, false);
        return new FocusHolder(inflate);
    }

    @Override
    public void onBindViewHolder(FocusHolder holder, int position) {
        final FocusInfo.RowsBean.StartBean start = rows.get(0).getStart();
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + start.getUserHeadPortrait(), holder.iv_user_icon_all_focus, MyApplication.getImageLoaderOptions());
        holder.tv_name.setText(start.getName());
        holder.tv_sign.setText(start.getSignName());
        if (start.isNoFocus()) {
            holder.tv_to_focus.setText("加关注");
            holder.tv_to_focus.setTextColor(Color.WHITE);
            holder.tv_to_focus.setBackgroundResource(R.drawable.circle_r_13_sel);
        } else {
            holder.tv_to_focus.setText("已关注");
            holder.tv_to_focus.setTextColor(Color.parseColor("#ff4f49"));
            holder.tv_to_focus.setBackgroundResource(R.drawable.circle_r_13_nor);
        }
        holder.tv_to_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start.isNoFocus()) {
                    mCore.toFocus(start.getId(), new OnCommonListener() {
                        @Override
                        public void onRequestSuccess(Response<String> response) {
                            ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                            if (reBackMsg.isSuccess()) {
                                ToastUtils.showToast("关注成功");
                                start.setNoFocus(!start.isNoFocus());
                                notifyDataSetChanged();
                                EventBus.getDefault().post(new CommentEvent(999));
                            } else {
                                ToastUtils.showToast(reBackMsg.getMsg());
                            }
                        }
                    });

                } else {
                    mCore.cancaleFocus(start.getId(), new OnCommonListener() {
                        @Override
                        public void onRequestSuccess(Response<String> response) {
                            ReBackMsg reBackMsg = mGson.fromJson(response.get(), ReBackMsg.class);
                            if (reBackMsg.isSuccess()) {
                                ToastUtils.showToast("取消关注成功");
                                start.setNoFocus(!start.isNoFocus());
                                notifyDataSetChanged();
                                EventBus.getDefault().post(new CommentEvent(999));
                            } else {
                                ToastUtils.showToast(reBackMsg.getMsg());
                            }
                        }
                    });
                }

            }
        });
        holder.rlv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("userId", start.getId());
                mInstance.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    class FocusHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.iv_user_icon_all_focus)
        private ImageView iv_user_icon_all_focus;

        @ViewInject(R.id.tv_name)
        private TextView tv_name;

        @ViewInject(R.id.tv_sign)
        private TextView tv_sign;

        @ViewInject(R.id.tv_to_focus)
        private TextView tv_to_focus;

        @ViewInject(R.id.rlv_item)
        private RelativeLayout rlv_item;

        public FocusHolder(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }
}
