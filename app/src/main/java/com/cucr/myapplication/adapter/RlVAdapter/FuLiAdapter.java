package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.fuli.DuiHuanCatgoryActivity;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.model.fuli.ActiveInfo;
import com.cucr.myapplication.model.fuli.DuiHuanGoosInfo;
import com.cucr.myapplication.utils.CommonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 911 on 2017/6/23.
 */
public class FuLiAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ActiveInfo.RowsBean> activeInfos;
    private List<DuiHuanGoosInfo.RowsBean> goodInfos;
    private int hasDuiHuan;

    public FuLiAdapter(Context context, List<ActiveInfo.RowsBean> activeInfos) {
        this.activeInfos = activeInfos;
        mContext = context;
    }

    public void setDate(List<ActiveInfo.RowsBean> activeInfos) {
        this.activeInfos = activeInfos;
        notifyDataSetChanged();
    }

    public void setDuiHuan(List<DuiHuanGoosInfo.RowsBean> goodInfos) {
        this.goodInfos = goodInfos;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constans.TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_duihuan_header, parent, false);
            DuiHuanViewHolder holder = new DuiHuanViewHolder(view);
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
            final ActiveInfo.RowsBean bean = activeInfos.get(position - 1);
            ((FiLiHolder) holder).rl_fuli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemListener != null) {
                        onItemListener.OnItemClick(v, bean.getId());
                    }
                }
            });
                //显示图片
                ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + bean.getPicUrl(), ((FiLiHolder) holder).iv_active_bg);
                //显示进行状态
                String endTime = bean.getEndTime();
                ((FiLiHolder) holder).tv_isopen.setText(CommonUtils.IsGone(endTime) ? "进行中" : "已结束");
                //跳转链接
//            String locationUrl = rowsBean.getLocationUrl();

        } else if (holder instanceof DuiHuanViewHolder) {
            FuLiDuiHuanAdapter fuLiDuiHuanAdapter = new FuLiDuiHuanAdapter(mContext);
            ((DuiHuanViewHolder) holder).mRlv_duihuan.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
            ((DuiHuanViewHolder) holder).mRlv_duihuan.setAdapter(fuLiDuiHuanAdapter);
            fuLiDuiHuanAdapter.setDate(goodInfos);
            fuLiDuiHuanAdapter.setOnItemListener(new FuLiDuiHuanAdapter.OnItemListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    Intent intent = new Intent(view.getContext(), DuiHuanCatgoryActivity.class);
                    intent.putExtra("datas", (Serializable) goodInfos);
                    intent.putExtra("position", position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? Constans.TYPE_ONE : Constans.TYPE_TWO;
    }

    @Override
    public int getItemCount() {
        //判断是否有福利活动
        if (goodInfos == null || goodInfos.size() == 0) {
            hasDuiHuan = 0;
        } else {
            hasDuiHuan = 1;
        }
        return activeInfos == null ? 0 + hasDuiHuan : activeInfos.size() + hasDuiHuan;
    }

    class FiLiHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_fuli;
        private TextView tv_isopen;
        private ImageView iv_active_bg;

        public FiLiHolder(View itemView) {
            super(itemView);
            rl_fuli = (RelativeLayout) itemView.findViewById(R.id.rl_fuli);
            tv_isopen = (TextView) itemView.findViewById(R.id.tv_isopen);
            iv_active_bg = (ImageView) itemView.findViewById(R.id.iv_active_bg);
        }
    }

    class DuiHuanViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRlv_duihuan;

        public DuiHuanViewHolder(View itemView) {
            super(itemView);
            mRlv_duihuan = (RecyclerView) itemView.findViewById(R.id.rlv_fuli_duihuan);
        }
    }

    private OnItemListener onItemListener;


    public interface OnItemListener {
        void OnItemClick(View view, int activeId);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

}
