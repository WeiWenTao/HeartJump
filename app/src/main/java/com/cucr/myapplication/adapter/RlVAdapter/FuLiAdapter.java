package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.fansCatgory.YyzcActivity;
import com.cucr.myapplication.activity.fuli.HuoDongActivity;
import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.bean.Home.HomeBannerInfo;
import com.cucr.myapplication.bean.fuli.ActiveInfo;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 911 on 2017/6/23.
 */
public class FuLiAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;
    private List<ActiveInfo.RowsBean> activeInfos;
    private List<HomeBannerInfo.ObjBean> obj;
    private Intent mIntent;

    public FuLiAdapter() {
        mContext = MyApplication.getInstance();
    }

    public void setDate(List<ActiveInfo.RowsBean> activeInfos) {
        this.activeInfos = activeInfos;
        notifyDataSetChanged();
    }

    public void setBanner(List<HomeBannerInfo.ObjBean> obj) {
        this.obj = obj;
        notifyDataSetChanged();
    }

    public void addDate(List<ActiveInfo.RowsBean> activeInfos) {
        if (activeInfos == null) {
            return;
        }
        notifyItemInserted(this.activeInfos.size() + 2);
        this.activeInfos.addAll(activeInfos);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constans.TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rl_fuli_header, parent, false);
            FuLiHeader holder = new FuLiHeader(view);
            return holder;
        } else {
            View view = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_rl_fuli, parent, false);
            FiLiHolder holder = new FiLiHolder(view);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //点击事件
        if (holder instanceof FiLiHolder) {
     /*       WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            ViewGroup.LayoutParams layoutParams = ((FiLiHolder) holder).ll_item.getLayoutParams();
            layoutParams.height = (int) (wm.getDefaultDisplay().getHeight() / 3.2f);
            ((FiLiHolder) holder).ll_item.setLayoutParams(layoutParams);*/

            final ActiveInfo.RowsBean bean = activeInfos.get(position - 1);
            ((FiLiHolder) holder).ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemListener != null) {
                        onItemListener.OnItemClick(v, bean.getId(), bean.getActiveName());
                    }
                }
            });
            //显示图片
            ImageLoader.getInstance().displayImage(HttpContans.IMAGE_HOST + bean.getPicUrl(),
                    ((FiLiHolder) holder).iv_active_bg,
                    MyApplication.getImageLoaderOptions());
            //显示进行状态
            String endTime = bean.getEndTime();
            ((FiLiHolder) holder).tv_title.setText(bean.getActiveName());
            ((FiLiHolder) holder).tv_time.setText(bean.getCreateTime());
            ((FiLiHolder) holder).tv_peoples.setText(bean.getOrderNo() + "人参与");

            //跳转链接
//            String locationUrl = rowsBean.getLocationUrl();

        } else if (holder instanceof FuLiHeader) {
            if (obj == null) {
                return;
            }
            mIntent = new Intent();
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ImageLoader.getInstance().displayImage(obj.get(0).getFileUrl(),
                    ((FuLiHeader) holder).iv_banner,
                    MyApplication.getImageLoaderOptions());
            ((FuLiHeader) holder).ll_zxsc.setOnClickListener(this);
            ((FuLiHeader) holder).ll_fxcj.setOnClickListener(this);
            ((FuLiHeader) holder).ll_qyhd.setOnClickListener(this);
            ((FuLiHeader) holder).ll_zcyy.setOnClickListener(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? Constans.TYPE_ONE : Constans.TYPE_TWO;
    }

    @Override
    public int getItemCount() {
        return activeInfos == null ? 1 : activeInfos.size() + 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_zxsc:
//                mIntent.setClass(mContext, HuoDongActivity.class);
                break;

            case R.id.ll_fxcj:
//                mIntent.setClass(mContext, HuoDongActivity.class);
                break;

            case R.id.ll_qyhd:
                mIntent.setClass(mContext, HuoDongActivity.class);
                break;

            case R.id.ll_zcyy:
                mIntent.setClass(mContext, YyzcActivity.class);
                break;
        }
        mContext.startActivity(mIntent);
    }

    class FiLiHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_time;
        private TextView tv_peoples;
        private ImageView iv_active_bg;
        private LinearLayout ll_item;

        public FiLiHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_peoples = (TextView) itemView.findViewById(R.id.tv_peoples);
            iv_active_bg = (ImageView) itemView.findViewById(R.id.iv_active_bg);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    class FuLiHeader extends RecyclerView.ViewHolder {

        @ViewInject(R.id.iv_banner)
        private ImageView iv_banner;

        @ViewInject(R.id.ll_zxsc)
        private LinearLayout ll_zxsc;

        @ViewInject(R.id.ll_fxcj)
        private LinearLayout ll_fxcj;

        @ViewInject(R.id.ll_qyhd)
        private LinearLayout ll_qyhd;

        @ViewInject(R.id.ll_zcyy)
        private LinearLayout ll_zcyy;

        public FuLiHeader(View itemView) {
            super(itemView);
            ViewUtils.inject(this, itemView);
        }
    }

    private OnItemListener onItemListener;


    public interface OnItemListener {
        void OnItemClick(View view, int activeId, String title);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

}
